package org.fitting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.name;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/** Test class for SeleniumWindow. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SeleniumWindow.class, RemoteWebDriver.class, WebDriverUtil.class})
public class SeleniumWindowTest {
    private SeleniumWindow window; // class under test.
    private static final String CURRENT_HANDLE = "http://test.fitting-test.org";
    private static final String NEW_WINDOWHANDLE = "http://www.fitting-test.org";

    @Mock
    private RemoteWebDriver driver;
    @Mock
    private WebDriver.TargetLocator targetLocator;
    @Mock
    private WebDriver.Navigation navigation;
    @Mock
    private WebElement element;
    @Mock
    private FitnesseContext context;

    private HashSet<String> windowHandles;

    @Before
    public void setUp() throws Exception {
        whenNew(RemoteWebDriver.class).withParameterTypes(URL.class, Capabilities.class).withArguments(isA(URL.class),
                isA(Capabilities.class)).thenReturn(driver);
        mockStatic(WebDriverUtil.class);

        when(driver.switchTo()).thenReturn(targetLocator);
        when(driver.navigate()).thenReturn(navigation);

        windowHandles = new HashSet<String>();
        windowHandles.add(CURRENT_HANDLE);

        when(driver.getWindowHandles()).thenReturn(windowHandles);
        when(driver.getWindowHandle()).thenReturn(CURRENT_HANDLE);
        window = new SeleniumWindow("id", "parentId");

        FitnesseContainer.set(context);
    }

    @Test
    public void shouldInitialize() throws Exception {
        assertEquals("id", window.getId());
        assertEquals("parentId", window.getParentId());
    }

    @Test
    public void shouldCreateNewWindow() throws Exception {
        final HashSet<String> newHandles = new HashSet<String>();
        newHandles.add(CURRENT_HANDLE);
        newHandles.add(NEW_WINDOWHANDLE);

        when(driver.getWindowHandles()).thenReturn(windowHandles).thenReturn(newHandles);
        SeleniumWindow.createNewWindow(NEW_WINDOWHANDLE, driver);
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenDriverNotAssignableToJavascriptExecutor() throws Exception {
        SeleniumWindow.createNewWindow(NEW_WINDOWHANDLE, new NonJavascriptExecutorDriver());
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenDriverWhenNoNewHandles() throws Exception {
        final HashSet<String> newHandles = new HashSet<String>();
        newHandles.add(CURRENT_HANDLE);

        when(driver.getWindowHandles()).thenReturn(windowHandles).thenReturn(newHandles);
        SeleniumWindow.createNewWindow(CURRENT_HANDLE, driver);
    }

    @Test
    public void shouldSwitchToFrameById() throws Exception {
        window.selectFrameWithId(CURRENT_HANDLE, driver);

        assertEquals(id(CURRENT_HANDLE), window.getSelectedFrameSelectClause());
        verifyStatic();
        WebDriverUtil.switchToFrame(any(WebDriver.class), eq(id(CURRENT_HANDLE)));
    }

    @Test
    public void shouldSwitchToFrameByName() throws Exception {
        window.selectFrameWithName(CURRENT_HANDLE, driver);

        assertEquals(name(CURRENT_HANDLE), window.getSelectedFrameSelectClause());
        verifyStatic();
        WebDriverUtil.switchToFrame(any(WebDriver.class), eq(name(CURRENT_HANDLE)));
    }

    @Test
    public void shouldSwitchToMainFrame() throws Exception {
        window.selectMainFrame(driver);

        assertEquals(name("_top"), window.getSelectedFrameSelectClause());
        verifyStatic();
        WebDriverUtil.switchToMainFrame(driver);
    }

    @Test
    public void shouldGetSelectedFrameSelectClause() throws Exception {
        window.selectFrameWithId(CURRENT_HANDLE, driver);
        assertEquals(id(CURRENT_HANDLE), window.getSelectedFrameSelectClause());
        window.selectFrameWithName(CURRENT_HANDLE, driver);
        assertEquals(name(CURRENT_HANDLE), window.getSelectedFrameSelectClause());
    }

    @Test
    public void shouldSetAndGetSelectedElement() throws Exception {
        window.setSelectedWebElement(element);
        assertEquals(element, window.getSelectedWebElement());
    }

    private class NonJavascriptExecutorDriver implements WebDriver {
        @Override
        public void get(String s) {

        }

        @Override
        public String getCurrentUrl() {
            return null;
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public List<WebElement> findElements(By by) {
            return null;
        }

        @Override
        public WebElement findElement(By by) {
            return null;
        }

        @Override
        public String getPageSource() {
            return null;
        }

        @Override
        public void close() {

        }

        @Override
        public void quit() {

        }

        @Override
        public Set<String> getWindowHandles() {
            return null;
        }

        @Override
        public String getWindowHandle() {
            return null;
        }

        @Override
        public TargetLocator switchTo() {
            return null;
        }

        @Override
        public Navigation navigate() {
            return null;
        }

        @Override
        public Options manage() {
            return null;
        }
    }
}
