package org.fitting;

import org.fitting.proxy.client.ProxyClient;
import org.fitting.proxy.domain.Proxy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.URL;

import static org.fitting.util.ReflectionUtility.extract;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProxyClient.class, RemoteWebDriver.class, FitnesseConfiguration.class, SeleniumConnector.class})
public class SeleniumConnectorTest {
    private static final String SELENIUM_SERVER_PORT = "4444";
    private static final String LOCAL_HOST = "localhost";
    private static final String LOCAL_HOST_IP = "127.0.0.1";
    private static final String PLATFORM = "windows";
    private static final String VERSION = "1.0";
    private static final String BROWSER_FIREFOX = "firefox";
    private static final String PROXY_SERVER_PORT = "10001";

    @Mock
    private RemoteWebDriver driver;
    @Mock
    private FitnesseConfiguration configuration;
    @Mock
    private DesiredCapabilities capabilities;
    @Mock
    private ProxyClient client;

    @Before
    public void setUp() throws Exception {
        whenNew(RemoteWebDriver.class).withParameterTypes(URL.class, Capabilities.class).withArguments(any(URL.class),
                any(Capabilities.class)).thenReturn(driver);
        whenNew(ProxyClient.class).withParameterTypes(String.class, int.class).withArguments(anyString(),
                anyInt()).thenReturn(client);

        mockStatic(FitnesseConfiguration.class);
        when(FitnesseConfiguration.instance()).thenReturn(configuration);
        when(configuration.getDefaultServerHost()).thenReturn(LOCAL_HOST);
        when(configuration.getDefaultServerPort()).thenReturn(SELENIUM_SERVER_PORT);
        when(configuration.getCapabilities(anyString(), anyString(), anyString())).thenReturn(
                capabilities);
        when(configuration.getCapabilities(anyString(), anyString(), anyString(), anyBoolean()))
                .thenReturn(capabilities);
        when(client.reserve()).thenReturn(10000);
    }

    @Test
    public void shouldNotCreateProxyClientBecauseProxyPortIsNull() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).build();
        ProxyClient client = (ProxyClient) extract(connector, "proxyClient");
        assertNull(client);
    }

    @Test
    public void shouldNotCreateProxyClientBecauseProxyHostIsNull() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).withProxy(null, PROXY_SERVER_PORT).build();
        ProxyClient client = (ProxyClient) extract(connector, "proxyClient");
        assertNull(client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPortNotAnInteger() {
        SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).withProxy(LOCAL_HOST, "10000a").build();
    }

    @Test
    public void shouldCreateSeleniumConnectorWithValidProxyPort() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).withProxy(LOCAL_HOST, PROXY_SERVER_PORT).build();
        Proxy proxy = (Proxy) extract(connector, "proxy");

        assertEquals(10000, proxy.getPort());

        verify(client, times(1)).reserve();
    }

    @Test
    public void shouldCreateSeleniumConnectorWithoutProxy() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).build();

        assertFalse("Proxy created for connector where no proxy details were provided.", connector.isProxyAvailable());
    }

    @Test
    public void shouldCreateSeleniumConnector() {
        when(capabilities.isJavascriptEnabled()).thenReturn(true);
        final SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM)
                .withHost(LOCAL_HOST, SELENIUM_SERVER_PORT)
                .withBrowser(BROWSER_FIREFOX, VERSION)
                .withJavascriptEnabled(false)
                .build();

        verify(configuration, times(1)).getCapabilities(eq(PLATFORM), eq(BROWSER_FIREFOX), eq(VERSION));
        assertNotNull("No SeleniumConnector created from builder", connector);
        assertTrue(connector.isJavascriptEnabled());
    }

    @Test
    public void shouldCreateSeleniumConnectorWithProxy() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).withProxy(LOCAL_HOST_IP, PROXY_SERVER_PORT).build();

        assertTrue("No proxy created for connector where proxy details were provided.", connector.isProxyAvailable());
    }

    @Test
    public void shouldCreateSeleniumConnectorWithoutProxyForNullArguments() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).withProxy(null, null).build();

        assertFalse("Proxy created for connector where null proxy details were provided.", connector.isProxyAvailable());
    }

    @Test
    public void shouldAddIpDomainMapping() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).withProxy(LOCAL_HOST_IP, PROXY_SERVER_PORT).build();

        assertEquals("Ip domain mapping present without being added", 0, connector.getIpDomainMappings().size());

        connector.addIpDomainMapping(LOCAL_HOST_IP, LOCAL_HOST);

        assertEquals("No ip domain mapping present after adding", 1, connector.getIpDomainMappings().size());
    }

    @Test
    public void shouldNotAddIpDomainMappingWithoutProxy() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).build();

        assertEquals("Ip domain mapping present without being added", 0, connector.getIpDomainMappings().size());

        connector.addIpDomainMapping(LOCAL_HOST_IP, LOCAL_HOST);

        assertEquals("Ip domain mapping present after adding without a proxy", 0, connector.getIpDomainMappings()
                .size());
    }

    @Test
    public void shouldStartProxy() throws Exception {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).withProxy(LOCAL_HOST_IP, PROXY_SERVER_PORT).build();
        final Proxy proxy = (Proxy) extract(connector, "proxy");
        connector.startProxy();

        verify(client).start(proxy);
    }

    @Test
    public void shouldNotStartProxyWhenClientIsNull() throws Exception {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).build();
        final Proxy proxy = (Proxy) extract(connector, "proxy");
        connector.startProxy();

        verify(client, never()).start(proxy);
    }

    @Test
    public void shouldStopExistingProxyDuringDestroy() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).withProxy(LOCAL_HOST, PROXY_SERVER_PORT).build();
        final Proxy proxy = (Proxy) extract(connector, "proxy");
        connector.destroy();

        verify(driver).quit();
        verify(client).stop(proxy);
    }

    @Test
    public void shouldNotStopNonExistingProxyDuringDestroy() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).build();
        final Proxy proxy = (Proxy) extract(connector, "proxy");
        connector.destroy();

        verify(driver).quit();
        verify(client, times(0)).stop(proxy);
    }

    @Test
    public void shouldLoadDefaultHostAndPort() {
        SeleniumConnector.builder().withPlatform(PLATFORM).withBrowser(BROWSER_FIREFOX, VERSION).build();
        verify(configuration, times(1)).getDefaultServerHost();
        verify(configuration, times(1)).getDefaultServerPort();
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenUrlCannotBeConstructed() {
        SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost("in:valid", SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).build();
        fail("Connector created for an invalid URL");
    }

    @Test
    public void shouldGetDriver() {
        final SeleniumConnector connector = SeleniumConnector.builder().withPlatform(PLATFORM).withHost(LOCAL_HOST, SELENIUM_SERVER_PORT).withBrowser(BROWSER_FIREFOX, VERSION).build();
        assertEquals(driver, connector.getDriver());
    }
}
