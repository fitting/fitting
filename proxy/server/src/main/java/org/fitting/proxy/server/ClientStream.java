package org.fitting.proxy.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.fitting.proxy.server.Session.SC_CLIENT_ERROR;
import static org.fitting.proxy.server.Session.SC_CONNECTING_TO_HOST;
import static org.fitting.proxy.server.Session.SC_CONNECTION_CLOSED;
import static org.fitting.proxy.server.Session.SC_FILE_REQUEST;
import static org.fitting.proxy.server.Session.SC_HOST_NOT_FOUND;
import static org.fitting.proxy.server.Session.SC_INTERNAL_SERVER_ERROR;
import static org.fitting.proxy.server.Session.SC_NOT_SUPPORTED;
import static org.fitting.proxy.server.Session.SC_OK;
import static org.fitting.proxy.server.SessionUtils.CONTENT_LENGTH;
import static org.fitting.proxy.server.SessionUtils.COOKIE;
import static org.fitting.proxy.server.SessionUtils.PROXY_CONNECTION;
import static org.fitting.proxy.server.SessionUtils.REFERER;
import static org.fitting.proxy.server.SessionUtils.USER_AGENT;
import static org.fitting.proxy.server.SessionUtils.httpMethodId;
import static org.apache.commons.lang.StringUtils.startsWith;

/** ClientStream */
public class ClientStream extends BufferedInputStream {
    /** Logger for this class */
    private static final Log LOG = LogFactory.getLog(ClientStream.class);
    private Server server;

    /** Buffer */
    private String buf;
    /** How many Bytes read? */
    private int nRead = 0;
    /** one line */
    private String line;
    /** The length of the header (with body, if one) */
    private int headerLength = 0;
    /** The length of the (optional) body of the actual request */
    private int contentLength = 0;
    /** This is set to true with requests with bodies, like "POST" */
    private boolean body = false;

    /** Connection variables */
    private Session session;
    private InetAddress remoteHostAddress;
    private String remoteHostName;
    private boolean ssl = false;

    private String errordescription;
    private int statuscode = SC_NOT_SUPPORTED;

    public String url;
    public String method;
    public int HTTPversion;
    public boolean ipv6reference; // true only for IPv6 address in URL (RFC 2732)
    public int remotePort = 0;
    public int post_data_len = 0;

    /**
     * Gets the header length.
     * @return length The header length.
     */
    public int getHeaderLength() {
        return headerLength;
    }

    /**
     * Gets the remote host.
     * @return remoteHost The remote host.
     */
    public InetAddress getRemoteHost() {
        return remoteHostAddress;
    }

    /**
     * Gets the remote host name.
     * @return remoteHostName The remote host name.
     */
    public String getRemoteHostName() {
        return remoteHostName;
    }

    /**
     * Constructor.
     * @param server  The Server.
     * @param session The Session.
     * @param in      The InputStream.
     */
    public ClientStream(final Server server, final Session session, final InputStream in) {
        super(in);
        this.server = server;
        this.session = session;
    }

    /**
     * Handler for the actual HTTP request
     * @throws java.io.IOException
     */
    public int read(byte[] a) throws IOException {
        statuscode = SC_OK;

        if (ssl) {// no parsing required if in SSL mode
            return super.read(a);
        }

        if (server == null) {
            throw new IOException("Stream closed");
        }

        boolean cookiesEnabled = server.isEnableCookiesByDefault();
        boolean startLine = true;
        boolean useProxy = server.useProxy();
        int nChars;

        String rq = "";
        headerLength = 0;
        post_data_len = 0;
        contentLength = 0;

        nChars = getLine(); // reads the first line
        buf = line;

        while (nChars != -1 && nChars > 2) {
            if (startLine) {
                startLine = false;
                int methodID = httpMethodId(buf);
                switch (methodID) {
                    case -1: {
                        statuscode = SC_NOT_SUPPORTED;
                        break;
                    }
                    case 2: {
                        ssl = true;
                    }
                    case 0: // '\0'
                    case 1: // '\001'
                    default: {
                        final InetAddress host = parseRequest(buf, methodID);
                        useProxy = !isLocallyMappedDns(host.getHostName());
                        if (statuscode != SC_OK) {
                            break; // error occurred, go on with the next line
                        }

                        if (!useProxy && !ssl) {
                            /* creates a new request without the host name */
                            buf = method + " " + url + " " + server.getHttpVersion() + "\r\n";
                            nRead = buf.length();
                        }
                        if ((useProxy && !session.isConnected()) || !host.equals(remoteHostAddress)) {
                            LOG.debug("connect: " + remoteHostAddress + " -> " + host);
                            statuscode = SC_CONNECTING_TO_HOST;
                            remoteHostAddress = host;
                        }
                        String proxyStatus = useProxy? (new StringBuilder("Proxied[")).append(server.getProxy().getHostAddress()).append("]").toString() : (new StringBuilder("Direct[")).append(host.getHostAddress()).append("]").toString();
                        String logMethod = method != null ? method : "???";
                        LOG.info((new StringBuilder(String.valueOf(proxyStatus))).append(" ").append(logMethod).append(" ").append(getFullUrl()).toString());
                    }
                }
            } else { // content-length parsing.
                if (startsWith(buf.toUpperCase(), CONTENT_LENGTH)) {
                    String clen = buf.substring(16);
                    if (clen.indexOf("\r") != -1) {
                        clen = clen.substring(0, clen.indexOf("\r"));
                    } else if (clen.indexOf("\n") != -1) {
                        clen = clen.substring(0, clen.indexOf("\n"));
                    }
                    try {
                        contentLength = Integer.parseInt(clen);
                    } catch (NumberFormatException e) {
                        statuscode = SC_CLIENT_ERROR;
                    }
                    LOG.debug("read_f: content_len: " + contentLength);
                    if (!ssl) {
                        body = true; // Note: in HTTP/1.1 any method can have a body, not only "POST"
                    }
                } else if (startsWith(buf, PROXY_CONNECTION)) {
                    if (!useProxy) {
                        buf = null;
                    } else {
                        buf = PROXY_CONNECTION + " Keep-Alive\r\n";
                        nRead = buf.length();
                    }
                } else if (startsWith(buf, COOKIE)) { // cookie crunch section
                    if (!cookiesEnabled) {
                        buf = null;
                    }
                } else if (server.filterHttp()) { // HTTP header filtering section
                    if (startsWith(buf, REFERER)) {// removes
                        // "Referer"
                        buf = null;
                    } else if (startsWith(buf, USER_AGENT)) {// changes User-Agent
                        buf = "User-Agent: " + server.getHttpUserAgent() + "\r\n";
                        nRead = buf.length();
                    }
                }
            }

            if (buf != null) {
                rq += buf;
                headerLength += nRead;
            }
            nChars = getLine();
            buf = line;
        }

        if (nChars != -1) { // adds last line (should be an empty line) to the header String.
            if (nChars > 0) {
                rq += buf;
                headerLength += nRead;
            }

            if (headerLength == 0) {
                statuscode = SC_CONNECTION_CLOSED;
            }

            for (int i = 0; i < headerLength; i++) {
                a[i] = (byte) rq.charAt(i);

            }
            if (body) { // read the body, if "Content-Length" given
                post_data_len = 0;
                while (post_data_len < contentLength) {
                    a[headerLength + post_data_len] = (byte) read(); // writes data into the array
                    post_data_len++;
                }
                headerLength += contentLength; // add the body-length to the header-length
                body = false;
            }
        }
        session.setUseProxyForRequest(useProxy);
        return (statuscode == SC_OK) ? headerLength : -1; // return  -1 with an error

    }

    /**
     * Reads a line.
     * @return read The number of chars in the line.
     * @throws java.io.IOException
     */
    public int getLine() throws IOException {
        int c = 0;
        line = "";
        nRead = 0;
        while (c != '\n') {
            c = read();
            if (c != -1) {
                line += (char) c;
                nRead++;
            } else {
                break;
            }
        }
        return nRead;
    }

    /**
     * Parser for the first line from the HTTP request.
     * Sets up the URL, method and remote host name.
     * @return an InetAddress for the host name, null on errors with a
     *         statuscode!=SC_OK
     */
    public InetAddress parseRequest(final String firstLine, final int methodIndex) {
        int pos;
        int ipv6bracket;

        String f = "";
        String r_host_name = "";
        String r_port = "";

        url = "";

        if (ssl) { // remove CONNECT
            f = firstLine.substring(8);
        } else {
            method = firstLine.substring(0, firstLine.indexOf(" ")); // first word in the line
            pos = firstLine.indexOf(":"); // locate first ":"
            if (pos == -1) { // Occurs with "GET / HTTP/1.1 This is not a proxy request
                url = firstLine.substring(firstLine.indexOf(" ") + 1, firstLine.lastIndexOf(" "));
                if (methodIndex == 0) { // method_index==0 --> GET/HEAD
                    statuscode = SC_FILE_REQUEST;
                } else if (methodIndex == 1) {
                    statuscode = SC_INTERNAL_SERVER_ERROR;
                    errordescription = "This HTTP proxy supports only the \"GET\" method while acting as webserver.";
                }
                remotePort = server.port;
                remoteHostAddress = session.getServerAddress();
                return remoteHostAddress;
            }
            f = firstLine.substring(pos + 3); // Proxy request removes "http://"
        }
        f = f.replace("\r", "").replace("\n", ""); // Strip white spaces

        int versionp = f.indexOf("HTTP/");
        String HTTPversionRaw;

        if (versionp == (f.length() - 8)) { // length of "HTTP/x.x": 8 chars

            HTTPversionRaw = f.substring(versionp + 5); // Detect the HTTP version
            if (HTTPversionRaw.equals("1.1")) {
                HTTPversion = 1;
            } else if (HTTPversionRaw.equals("0.00.01-SNAPSHOT")) {
                HTTPversion = 0;
            }

            f = f.substring(0, versionp - 1); // remove " HTTP/x.x"
            LOG.debug("-->" + f + "<--");
        } else { // bad request: no "HTTP/xxx" at the end of the line
            HTTPversionRaw = "";
        }

        pos = f.indexOf("/"); // locate the first slash
        if (pos != -1) {
            url = f.substring(pos); // saves path without host name
            r_host_name = f.substring(0, pos); // reduce string to the host name
        } else {
            url = "/";
            r_host_name = f;
        }

        LOG.debug("#->" + url);


        ipv6bracket = r_host_name.indexOf("["); // search for bracket in host name (IPv6, RFC 2732)
        if (ipv6bracket == 0) {
            r_host_name = r_host_name.substring(1);
            ipv6bracket = r_host_name.indexOf("]");
            r_port = r_host_name.substring(ipv6bracket + 1);
            r_host_name = r_host_name.substring(0, ipv6bracket);

            LOG.debug("ipv6 bracket ->" + r_host_name + "<--");

            ipv6reference = true; // URL with brackets, must be IPv6 address

            pos = r_port.indexOf(":"); // detect the remote port number, if any
            if (pos != -1) {
                r_port = r_port.substring(pos + 1);
            } else {
                r_port = null;
            }
        } else { // no IPv6 reference with brackets according to RFC 2732
            ipv6reference = false;
            pos = r_host_name.indexOf(":");
            if (pos != -1) {
                r_port = r_host_name.substring(pos + 1);
                r_host_name = r_host_name.substring(0, pos);
            } else
                r_port = null;
        }

        if (r_port != null && !r_port.equals("")) { // Port number: parse String and convert to integer
            try {
                remotePort = Integer.parseInt(r_port);
            } catch (NumberFormatException e) {
                LOG.debug("get_Host :" + e + " Failed to parse remote port numer!");
                remotePort = 80;
            }
        } else {
            remotePort = 80;
        }

        LOG.debug(method + " " + url + " " + HTTPversionRaw);

        remoteHostName = r_host_name;
        InetAddress address = null;

        LOG.info(session.getClient().getInetAddress().getHostAddress() + " " + method + " " + getFullUrl());

        try { // Resolve host name
            if (server.getDnsTable().containsKey(remoteHostName)) {
                final String ip = server.getDnsTable().get(remoteHostName);
                if (ip != null) {
                    address = InetAddress.getByName(ip);
                    address = InetAddress.getByAddress(remoteHostName, address.getAddress());
                }
            }
            if (address == null) {
                if (server.useProxy()) {
                    address = InetAddress.getByName("127.0.0.1");
                    address = InetAddress.getByAddress(remoteHostName, address.getAddress());
                } else {
                    address = InetAddress.getByName(remoteHostName);
                }
            }
        } catch (UnknownHostException e_u_host) {
            if (!server.useProxy()) {
                statuscode = SC_HOST_NOT_FOUND;
            }
        }

        if (remotePort == server.port && address != null && address.equals(session.getServerAddress())) {
            if (methodIndex > 0) {
                statuscode = SC_INTERNAL_SERVER_ERROR;
                errordescription = "This WWW proxy supports only the \"GET\" method while acting as webserver.";
            } else {
                statuscode = SC_FILE_REQUEST;
            }
        }
        return address;
    }

    /**
     * Indicates if the remote host is mapped in the dns table.
     * @param remoteHostName The remote host.
     * @return <code>true</code> if locally mapped in dns table, else <code>false</code>.
     */
    public boolean isLocallyMappedDns(String remoteHostName) {
        boolean locallyMapped = false;
        if (server.getDnsTable().containsKey(remoteHostName)) {
            final String ip = server.getDnsTable().get(remoteHostName);
            if (ip != null) {
                locallyMapped = true;
            }
        }
        return locallyMapped;
    }

    /**
     * Indicates if the current connection was established with the CONNECT method.
     * @return <code>true</code> if connected using CONNECT method, else <code>false</code>.
     */
    public boolean isTunnel() {
        return ssl;
    }

    /**
     * Gets the full url.
     * @return fullUrl The the full qualified URL of the actual request.
     */
    public String getFullUrl() {
        final String hostName = getRemoteHostName();
        return "http" + (ssl ? "s" : "") + "://" + (ipv6reference ? "[" + hostName + "]" : hostName)
                + (remotePort != 80 ? (":" + remotePort) : "") + url;
    }

    /**
     * Gets the status code.
     * @return statusCode The status code for the current request.
     */
    public int getStatusCode() {
        return statuscode;
    }

    /**
     * Gets the error description for the request.
     * @return errorDescription The (optional) error description for this request.
     */
    public String getErrorDescription() {
        return errordescription;
    }
}
