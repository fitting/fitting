package org.fitting.fixture;

import org.fitting.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/** Test class for SeleniumNavigationFixture. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FitnesseContainer.class, SeleniumFixture.class, WebDriverUtil.class, SeleniumWindow.class})
public class SeleniumNavigationFixtureTest {
    private SeleniumNavigationFixture fixture; // class under test

    @Mock
    private FitnesseContext context;
    @Mock
    private WebDriver driver;
    @Mock
    private SeleniumWindow window;

    @Before
    public void setUp() throws Exception {
        mockStatic(FitnesseContainer.class);
        mockStatic(WebDriverUtil.class);
        when(FitnesseContainer.get()).thenReturn(context);
        when(context.getActiveWindow()).thenReturn(window);
        when(context.getDriver()).thenReturn(driver);
        fixture = new SeleniumNavigationFixture();
    }

    @Test
    public void shouldRefresh() throws Exception {
        fixture.refresh();
        verifyStatic();
        WebDriverUtil.refresh(isA(WebDriver.class));
    }

    @Test
    public void shouldSwitchToIframeWithId() throws Exception {
        fixture.switchToIframeWithId("id");
        verify(window, times(1)).selectFrameWithId(eq("id"), isA(WebDriver.class));
    }

    @Test
    public void shouldSwitchToMainFrame() throws Exception {
        fixture.switchToMainFrame();
        verify(window, times(1)).selectMainFrame(isA(WebDriver.class));
    }

    @Test
    public void shouldSwitchToFrameWithId() throws Exception {
        fixture.switchToFrameWithId("id");
        verify(window, times(1)).selectFrameWithId(eq("id"), isA(WebDriver.class));
    }

    @Test
    public void shouldSwitchToIframeWithName() throws Exception {
        fixture.switchToIframeWithName("name");
        verify(window, times(1)).selectFrameWithName(eq("name"), isA(WebDriver.class));
    }

    @Test
    public void shouldSwitchToFrameWithName() throws Exception {
        fixture.switchToFrameWithName("name");
        verify(window, times(1)).selectFrameWithName(eq("name"), isA(WebDriver.class));
    }

    @Test
    public void shouldOpenUrl() throws Exception {
        fixture.open("<a href=\"http://www.fitting-test.org\">http://www.fitting-test.org</a>");
        verifyStatic();
        WebDriverUtil.open(isA(WebDriver.class), eq("<a href=\"http://www.fitting-test.org\">http://www.fitting-test.org</a>"));
    }

    @Test
    public void shouldOpenUrlWithParameterAndValue() throws Exception {
        fixture.openUrlWithParameterAndValue("<a href=\"http://www.fitting-test.org\">http://www.fitting-test.org?x=y</a>",
                "param", "value");
        verifyStatic();
        WebDriverUtil.openUrlWithParameterAndValue(isA(WebDriver.class),
                eq("<a href=\"http://www.fitting-test.org\">http://www.fitting-test.org?x=y</a>"), eq("param"), eq("value"));
    }

    @Test
    public void shouldClickOkForAlertWindow() {
        when(WebDriverUtil.isAlertWindowPresent(isA(WebDriver.class))).thenReturn(true);
        fixture.clickOkForAlertWindow();

        verifyStatic();
        WebDriverUtil.isAlertWindowPresent(isA(WebDriver.class));
        WebDriverUtil.acceptAlertWindow(isA(WebDriver.class));
    }

    @Test
    public void shouldNotClickOkForAlertWindowWithNoWindowPresent() {
        when(WebDriverUtil.isAlertWindowPresent(isA(WebDriver.class))).thenReturn(false);

        fixture.clickOkForAlertWindow();

        verifyStatic();
        WebDriverUtil.isAlertWindowPresent(isA(WebDriver.class));
        verifyStatic(never());
        WebDriverUtil.acceptAlertWindow(isA(WebDriver.class));
    }

    @Test
    public void shouldClickCancelForAlertWindow() {
        when(WebDriverUtil.isAlertWindowPresent(isA(WebDriver.class))).thenReturn(true);
        fixture.clickCancelForAlertWindow();

        verifyStatic();
        WebDriverUtil.isAlertWindowPresent(isA(WebDriver.class));
        WebDriverUtil.dismissAlertWindow(isA(WebDriver.class));
    }

    @Test
    public void shouldNotClickCancelForAlertWindowWithNoWindowPresent() {
        when(WebDriverUtil.isAlertWindowPresent(isA(WebDriver.class))).thenReturn(false);

        fixture.clickCancelForAlertWindow();

        verifyStatic();
        WebDriverUtil.isAlertWindowPresent(isA(WebDriver.class));
        verifyStatic(never());
        WebDriverUtil.dismissAlertWindow(isA(WebDriver.class));
    }

    @Test
    public void shouldGetTextFromAlertWindow() {
        when(WebDriverUtil.getAlertWindowText(isA(WebDriver.class))).thenReturn("org/fitting/test");

        assertEquals("org/fitting/test", fixture.textFromAlertWindow());
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionForGettingTextFromAlertWindowWithNoAlertWindowPresent() {
        when(WebDriverUtil.getAlertWindowText(isA(WebDriver.class))).thenThrow(new WebDriverException("no alert"));

        fixture.textFromAlertWindow();
    }
}
