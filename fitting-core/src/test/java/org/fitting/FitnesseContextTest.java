package org.fitting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.base.Function;

/** Test class for FitnesseContext. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FitnesseContext.class, RemoteWebDriver.class, SeleniumConnector.class, SeleniumWindow.class, WebDriverWait.class})
public class FitnesseContextTest {
    @Mock
    private RemoteWebDriver driver;
    @Mock
    private WebDriver.TargetLocator targetLocator;
    @Mock
    private WebDriver.Navigation navigation;
    @Mock
    private SeleniumConnector seleniumConnector;
    @Mock
    private SeleniumWindow window;
    @Mock
    private HashSet<String> handles;
    @Mock
    private WebDriverWait webDriverWait;

    private static final String CURRENT_HANDLE = "http://test.fitting-test.org/";
    private static final String NEW_WINDOWHANDLE = "http://www.fitting-test.org";
    private static final String FAULTY_WINDOWHANDLE = "foo-bar-baz";

    @Before
    public void setUp() throws Exception {
        handles = new HashSet<String>();

        when(seleniumConnector.getDriver()).thenReturn(driver);
        when(driver.getWindowHandle()).thenReturn(CURRENT_HANDLE);

        mockStatic(SeleniumWindow.class);
        when(SeleniumWindow.createNewWindow(anyString(), any(WebDriver.class))).thenReturn(window);

        when(driver.switchTo()).thenReturn(targetLocator);
        when(driver.navigate()).thenReturn(navigation);
        when(driver.getWindowHandles()).thenReturn(handles);
        when(SeleniumWindow.switchToWindow(eq(FAULTY_WINDOWHANDLE), any(WebDriver.class))).thenThrow(WebDriverException.class);
        whenNew(WebDriverWait.class).withArguments(any(WebDriver.class), anyLong(), anyLong()).thenReturn(webDriverWait);
    }

    @Test
    public void shouldAddDomainMappingToProxy() throws Exception {
        new FitnesseContext(seleniumConnector).addIpDomainMapping("127.0.0.1", "localhost");
        verify(seleniumConnector, times(1)).addIpDomainMapping("localhost", "127.0.0.1");
    }

    @Test
    public void shouldStartProxy() throws Exception {
        new FitnesseContext(seleniumConnector).startProxy();
        verify(seleniumConnector, times(1)).startProxy();
    }

    @Test(expected = WebDriverException.class)
    public void shouldNotInitialize() {
        new FitnesseContext(null).startProxy();
    }

    @Test
    public void shouldDestroyContextAndStopProxy() throws Exception {
        FitnesseContext context = new FitnesseContext(seleniumConnector);
        context.startProxy();
        context.destroy();

        verify(seleniumConnector, times(1)).startProxy();
        verify(seleniumConnector, times(1)).destroy();
    }

    @Test
    public void shouldCloseActiveWindow() throws Exception {
        FitnesseContext context = new FitnesseContext(seleniumConnector);
        context.closeActiveWindow();
        verify(seleniumConnector, times(1)).destroy();
    }

    @Test
    public void shouldCreateNewWindow() throws Exception {
        when(window.getId()).thenReturn(NEW_WINDOWHANDLE);
        handles.add(NEW_WINDOWHANDLE);

        FitnesseContext context = new FitnesseContext(seleniumConnector);

        assertEquals(1, context.getWindowHandles().size());
        assertEquals(NEW_WINDOWHANDLE, context.createNewWindow(NEW_WINDOWHANDLE, true));
        assertEquals(2, context.getWindowHandles().size());
        assertEquals(window, context.getActiveWindow());
    }

    @Test
    public void shouldCreateNewWindowButMakeNotActive() throws Exception {
        when(window.getId()).thenReturn(NEW_WINDOWHANDLE);

        FitnesseContext context = new FitnesseContext(seleniumConnector);
        assertEquals(1, context.getWindowHandles().size());
        assertEquals(NEW_WINDOWHANDLE, context.createNewWindow(NEW_WINDOWHANDLE, false));
        assertEquals(2, context.getWindowHandles().size());
        assertTrue(!window.equals(context.getActiveWindow()));
    }

    @Test
    public void shouldCloseActiveWindows() throws Exception {
        when(window.getId()).thenReturn(NEW_WINDOWHANDLE);
        when(window.getParentId()).thenReturn(CURRENT_HANDLE);
        handles.add(NEW_WINDOWHANDLE);
        handles.add(CURRENT_HANDLE);

        FitnesseContext context = new FitnesseContext(seleniumConnector);
        assertEquals(1, context.getWindowHandles().size());
        assertEquals(NEW_WINDOWHANDLE, context.createNewWindow(NEW_WINDOWHANDLE, true));
        assertEquals(2, context.getWindowHandles().size());

        context.closeActiveWindow();
        verify(seleniumConnector, never()).destroy();
        assertEquals(1, context.getWindowHandles().size());
        assertEquals(CURRENT_HANDLE, context.getActiveWindow().getId());
    }

    @Test
    public void shouldCloseActiveWindowAndDestroyContext() throws Exception {
        FitnesseContext context = new FitnesseContext(seleniumConnector);
        assertEquals(1, context.getWindowHandles().size());

        context.closeActiveWindow();
        verify(seleniumConnector, times(1)).destroy();
    }

    @Test
    public void shouldActivateWindow() throws Exception {
        when(window.getId()).thenReturn(NEW_WINDOWHANDLE);
        handles.add(NEW_WINDOWHANDLE);
        handles.add(CURRENT_HANDLE);

        FitnesseContext context = new FitnesseContext(seleniumConnector);
        assertEquals(NEW_WINDOWHANDLE, context.createNewWindow(NEW_WINDOWHANDLE, false));

        assertEquals(2, context.getWindowHandles().size());
        assertFalse(window.equals(context.getActiveWindow()));

        context.activateWindow(NEW_WINDOWHANDLE);

        assertEquals(window, context.getActiveWindow());
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenActivatingWindowThatDoesNotExist() throws Exception {
        FitnesseContext context = new FitnesseContext(seleniumConnector);
        assertEquals(1, context.getWindowHandles().size());

        context.activateWindow(FAULTY_WINDOWHANDLE);
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenActivatingWindowTimesOut() throws Exception {
        when(window.getId()).thenReturn(NEW_WINDOWHANDLE);
        handles.add(NEW_WINDOWHANDLE);
        when(webDriverWait.until(any(Function.class))).thenThrow(TimeoutException.class);

        FitnesseContext context = new FitnesseContext(seleniumConnector);
        context.createNewWindow(NEW_WINDOWHANDLE, false);
        context.activateWindow(NEW_WINDOWHANDLE);
    }

    @Test
    public void shouldNotIndicateWindowIsActive() throws Exception {
        FitnesseContext context = new FitnesseContext(seleniumConnector);
        assertEquals(1, context.getWindowHandles().size());

        assertFalse(context.isActiveWindow("id"));
    }

    @Test
    public void shouldIndicateWindowIsActive() throws Exception {
        FitnesseContext context = new FitnesseContext(seleniumConnector);
        assertEquals(1, context.getWindowHandles().size());

        assertTrue(context.isActiveWindow(context.getWindowHandles().iterator().next()));
    }

    @Test
    public void shouldNotThrowExceptionWhenWindowHandleIsNull() throws Exception {
        FitnesseContext context = new FitnesseContext(seleniumConnector);
        assertEquals(1, context.getWindowHandles().size());

        assertFalse(context.isActiveWindow(null));
    }

    @Test
    public void shouldSwitchToMainWindow() throws Exception {
        when(window.getId()).thenReturn(NEW_WINDOWHANDLE);
        handles.add(NEW_WINDOWHANDLE);
        handles.add(CURRENT_HANDLE);

        FitnesseContext context = new FitnesseContext(seleniumConnector);
        assertEquals(1, context.getWindowHandles().size());
        assertEquals(NEW_WINDOWHANDLE, context.createNewWindow(NEW_WINDOWHANDLE, true));

        assertEquals(2, context.getWindowHandles().size());
        assertEquals(NEW_WINDOWHANDLE, context.getActiveWindow().getId());

        context.switchToMainWindow();
        assertEquals(CURRENT_HANDLE, context.getActiveWindow().getId());

        verify(driver, times(2)).switchTo();
        verify(targetLocator, times(2)).window(anyString());
    }
}
