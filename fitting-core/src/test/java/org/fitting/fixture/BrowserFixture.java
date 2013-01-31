package org.fitting.fixture;

import org.fitting.FitnesseContext;
import org.fitting.SeleniumConnector;

import static org.fitting.FitnesseContainer.get;
import static org.fitting.FitnesseContainer.set;

/**
 * Browser Fixture. Because we do not want to create a new WebDriver instance
 * for each test case, due to the fact that it takes a lot of time, this Fixture
 * enables us to open and close a WebDriver in the Setup and Teardown of each
 * Test or Suite.
 */
public class BrowserFixture {
    /**
     * Open the browser.
     * @param browser The browser (firefox, ie, opera, chrome).
     */
    public void openBrowser(final String browser) {
        openOnPlatformBrowserWithVersionOnHostAndPortAndUseProxyAndPort(null, browser, null, null, null, null, null);
    }

    /**
     * Open the browser.
     * @param browser   The browser (firefox, ie, opera, chrome).
     * @param proxyHost The host where the proxy is running.
     * @param proxyPort The port that the proxy is running on.
     */
    public void openBrowserAndUseProxyAndPort(final String browser, final String proxyHost, final String proxyPort) {
        openOnPlatformBrowserWithVersionOnHostAndPortAndUseProxyAndPort(null, browser, null, null, null, proxyHost, proxyPort);
    }

    /**
     * Open the browser.
     * @param browser The browser (firefox, ie, opera, chrome).
     * @param host    The host where the selenium server is running.
     * @param port    The port that the selenium server is running on.
     */
    public void openBrowserOnHostAndPort(final String browser, final String host, final String port) {
        openOnPlatformBrowserWithVersionOnHostAndPortAndUseProxyAndPort(null, browser, null, host, port, null, null);
    }

    /**
     * Open the browser.
     * @param browser   The browser (firefox, ie, opera, chrome).
     * @param host      The host where the selenium server is running.
     * @param port      The port that the selenium server is running on.
     * @param proxyHost The host where the proxy is running.
     * @param proxyPort The port that the proxy is running on.
     */
    public void openBrowserOnHostAndPortAndUseProxyAndPort(final String browser, final String host, final String port, final String proxyHost, final String proxyPort) {
        openOnPlatformBrowserWithVersionOnHostAndPortAndUseProxyAndPort(null, browser, null, host, port, proxyHost, proxyPort);
    }

    /**
     * Open the browser.
     * @param platform The OS platform (e.g. xp).
     * @param browser  The browser (firefox, ie, opera, chrome).
     * @param version  The browser version.
     * @param host     The host where the selenium server is running.
     * @param port     The port that the selenium server is running on.
     */
    public void openOnPlatformBrowserWithVersionOnHostAndPort(final String platform, final String browser, final String version, final String host, final String port) {
        openOnPlatformBrowserWithVersionOnHostAndPortAndUseProxyAndPort(platform, browser, version, host, port, null, null);
    }

    /**
     * Open the browser.
     * @param platform  The OS platform (e.g. xp).
     * @param browser   The browser (firefox, ie, opera, chrome).
     *                  * @param version  The browser version.
     * @param host      The host where the selenium server is running.
     * @param port      The port that the selenium server is running on.
     * @param proxyHost The host where the proxy is running.
     * @param proxyPort The port that the proxy is running on.
     */
    public void openOnPlatformBrowserWithVersionOnHostAndPortAndUseProxyAndPort(final String platform, final String browser, final String version, final String host,
            final String port, final String proxyHost, final String proxyPort) {
        final SeleniumConnector connector = SeleniumConnector.builder().withPlatform(platform).withBrowser(browser, version).withHost(host, port).withProxy(proxyHost, proxyPort)
                .build();
        final FitnesseContext context = new FitnesseContext(connector);
        set(context);
    }

    /** Start the actual proxy. */
    public void startProxy() {
        get().startProxy();
    }

    /** Closes the browser. */
    public void closeBrowser() {
        get().destroy();
    }

    /** Close the active window. (e.g. for use with window popups) */
    public void closeWindow() {
        get().closeActiveWindow();
    }
}
