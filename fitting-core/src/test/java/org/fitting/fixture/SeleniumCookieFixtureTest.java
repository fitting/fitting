package org.fitting.fixture;

import org.fitting.FitnesseContainer;
import org.fitting.FitnesseContext;
import org.fitting.SeleniumWindow;
import org.fitting.WebDriverUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.fitting.WebDriverUtil.addCookie;
import static org.fitting.WebDriverUtil.clearAllCookiesOnDomain;
import static org.fitting.WebDriverUtil.cookieValueContains;
import static org.fitting.WebDriverUtil.copyCookieToDomain;
import static org.fitting.WebDriverUtil.deleteCookieWithNameOnDomain;
import static org.fitting.WebDriverUtil.getCookieValue;
import static org.fitting.WebDriverUtil.isCookieWithNamePresentOnDomain;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/** Test class for SeleniumCookieFixture. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FitnesseContainer.class, FitnesseContext.class, SeleniumElementFixture.class, WebDriverUtil.class})
public class SeleniumCookieFixtureTest {
    private SeleniumCookieFixture fixture; // class under test.

    @Mock
    private FitnesseContext context;
    @Mock
    private SeleniumWindow window;
    @Mock
    private WebDriver driver;
    @Mock
    private WebElement element;
    @Mock
    private Cookie cookie;

    @Before
    public void setUp() throws Exception {
        mockStatic(FitnesseContainer.class);
        mockStatic(WebDriverUtil.class);
        when(FitnesseContainer.get()).thenReturn(context);
        when(context.getDriver()).thenReturn(driver);
        fixture = new SeleniumCookieFixture();
    }

    @Test
    public void testValueForCookieWithNameIs() throws Exception {
        fixture.valueForCookieWithNameIs("cookieName");
        verifyStatic();
        getCookieValue(isA(WebDriver.class), eq("cookieName"));
    }

    @Test
    public void testValueForCookieWithNameContains() throws Exception {
        fixture.valueForCookieWithNameContains("cookieName", "value");
        verifyStatic();
        cookieValueContains(isA(WebDriver.class), eq("cookieName"), eq("value"));
    }

    @Test
    public void testAddCookieWithNameAndValue() throws Exception {
        fixture.addCookieWithNameAndValue("cookieName", "value");
        verifyStatic();
        addCookie(eq(context), isA(WebDriver.class), eq("cookieName"), eq("value"), eq((String) null), eq((String) null));
    }

    @Test
    public void testAddCookieWithNameAndValueToDomain() throws Exception {
        fixture.addCookieWithNameAndValueToDomain("cookieName", "value", "domain");
        verifyStatic();
        addCookie(eq(context), isA(WebDriver.class), eq("cookieName"), eq("value"), eq((String) null), eq("domain"));
    }

    @Test
    public void testCopyCookieWithNameToDomain() throws Exception {
        fixture.copyCookieWithNameToDomain("cookieName", "domain");
        verifyStatic();
        copyCookieToDomain(eq(context), isA(WebDriver.class), eq("cookieName"), eq("domain"));
    }

    @Test
    public void testClearAllCookiesOnDomain() throws Exception {
        fixture.clearAllCookiesOnDomain("domain");
        verifyStatic();
        clearAllCookiesOnDomain(eq(context), isA(WebDriver.class), eq("domain"));
    }

    @Test
    public void testClearAllCookies() throws Exception {
        fixture.clearAllCookies();
        verifyStatic();
        clearAllCookiesOnDomain(eq(context), isA(WebDriver.class), eq((String) null));
    }

    @Test
    public void testDeleteCookieWithNameOnDomain() throws Exception {
        fixture.deleteCookieWithNameOnDomain("cookieName", "domain");
        verifyStatic();
        deleteCookieWithNameOnDomain(eq(context), isA(WebDriver.class), eq("cookieName"), eq("domain"));
    }

    @Test
    public void testDeleteCookieWithName() throws Exception {
        fixture.deleteCookieWithName("cookieName");
        verifyStatic();
        deleteCookieWithNameOnDomain(eq(context), isA(WebDriver.class), eq("cookieName"), eq((String) null));
    }

    @Test
    public void testcookieWithNameOnDomainIsPresent() throws Exception {
        fixture.cookieWithNameOnDomainIsPresent("cookieName", "domain");
        verifyStatic();
        isCookieWithNamePresentOnDomain(eq(context), isA(WebDriver.class), eq("cookieName"), eq("domain"));
    }

    @Test
    public void testCookieWithNameIsPresent() throws Exception {
        fixture.cookieWithNameIsPresent("cookieName");
        verifyStatic();
        isCookieWithNamePresentOnDomain(eq(context), isA(WebDriver.class), eq("cookieName"), eq((String) null));
    }
}
