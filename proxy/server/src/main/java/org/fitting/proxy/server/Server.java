package org.fitting.proxy.server;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/** Server. */
public class Server implements Runnable {
    /** Logger for this class */
    private static final Log LOG = LogFactory.getLog(Server.class);
    private static final String DEFAULT_USER_AGENT = "Mozilla/4.0 (compatible; MSIE 5.0; WindowsNT 5.1)";
    private static final int DEFAULT_SERVER_PORT = 10000;
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String VERSION = "0.0.1-SNAPSHOT";

    private volatile boolean serverRunning = false;
    private ServerSocket serverSocket;
    public int port = DEFAULT_SERVER_PORT;
    protected Thread runningThread = null;

    private InetAddress proxy;
    private int proxyPort = 0;
    private boolean useProxy;
    private boolean blockUrls;
    private boolean enableCookiesByDefault;
    private boolean filterHttp;
    private String httpUserAgent = DEFAULT_USER_AGENT;
    private Map<String, String> dnsTable;

    private volatile int numconnections;
    private volatile long bytesread;
    private volatile long byteswritten;

    /** Constructor. */
    public Server(final Configuration configuration) {
        dnsTable = new HashMap<String, String>();
        setProxy(configuration.getString("proxy.host", ""));
        setProxyPort(configuration.getInt("proxy.port", 0));
        setUseProxy(configuration.getBoolean("proxy.use", false));
    }

    /**
     * Gets the dns table.
     * @return dnsTable The dns table.
     */
    public Map<String, String> getDnsTable() {
        return dnsTable;
    }

    /**
     * Add a dns entry.
     * @param domain The domain name.
     * @param ip     The ip address.
     */
    public void addDnsEntry(final String domain, final String ip) {
        dnsTable.put(domain, ip);
    }

    /**
     * Sets the dns table.
     * @param dnsTable The dns table.
     */
    public void setDnsTable(final Map<String, String> dnsTable) {
        this.dnsTable = dnsTable;
    }

    /**
     * Set the port.
     * @param port The port.
     */
    public void setPort(final int port) {
        this.port = port;
    }

    /**
     * Get the port.
     * @return port The port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the proxy.
     * @param host The proxy host.
     */
    public void setProxy(final String host) {
        try {
            this.proxy = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException(host + " is not a valid host.");
        }
    }

    /**
     * Gets the proxy.
     * @return proxy The proxy.
     */
    public InetAddress getProxy() {
        return proxy;
    }

    /**
     * Sets the proxy port.
     * @param proxyPort The proxy port.
     */
    public void setProxyPort(final int proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * Gets the proxy port.
     * @return port The proxy port.
     */
    public int getProxyPort() {
        return proxyPort;
    }

    /**
     * Set indicator use proxy.
     * @param useProxy Indicator use proxy.
     */
    public void setUseProxy(final boolean useProxy) {
        this.useProxy = useProxy;
    }

    /**
     * Indicates if we use a proxy.
     * @return <code>true</code> if use, else <code>false</code>.
     */
    public boolean useProxy() {
        return useProxy;
    }

    /**
     * Set indicator block urls.
     * @param blockUrls Indicator block urls.
     */
    public void setBlockUrls(final boolean blockUrls) {
        this.blockUrls = blockUrls;
    }

    /**
     * Indicates if we block urls.
     * @return <code>true</code> if blocking, else <code>false</code>.
     */
    public boolean blockUrls() {
        return blockUrls;
    }

    /**
     * Set indicator enable cookies by default.
     * @param enableCookiesByDefault Indicator enable cookies by default.
     */
    public void setEnableCookiesByDefault(final boolean enableCookiesByDefault) {
        this.enableCookiesByDefault = enableCookiesByDefault;
    }

    /**
     * Set indicator filter http.
     * @param filterHttp Indicator filter http.
     */
    public void setFilterHttp(final boolean filterHttp) {
        this.filterHttp = filterHttp;
    }

    /**
     * Indicates if we filter http.
     * @return <code>true</code> if filter, else <code>false</code>.
     */
    public boolean filterHttp() {
        return filterHttp;
    }

    /**
     * Set the user agent.
     * @param httpUserAgent The user agent.
     */
    public void setHttpUserAgent(final String httpUserAgent) {
        this.httpUserAgent = httpUserAgent;
    }

    /**
     * Get the http user agent.
     * @return httpUserAgent The http user agent.
     */
    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    /**
     * Indicates if cookies are enabled by default.
     * @return <code>true</code> if enabled, else <code>false</code>.
     */
    public boolean isEnableCookiesByDefault() {
        return enableCookiesByDefault;
    }

    /**
     * Gets the http version.
     * @return version The http version.
     */
    public String getHttpVersion() {
        return HTTP_VERSION;
    }

    /** Increase the number of connections counter. */
    public void increaseNumConnections() {
        numconnections++;
    }

    /** Decrease the number of connections counter. */
    public void decreaseNumConnections() {
        numconnections--;
    }

    /**
     * Gets the proxy version.
     * @return version The proxy version.
     */
    public String getVersion() {
        return VERSION;
    }

    /**
     * Gets the identification.
     * @return identification The Server identification.
     */
    public String getIdentification() {
        return "proxy/" + VERSION;
    }

    /**
     * Add bytes written.
     * @param written The number of bytes written.
     */
    public void addBytesWritten(int written) {
        byteswritten += written;
    }

    /**
     * Add bytes read.
     * @param read The number of bytes read.
     */
    public void addBytesRead(long read) {
        bytesread += read;
    }


    @Override
    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
            serverRunning = true;
        }

        openServerSocket();
        while (serverRunning) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
                new Session(this, clientSocket);
            } catch (IOException e) {
                if (isStopped()) {
                    LOG.debug("Proxy stopped.");
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            new Thread(new Session(this, clientSocket)).start();
        }
        LOG.debug("Proxy stopped.");
    }

    public synchronized boolean isStopped() {
        return !serverRunning;
    }

    public synchronized void stop() {
        serverRunning = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + port, e);
        }
    }
}
