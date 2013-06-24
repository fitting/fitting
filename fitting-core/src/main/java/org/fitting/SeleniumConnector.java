package org.fitting;

import org.fitting.proxy.client.ProxyClient;
import org.fitting.proxy.domain.DnsEntry;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.openqa.selenium.remote.CapabilityType.PROXY;

/**
 * Selenium connector.
 * Responsible for communication with selenium.
 */
public final class SeleniumConnector {
    private final org.fitting.proxy.domain.Proxy proxy;
    private final ProxyClient proxyClient;
    private final WebDriver webDriver;
    private final boolean javascriptEnabled;

    /**
     * Create a new selenium connector
     * @param capabilities The desired capabilities for the browser.
     * @param url The URL to connect to.
     * @param proxy The proxy.
     * @param proxyClient The proxy client or null.
     */
    private SeleniumConnector(final DesiredCapabilities capabilities, final URL url,
                              final org.fitting.proxy.domain.Proxy proxy, final ProxyClient proxyClient) {
        this.proxy = proxy;
        this.webDriver = new RemoteWebDriver(url, capabilities);
        this.javascriptEnabled = capabilities.isJavascriptEnabled();
        this.proxyClient = proxyClient;
    }

    /**
     * Adds an ip domain mapping if a proxy is available.
     * @param ip     The ip address.
     * @param domain The domain.
     */
    public void addIpDomainMapping(final String ip, final String domain) {
        if (this.isProxyAvailable()) {
            this.proxy.addDnsEntry(new DnsEntry(ip, domain));
        }
    }

    /**
     * Get the registered ip-domain mappings.
     * @return The ip domain mappings as a map with the ip as key and domain as value.
     */
    public Map<String, String> getIpDomainMappings() {
        Map<String, String> mappings = new HashMap<String, String>();
        for (DnsEntry entry : this.proxy.getDnsEntries()) {
            mappings.put(entry.getIp(), entry.getDomain());
        }
        return mappings;
    }

    /**
     * Indicates if the proxy is available.
     * @return <code>true</code> if available, else <code>false</code>.
     */
    public boolean isProxyAvailable() {
        return this.proxyClient != null;
    }

    /** Start the actual proxy using the client. */
    public void startProxy() {
        if (this.proxyClient != null) {
            this.proxyClient.start(this.proxy);
        }
    }

    /** Closed the webdriver and stop de proxy using the client. */
    public void destroy() {
        this.webDriver.quit();
        if (this.proxyClient != null) {
            this.proxyClient.stop(this.proxy);
        }
    }

    /**
     * Get the web driver.
     * @return driver The WebDriver.
     */
    public WebDriver getDriver() {
        return this.webDriver;
    }

    /**
     * Check if javascript is enabled for the browser/connector.
     * @return true if javascript is enabled.
     */
    public boolean isJavascriptEnabled() {
        return this.javascriptEnabled;
    }

    /**
     * Get a builder for the connector.
     * @return The builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for constructing a connector.
     */
    public static final class Builder {
        private static final String URL_MASK = "http://%s:%d/wd/hub";
        private static final String URL_PROTOCOL = "http://";
        private static final String HOST_PORT_SEPERATOR = ":";
        private static final String URL_BASE_PATH = "/wd/hub";

        private String platform;
        private String browser;
        private String version;
        private String host;
        private String port;
        private String proxyServerHost;
        private String proxyServerPort;
        private boolean javascript = true;

        /**
         * Set the platform the connector should target.
         * @param platform The platform.
         * @return The builder with the platform set.
         */
        public Builder withPlatform(final String platform) {
            this.platform = platform;

            return this;
        }

        /**
         * Provide the browser the connector should use.
         * @param browser The browser name.
         * @param version The browser version.
         * @return The builder with the browser set.
         */
        public Builder withBrowser(final String browser, final String version) {
            this.browser = browser;
            this.version = version;
            return this;
        }

        /**
         * Set the host the connector should connect to.
         * @param host The host.
         * @param port The port.
         * @return The builder with the host and port set.
         */
        public Builder withHost(final String host, final String port) {
            this.host = host;
            this.port = port;
            return this;
        }

        /**
         * Set whether Javascript should be enabled for the browser.
         * Defaults to true.
         *
         * @param javascriptEnabled true if javascript should be enabled, false if not.
         * @return The builder with javascript set.
         */
        public Builder withJavascriptEnabled(final boolean javascriptEnabled) {
            this.javascript = javascriptEnabled;
            return this;
        }

        /**
         * Provide the proxy to use.
         *
         * @param proxyServerHost The host of the proxy server.
         * @param proxyServerPort The port of the proxy server.
         *
         * @return The Builder instance with the proxy set.
         */
        public Builder withProxy(final String proxyServerHost, final String proxyServerPort) {
            this.proxyServerHost = proxyServerHost;
            this.proxyServerPort = proxyServerPort;
            return this;
        }

        /**
         * Build the connector from the provided values.
         *
         * @return The created SeleniumConnector.
         */
        public SeleniumConnector build() {
            final org.fitting.proxy.domain.Proxy proxy = new org.fitting.proxy.domain.Proxy();
            final FitnesseConfiguration configuration = FitnesseConfiguration.instance();
            final URL url = createWebDriverURL(this.host, this.port, configuration);
            final DesiredCapabilities capabilities = configuration.getCapabilities(platform, browser, version);
            capabilities.setJavascriptEnabled(javascript);
            final ProxyClient proxyClient = this.createProxyClientAndAddProxyCapabilities(proxyServerHost, proxyServerPort, proxy,
                    capabilities);

            return new SeleniumConnector(capabilities, url, proxy, proxyClient);
        }

        /**
         * Create a proxy client from the provided information, if applicable, and
         * if the client was succesfully created, add a matching selenium proxy to
         * the desired capabilities.
         * @param proxyServerHost The proxy server host.
         * @param proxyServerPort The proxy server port.
         * @param proxy           The proxy.
         * @param capabilities    The DesiredCapabilities.
         * @return The proxy client or null if none was created.
         */
        private ProxyClient createProxyClientAndAddProxyCapabilities(final String proxyServerHost,
                                                                     final String proxyServerPort,
                                                                     final org.fitting.proxy.domain.Proxy proxy,
                                                                     final DesiredCapabilities capabilities) {
            ProxyClient client = null;
            try {
                if (isNotEmpty(proxyServerHost) && isNotEmpty(proxyServerPort)) {
                    client = new ProxyClient(proxyServerHost, Integer.parseInt(proxyServerPort));
                    final Integer reservedPort = client.reserve();
                    proxy.setPort(reservedPort);

                    final String proxyUrl = proxyServerHost + HOST_PORT_SEPERATOR + proxy.getPort();
                    final Proxy seleniumProxy = new Proxy();
                    seleniumProxy.setHttpProxy(proxyUrl);
                    seleniumProxy.setSslProxy(proxyUrl);
                    capabilities.setCapability(PROXY, seleniumProxy);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Proxy server port " + proxyServerPort + " is not a valid port number.",
                        e);
            }
            return client;
        }

        /**
         * Construct the webdriver url based on the given parameters.
         * @param host          The host.
         * @param port          The port.
         * @param configuration The FitnesseConfiguration.
         * @return url The url.
         */
        private URL createWebDriverURL(final String host, final String port, final FitnesseConfiguration configuration) {
            try {
                final String serverHost = isEmpty(host) ? configuration.getDefaultServerHost() : host;
                final String serverPort = isEmpty(port) ? configuration.getDefaultServerPort() : port;
                return new URL(URL_PROTOCOL + serverHost + HOST_PORT_SEPERATOR + serverPort + URL_BASE_PATH);
            } catch (MalformedURLException e) {
                throw new WebDriverException("Could not construct a valid WebDriver URL for host " + host + " on port "
                        + port, e);
            }
        }
    }
}
