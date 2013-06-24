package org.fitting.fixture;

import org.fitting.FitnesseContainer;
import org.fitting.FitnesseContext;
import org.fitting.WebDriverUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/** Test class for SeleniumTimingFixture. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FitnesseContainer.class, SeleniumFixture.class, WebDriverUtil.class})
public class SeleniumTimingFixtureTest {
    private SeleniumTimingFixture fixture; // class under test

    @Mock
    private FitnesseContext context;
    @Mock
    private WebDriver driver;


    @Before
    public void setUp() throws Exception {
        mockStatic(FitnesseContainer.class);
        mockStatic(WebDriverUtil.class);
        when(FitnesseContainer.get()).thenReturn(context);
        when(context.getDriver()).thenReturn(driver);
        fixture = new SeleniumTimingFixture();
    }

    @Test
    public void shouldWaitXSeconds() throws Exception {
        fixture.waitSeconds(1);
        verifyStatic();
        WebDriverUtil.waitXSecond(isA(WebDriver.class), eq(1));
    }

    @Test
    public void shouldWaitForElementWithIdPresentWithinSeconds() throws Exception {
        fixture.waitForElementWithIdPresentWithinSeconds("by", 1);
        verifyStatic();
        WebDriverUtil.waitForElementPresent(isA(WebDriver.class), isA(By.class), eq(1));
    }

    @Test
    public void shouldWaitForElementWithNamePresentWithinSeconds() throws Exception {
        fixture.waitForElementWithNamePresentWithinSeconds("by", 1);
        verifyStatic();
        WebDriverUtil.waitForElementPresent(isA(WebDriver.class), isA(By.class), eq(1));
    }

    @Test
    public void shouldWaitForElementWithClassNamePresentWithinSeconds() throws Exception {
        fixture.waitForElementWithClassNamePresentWithinSeconds("by", 1);
        verifyStatic();
        WebDriverUtil.waitForElementPresent(isA(WebDriver.class), isA(By.class), eq(1));
    }

    @Test
    public void shouldWaitForElementWithTagNamePresentWithinSeconds() throws Exception {
        fixture.waitForElementWithTagNamePresentWithinSeconds("by", 1);
        verifyStatic();
        WebDriverUtil.waitForElementPresent(isA(WebDriver.class), isA(By.class), eq(1));
    }

    @Test
    public void shouldWaitForElementWithXpathPresentWithinSeconds() throws Exception {
        fixture.waitForElementWithXpathPresentWithinSeconds("by", 1);
        verifyStatic();
        WebDriverUtil.waitForElementPresent(isA(WebDriver.class), isA(By.class), eq(1));
    }

    @Test
    public void shouldWaitForElementWithLinkTextPresentWithinSeconds() throws Exception {
        fixture.waitForElementWithLinkTextPresentWithinSeconds("by", 1);
        verifyStatic();
        WebDriverUtil.waitForElementPresent(isA(WebDriver.class), isA(By.class), eq(1));
    }
}
