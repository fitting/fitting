package org.fitting.fixture;

import org.fitting.FitnesseContainer;
import org.fitting.FitnesseContext;
import org.fitting.SeleniumConnector;
import org.fitting.proxy.client.ProxyClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.URL;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/** Test class for BrowserFixture. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BrowserFixture.class, FitnesseContainer.class, FitnesseContext.class, SeleniumConnector.class, SeleniumConnector.Builder.class})
public class BrowserFixtureTest {
    private BrowserFixture fixture; // class under test.

    @Mock
    private FitnesseContext context;
    @Mock
    private WebDriver driver;
    @Mock
    private WebDriver.TargetLocator locator;
    @Mock
    private SeleniumConnector browserTarget;
    @Mock
    private SeleniumConnector.Builder connectorBuilder;

    @Before
    public void setUp() throws Exception {
        mockStatic(FitnesseContainer.class);
        mockStatic(SeleniumConnector.class);
        mockStatic(SeleniumConnector.Builder.class);
        fixture = new BrowserFixture();
        when(FitnesseContainer.get()).thenReturn(context);
        when(SeleniumConnector.builder()).thenReturn(connectorBuilder);
        when(connectorBuilder.withBrowser(anyString(), anyString())).thenReturn(connectorBuilder);
        when(connectorBuilder.withJavascriptEnabled(anyBoolean())).thenReturn(connectorBuilder);
        when(connectorBuilder.withProxy(anyString(), anyString())).thenReturn(connectorBuilder);
        when(connectorBuilder.withHost(anyString(), anyString())).thenReturn(connectorBuilder);
        when(connectorBuilder.withPlatform(anyString())).thenReturn(connectorBuilder);
        when(connectorBuilder.build()).thenReturn(browserTarget);
        whenNew(SeleniumConnector.class)
                .withParameterTypes(DesiredCapabilities.class, URL.class, org.fitting.proxy.domain.Proxy.class, ProxyClient.class)
                .withArguments(isA(DesiredCapabilities.class), isA(URL.class), isA(org.fitting.proxy.domain.Proxy.class), isA(ProxyClient.class))
                .thenReturn(browserTarget);
        whenNew(FitnesseContext.class).withArguments(any(SeleniumConnector.class)).thenReturn(context);
        when(context.getDriver()).thenReturn(driver);
        when(browserTarget.getDriver()).thenReturn(driver);
        when(driver.switchTo()).thenReturn(locator);
        when(driver.getWindowHandle()).thenReturn("handle");
    }

    @Test
    public void shouldOpenBrowser() throws Exception {
        fixture.openBrowser("firefox");
        verify(connectorBuilder, times(1)).withBrowser(eq("firefox"), anyString());
        verifyNew(FitnesseContext.class, times(1));
    }

    @Test
    public void shouldOpenBrowserAndUseProxyAndPort() throws Exception {
        fixture.openBrowserAndUseProxyAndPort("firefox", "127.0.0.1", "9000");
        verify(connectorBuilder, times(1)).withBrowser(eq("firefox"), anyString());
        verify(connectorBuilder, times(1)).withProxy("127.0.0.1", "9000");
        verifyNew(FitnesseContext.class, times(1)).withArguments(any(SeleniumConnector.class));
    }

    @Test
    public void shouldOpenBrowserOnHostAndPort() {
        fixture.openBrowserOnHostAndPort("firefox", "ip", "port");
        verify(connectorBuilder, times(1)).withBrowser(eq("firefox"), anyString());
        verify(connectorBuilder, times(1)).withHost("ip", "port");
        PowerMockito.verifyNew(FitnesseContext.class, times(1));
    }

    @Test
    public void shouldOpenBrowserOnHostAndPortAndUseProxyAndPort() {
        fixture.openBrowserOnHostAndPortAndUseProxyAndPort("firefox", "ip", "port", "127.0.0.1", "9000");
        verify(connectorBuilder, times(1)).withHost("ip", "port");
        verify(connectorBuilder, times(1)).withBrowser(eq("firefox"), anyString());
        verify(connectorBuilder, times(1)).withProxy("127.0.0.1", "9000");
        verifyNew(FitnesseContext.class, times(1));
    }

    @Test
    public void shouldOpenOnPlatformBrowserWithVersionOnHostAndPort() throws Exception {
        fixture.openOnPlatformBrowserWithVersionOnHostAndPort("xp", "firefox", "8", "ip", "port");
        verify(connectorBuilder, times(1)).withPlatform("xp");
        verify(connectorBuilder, times(1)).withHost("ip", "port");
        verify(connectorBuilder, times(1)).withBrowser("firefox", "8");
        verify(connectorBuilder, times(1)).withProxy(null, null);
        verifyNew(FitnesseContext.class, times(1));
    }

    @Test
    public void shouldOpenOnPlatformBrowserWithVersionOnHostAndPortAndUseProxyAndPort() {
        fixture.openOnPlatformBrowserWithVersionOnHostAndPortAndUseProxyAndPort("xp", "firefox", "8", "ip", "port",
                "127.0.0.1", "9000");
        verify(connectorBuilder, times(1)).withPlatform("xp");
        verify(connectorBuilder, times(1)).withHost("ip", "port");
        verify(connectorBuilder, times(1)).withBrowser("firefox", "8");
        verify(connectorBuilder, times(1)).withProxy("127.0.0.1", "9000");
        verifyNew(FitnesseContext.class, times(1));
    }

    @Test
    public void shouldStartProxy() throws Exception {
        fixture.startProxy();
        verify(context).startProxy();
    }

    @Test
    public void shouldCloseBrowser() throws Exception {
        fixture.closeBrowser();
        verify(context).destroy();
    }

    @Test
    public void shouldCloseWindow() throws Exception {
        fixture.closeWindow();
        verify(context).closeActiveWindow();
        // TODO Update test to reflect multi-window.
    }
}
