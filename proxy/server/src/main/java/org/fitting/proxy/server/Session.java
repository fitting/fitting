package org.fitting.proxy.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/** Session. */
public class Session implements Runnable {
    /** Logger for this class */
    private static final Log LOG = LogFactory.getLog(Session.class);

    public static final int SC_OK = 0;
    public static final int SC_CONNECTING_TO_HOST = 1;
    public static final int SC_HOST_NOT_FOUND = 2;
    public static final int SC_URL_BLOCKED = 3;
    public static final int SC_CLIENT_ERROR = 4;
    public static final int SC_INTERNAL_SERVER_ERROR = 5;
    public static final int SC_NOT_SUPPORTED = 6;
    public static final int SC_REMOTE_DEBUG_MODE = 7;
    public static final int SC_CONNECTION_CLOSED = 8;
    public static final int SC_HTTP_OPTIONS_THIS = 9;
    public static final int SC_FILE_REQUEST = 10;
    public static final int SC_MOVED_PERMANENTLY = 11;

    private Server server;
    private InetAddress serverAddress;
    private boolean useProxyForRequest;

    /** downstream connections */
    private Socket client;
    private BufferedOutputStream out;
    private ClientStream in;

    /** upstream connections */
    private Socket HTTP_Socket;
    private BufferedOutputStream HTTP_out;
    private ServerStream HTTP_in;

    public Session(final Server server, final Socket client) {
        this.server = server;

        try {
            in = new ClientStream(server, this, client.getInputStream());
            out = new BufferedOutputStream(client.getOutputStream());
            this.server = server;
            this.client = client;
            this.useProxyForRequest = server.useProxy();
        } catch (IOException e) {
            try {
                client.close();
            } catch (IOException e2) {
            }
            LOG.error("Error while creating IO-Streams: " + e);
            return;
        }

        try {
            serverAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            LOG.error("Can't get own server address! " + e);
            return;
        }
    }

    @Override
    public void run() {
        LOG.info("Begin http session.");
        server.increaseNumConnections();
        try {
            handleRequest();
        } catch (IOException e) {
            /*--debug--*/
            LOG.info("run1: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Session.run() ", e);
        }
        try { // close downstream connections
            in.close();
            out.close();
            client.close();
            if (isConnected()) { // close upstream connections (webserver or other proxy)
                HTTP_Socket.close();
                HTTP_out.close();
                HTTP_in.close();
            }
        } catch (IOException e) {
            LOG.error("run2: " + e.getMessage());
        }
        server.decreaseNumConnections();
        /*--debug--*/
        LOG.info("End http session.");
    }

    /** The main routine, where it all happens */
    public void handleRequest() throws Exception {
        InetAddress remote_host;
        Read remote_in = null;
        int remote_port;
        byte[] b = new byte[65536];
        int numread = in.read(b);

        while (true) { // with this loop we support persistent connections
            if (numread == -1) { // -1 signals an error
                final int statusCode = in.getStatusCode();
                if (statusCode != SC_CONNECTING_TO_HOST) {
                    switch (statusCode) {
                        case SC_CONNECTION_CLOSED:
                            break;
                        case SC_CLIENT_ERROR:
                            sendErrorMSG(400,
                                    "Your client sent a request that this proxy could not understand. ("
                                            + in.getErrorDescription() + ")");
                            break;
                        case SC_HOST_NOT_FOUND:
                            sendErrorMSG(
                                    504,
                                    "Host not found.<BR>The proxy was unable to resolve the hostname of this request. " +
                                            "<BR>Perhaps the hostname was misspelled, the server is down or you have " +
                                            "no connection to the internet.");
                            break;
                        case SC_INTERNAL_SERVER_ERROR:
                            sendErrorMSG(500,
                                    "Server Error! (" + in.getErrorDescription() + ")");
                            break;
                        case SC_NOT_SUPPORTED:
                            sendErrorMSG(501,
                                    "Your client used a HTTP method that this proxy doesn't support: ("
                                            + in.getErrorDescription() + ")");
                            break;
                        case SC_URL_BLOCKED:
                            sendErrorMSG(
                                    403,
                                    (in.getErrorDescription() != null && in.getErrorDescription().length() > 0
                                            ? in.getErrorDescription()
                                            : "The request for this URL was denied by the jHTTPp2 URL-Filter."));
                            break;
                        case SC_HTTP_OPTIONS_THIS:
                            sendHeader(200);
                            endHeader();
                            break;
                        case SC_MOVED_PERMANENTLY:
                            sendHeader(301);
                            write(out, "Location: " + in.getErrorDescription()
                                    + "\r\n");
                            endHeader();
                            out.flush();
                        default:
                    }
                    break; // return from main loop.
                } else { // also an error because we are not connected (or to
                    // the wrong host)
                    // Creates a new connection to a remote host.
                    if (isConnected()) {
                        try {
                            HTTP_Socket.close();
                        } catch (IOException e_close_socket) {
                        }
                    }
                    numread = in.getHeaderLength(); // get the header length
                    if (!useProxyForRequest) {// sets up hostname and port
                        remote_host = in.getRemoteHost();
                        remote_port = in.remotePort;
                    } else {
                        remote_host = server.getProxy();
                        remote_port = server.getProxyPort();
                    }
                    // if (server.debug)server.writeLog("Connect: " +
                    // remote_host + ":" + remote_port);
                    try {
                        connect(remote_host, remote_port);
                    } catch (IOException e_connect) {
                        /*--debug--*/
                        LOG.info("handleRequest: " + e_connect.toString());
                        sendErrorMSG(
                                502,
                                "Error while creating a TCP connection to ["
                                        + remote_host.getHostName()
                                        + ":"
                                        + remote_port
                                        + "] <BR>The proxy server cannot connect to the given address or port ["
                                        + e_connect.toString() + "]");
                        break;
                    } catch (Exception e) {
                        /*--debug--*/
                        LOG.info("handleRequest: " + e.toString());
                        sendErrorMSG(500, "Error: " + e.toString());
                        break;
                    }
                    if (!in.isTunnel() || (in.isTunnel() && useProxyForRequest)) {
                        // no SSL-Tunnel or SSL-Tunnel with another remote
                        // proxy: simply forward the request
                        HTTP_out.write(b, 0, numread);
                        HTTP_out.flush();
                    } else {
                        // SSL-Tunnel with "CONNECT": creates a tunnel
                        // connection with the server
                        sendLine(server.getHttpVersion()
                                + " 200 Connection established");
                        sendLine("Proxy-Agent", server.getIdentification());
                        endHeader();
                        out.flush();
                    }
                    // read the data from the remote server
                    if (remote_in != null) {
                        remote_in.close(); // close org.fitting.proxy.Jhttpp2Read thread
                    }
                    remote_in = new Read(server, this, HTTP_in, out);

                    server.addBytesWritten(numread);
                }
            }

            while (true) { // reads data from the client
                numread = in.read(b);
                // if (server.debug)server.writeLog("org.fitting.proxy.Jhttpp2HTTPSession: " +
                // numread + " Bytes read.");
                if (numread != -1) {
                    HTTP_out.write(b, 0, numread);
                    HTTP_out.flush();
                    server.addBytesWritten(numread);
                } else
                    break;
            } // end of inner loop for data transfer
        }// end of main loop

        out.flush();

        if (isConnected() && remote_in != null)
            remote_in.close(); // close org.fitting.proxy.Jhttpp2Read thread
        return;
    }

    /** Connects to the given host and port. */
    public void connect(final InetAddress host, final int port) throws IOException {
        HTTP_Socket = new Socket(host, port);
        HTTP_in = new ServerStream(this, HTTP_Socket.getInputStream());
        HTTP_out = new BufferedOutputStream(HTTP_Socket.getOutputStream());
    }

    /** sends a message to the user */
    public void sendErrorMSG(int a, String info) throws IOException {
        String statuscode = sendHeader(a);
        String localhost = "localhost";
        try {
            localhost = InetAddress.getLocalHost().getHostName() + ":"
                    + server.port;
        } catch (UnknownHostException e_unknown_host) {
        }
        String msg = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"><html>\r"
                + "<!-- Proxy error message --><HEAD>\r" + "<TITLE>"
                + statuscode
                + "</TITLE>\r"
                + "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\" LINK=\"#000080\" VLINK=\"#000080\" ALINK=\"#000080\">\r"
                + "<h2 class=\"headline\">HTTP "
                + statuscode
                + " </h2>\r"
                + "<HR size=\"4\">\r"
                + "<p class=\"i30\">Your request for the following URL failed:</p>"
                + "<p class=\"tiagtext\"><a href=\""
                + in.getFullUrl()
                + "\">"
                + in.getFullUrl()
                + "</A> </p>\r"
                + "<P class=\"i25\">Reason: "
                + info
                + "</P>"
                + "<HR size=\"4\">\r"
                + "<p class=\"i25\">HTTP Proxy, Version " + server.getVersion() + " at " + localhost + "</p>\r"
                + "</BODY></HTML>";
        sendLine("Content-Length", String.valueOf(msg.length()));
        sendLine("Content-Type", "text/html; charset=iso-8859-1");
        endHeader();
        write(out, msg);
        out.flush();
    }

    /**
     * Send header.
     * @param status The status.
     * @return status code.
     * @throws java.io.IOException
     */
    public String sendHeader(int status) throws IOException {
        String stat;
        switch (status) {
            case 200:
                stat = "200 OK";
                break;
            case 202:
                stat = "202 Accepted";
                break;
            case 300:
                stat = "300 Ambiguous";
                break;
            case 301:
                stat = "301 Moved Permanently";
                break;
            case 400:
                stat = "400 Bad Request";
                break;
            case 401:
                stat = "401 Denied";
                break;
            case 403:
                stat = "403 Forbidden";
                break;
            case 404:
                stat = "404 Not Found";
                break;
            case 405:
                stat = "405 Bad Method";
                break;
            case 413:
                stat = "413 Request Entity Too Large";
                break;
            case 415:
                stat = "415 Unsupported Media";
                break;
            case 501:
                stat = "501 Not Implemented";
                break;
            case 502:
                stat = "502 Bad Gateway";
                break;
            case 504:
                stat = "504 Gateway Timeout";
                break;
            case 505:
                stat = "505 HTTP Version Not Supported";
                break;
            default:
                stat = "500 Internal Server Error";
        }
        sendLine(server.getHttpVersion() + " " + stat);
        sendLine("Server", server.getIdentification());
        if (status == 501) {
            sendLine("Allow", "GET, HEAD, POST, PUT, DELETE, CONNECT");
        }
        sendLine("Cache-Control", "no-cache, must-revalidate");
        sendLine("Connection", "close");
        return stat;
    }

    /**
     * Send header.
     * @param status         The status.
     * @param content_type   The content type.
     * @param content_length The content length.
     * @throws java.io.IOException
     */
    public void sendHeader(final int status, final String content_type, final long content_length) throws IOException {
        sendHeader(status);
        sendLine("Content-Length", String.valueOf(content_length));
        sendLine("Content-Type", content_type);
    }

    public void sendLine(final String s) throws IOException {
        write(out, s + "\r\n");
    }

    public void sendLine(final String header, final String s) throws IOException {
        write(out, header + ": " + s + "\r\n");
    }

    public void endHeader() throws IOException {
        write(out, "\r\n");
    }

    /**
     * Write string to outputstream.
     * @param out   The BufferedOutputStream.
     * @param value The value to write.
     */
    public void write(final BufferedOutputStream out, final String value) throws IOException {
        out.write(value.getBytes(), 0, value.length());
    }

    /**
     * Gets the server address.
     * @return serveraddress The server address.
     */
    public InetAddress getServerAddress() {
        return serverAddress;
    }

    /**
     * Gets the client.
     * @return client The client socket.
     */
    public Socket getClient() {
        return client;
    }

    /**
     * Indicates if the session is connected.
     * @return <code>true</code> if connected, else <code>false</code>.
     */
    public boolean isConnected() {
        return HTTP_Socket != null;
    }

    /**
     * Get the statuscode.
     * @return statusCode The status code.
     */
    public int getStatus() {
        return in.getStatusCode();
    }

    /**
     * Indicates to use proxy for request.
     * @return <code>true</code> if use, else <code>false</code>.
     */
    public boolean isUseProxyForRequest() {
        return useProxyForRequest;
    }

    /**
     * Sets indicator use proxy for request.
     * @param useProxyForRequest Indicator use proxy for request.
     */
    public void setUseProxyForRequest(boolean useProxyForRequest) {
        this.useProxyForRequest = useProxyForRequest;
    }
}




