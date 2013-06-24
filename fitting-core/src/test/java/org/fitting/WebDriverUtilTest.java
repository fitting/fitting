package org.fitting;

import junit.framework.Assert;
import org.fitting.fixture.SeleniumElementFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.fitting.WebDriverUtil.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.Keys.ESCAPE;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/** Test class for WebDriverUtil. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FitnesseContainer.class, FitnesseContext.class, Select.class, SeleniumElementFixture.class,
        WebDriverUtil.class, SeleniumWindow.class})
public class WebDriverUtilTest {
    @Mock
    private WebDriver driver;
    @Mock
    private WebElement element;
    @Mock
    private WebDriver.Options options;
    @Mock
    private WebDriver.TargetLocator locator;
    @Mock
    private WebDriver.Navigation navigation;
    @Mock
    private NotSuchElementCallback callback;
    @Mock
    private FitnesseContext context;
    @Mock
    private Cookie cookie;
    @Mock
    private Select select;
    @Mock
    private Alert alert;

    private static final By by = By.id("id");

    @Before
    public void setUp() throws Exception {
        when(driver.findElement(by)).thenReturn(element);
        when(element.findElement(by)).thenReturn(element);
        when(driver.findElements(by)).thenReturn(asList(new WebElement[] {element}));
        when(element.findElements(by)).thenReturn(asList(new WebElement[] {element}));
        when(element.findElements(isA(By.class))).thenReturn(asList(new WebElement[] {element}));

        when(driver.manage()).thenReturn(options);
        when(driver.switchTo()).thenReturn(locator);
        when(locator.alert()).thenReturn(alert);
        when(options.getCookieNamed("name")).thenReturn(cookie);
        when(driver.getWindowHandle()).thenReturn("handle");
    }

    @Test
    public void shouldOpenAndStripUrl() throws Exception {
        open(driver, "<a href=\"http://www.fitting-test.org\">http://www.fitting-test.org</a>");
        verify(driver).get("http://www.fitting-test.org");
    }

    @Test
    public void shouldOpenAndNotStripUrl() throws Exception {
        open(driver, "http://www.fitting-test.org");
        verify(driver).get("http://www.fitting-test.org");
    }

    @Test
    public void shouldOpenAndNotStripUrlBecauseTagsDontMatch() throws Exception {
        open(driver, "<a href=\"http://www.fitting-test.org\">http://www.fitting-test.org</b>");
        verify(driver).get("<a href=\"http://www.fitting-test.org\">http://www.fitting-test.org</b>");
    }

    @Test
    public void shouldOpenAndStripUrlAndAddParam() throws Exception {
        openUrlWithParameterAndValue(driver, "<a href=\"http://www.fitting-test.org\">http://www.fitting-test.org</a>", "param", "value");
        verify(driver).get("http://www.fitting-test.org?param=value");
    }

    @Test
    public void shouldOpenAndNotStripUrlAndAddParam() throws Exception {
        openUrlWithParameterAndValue(driver, "http://www.fitting-test.org?x=y", "param", "value");
        verify(driver).get("http://www.fitting-test.org?x=y&param=value");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotHaveElementPresent() throws Exception {
        when(driver.findElement(by)).thenThrow(NoSuchElementException.class);
        assertFalse(isElementPresent(driver, by));
        verify(driver).findElement(by);
    }

    @Test
    public void shouldHaveElementPresent() throws Exception {
        assertTrue(isElementPresent(driver, by));
        verify(driver).findElement(by);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotHaveSubElementPresent() throws Exception {
        when(element.findElement(by)).thenThrow(NoSuchElementException.class);
        assertFalse(isElementPresent(element, by));
        verify(element).findElement(by);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = WebDriverException.class)
    public void shouldThrowWebDriverExceptionWhenSearchContextIsNull() throws Exception {
        when(driver.findElement(by)).thenThrow(NoSuchElementException.class);
        isElementPresent(null, by);
    }

    @Test
    public void shouldHaveSubElementPresent() throws Exception {
        assertTrue(isElementPresent(element, by));
        verify(element).findElement(by);
    }

    @Test
    public void shouldHaveElementDisplayed() {
        when(driver.findElement(by)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(true);

        assertTrue(isElementDisplayed(driver, by));
        verify(driver, times(1)).findElement(by);
        verify(element, times(1)).isDisplayed();
    }

    @Test
    public void shouldNotHaveElementDisplayedForUndisplayedElement() {
        when(driver.findElement(by)).thenReturn(element);
        when(element.isDisplayed()).thenReturn(false);

        assertFalse(isElementDisplayed(driver, by));
        verify(driver, times(1)).findElement(by);
        verify(element, times(1)).isDisplayed();
    }

    @Test
    public void shouldNotHaveElementDisplayedForNonExistingElement() {
        when(driver.findElement(by)).thenThrow(NoSuchElementException.class);

        assertFalse(isElementDisplayed(driver, by));
    }

    @Test(expected = org.fitting.WebDriverException.class)
    public void shouldThrowWebDriverExceptionForElementDisplayedWithoutContext() {
        isElementDisplayed(null, by);
        fail("Exception expected for element displayed call without a search context.");
    }

    @SuppressWarnings("unchecked")
    @Test(expected = WebDriverException.class)
    public void shouldThrowWebDriverExceptionWhenGetElement() throws Exception {
        when(driver.findElement(by)).thenThrow(NoSuchElementException.class);
        getElement(driver, by);
    }

    @Test
    public void shouldGetElement() throws Exception {
        assertEquals(element, getElement(driver, by));
        verify(driver).findElement(by);
    }

    @Test
    public void shouldNotGetElement() throws Exception {
        when(driver.findElement(by)).thenThrow(NoSuchElementException.class);
        assertEquals(null, getElement(driver, by, callback));
        verify(driver).findElement(by);
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenGetElementAndParentIsNull() throws Exception {
        getElement(null, by);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = WebDriverException.class)
    public void shouldThrowWebDriverExceptionWhenGetSubElement() throws Exception {
        when(element.findElement(by)).thenThrow(NoSuchElementException.class);
        getElement(element, by);
    }

    @Test
    public void shouldGetSubElement() throws Exception {
        assertEquals(element, getElement(element, by));
        verify(element).findElement(by);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotGetElements() throws Exception {
        when(driver.findElements(by)).thenThrow(NoSuchElementException.class);
        assertEquals(0, getElements(driver, by).size());
        verify(driver).findElements(by);
    }

    @Test
    public void shouldGetElements() throws Exception {
        assertEquals(1, getElements(driver, by).size());
        verify(driver).findElements(by);
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenGetElementsAndParentIsNull() throws Exception {
        getElements(null, by);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotGetSubElements() throws Exception {
        when(element.findElements(by)).thenThrow(NoSuchElementException.class);
        assertEquals(0, getElements(element, by).size());
        verify(element).findElements(by);
    }

    @Test
    public void shouldGetSubElements() throws Exception {
        assertEquals(1, getElements(element, by).size());
        verify(element).findElements(by);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldWaitXSeconds() throws Exception {
        long start = System.currentTimeMillis();
        when(driver.findElement(any(By.class))).thenThrow(NoSuchElementException.class);
        waitXSecond(driver, 1);
        verify(driver, times(5)).findElement(any(By.class));
        long secondsPassed = (System.currentTimeMillis() - start) / 1000;
        assertEquals(1, secondsPassed);
    }

    @Test
    public void shouldWaitForElementPresent() {
        when(driver.findElement(by)).then(new Answer<WebElement>() {
            @Override
            public WebElement answer(final InvocationOnMock invocationOnMock) throws Throwable {
                Thread.currentThread().sleep(2500);
                return element;
            }
        });
        long start = System.currentTimeMillis();
        boolean present = waitForElementPresent(driver, by, 5);
        long secondsPassed = (System.currentTimeMillis() - start) / 1000;

        assertTrue(present);
        verify(driver, times(1)).findElement(by);
        assertEquals(2, secondsPassed);
    }

    @Test
    public void shouldNotBePresentAfterWaitPeriod() throws Exception {
        long start = System.currentTimeMillis();
        when(driver.findElement(any(By.class))).thenReturn((WebElement) null);
        waitForElementPresent(driver, by, 1, callback);
        verify(driver, times(5)).findElement(any(By.class));
        long secondsPassed = (System.currentTimeMillis() - start) / 1000;
        assertEquals(1, secondsPassed);
    }


    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotWaitXSecondsBecauseElementWasFoundBeforeTimePassed() throws Exception {
        long start = System.currentTimeMillis();
        when(driver.findElement(any(By.class))).thenThrow(NoSuchElementException.class).thenReturn(element);
        waitXSecond(driver, 1);
        verify(driver, times(2)).findElement(any(By.class));
        long secondsPassed = (System.currentTimeMillis() - start) / 1000;
        assertEquals(0, secondsPassed);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = WebDriverException.class)
    public void shouldNotGetCookieValue() throws Exception {
        when(options.getCookieNamed("cookie-name")).thenThrow(NullPointerException.class);
        getCookieValue(driver, "cookie-name");
        verify(driver).manage();
        verify(options).getCookieNamed("cookie-name");
    }

    @Test
    public void shouldIndicateIfCookieValueContains() throws Exception {
        when(driver.manage()).thenReturn(options);
        when(options.getCookieNamed("cookie-name")).thenReturn(cookie);
        when(cookie.getValue())
                .thenReturn("not containing value") // first does not contain value
                .thenReturn("cookie-value"); // second contains value

        assertFalse(cookieValueContains(driver, "cookie-name", "cookie-value"));
        assertTrue(cookieValueContains(driver, "cookie-name", "cookie-value"));
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhileIndicatingCookieValueContainsWhenNoElementIsSelected() throws Exception {
        cookieValueContains(driver, "cookie-name", "cookie-value");
    }

    @Test
    public void shouldGetCookieValue() throws Exception {
        when(options.getCookieNamed("cookie-name")).thenReturn(cookie);
        when(cookie.getValue()).thenReturn("cookie-value");
        assertEquals("cookie-value", getCookieValue(driver, "cookie-name"));
        verify(driver).manage();
        verify(options).getCookieNamed("cookie-name");
        verify(cookie).getValue();
    }

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException {
        assertEquals(1, WebDriverUtil.class.getDeclaredConstructors().length);
        Constructor<WebDriverUtil> constructor = WebDriverUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (Exception e) {
            fail();
        }
    }

    @SuppressWarnings("unchecked")
    @Test(expected = WebDriverException.class)
    public void shouldNotGetAttributeValue() throws Exception {
        getAttributeValue(null, "attribute-name");
    }

    @Test
    public void shouldGetAttributeValue() throws Exception {
        when(element.getAttribute(isA(String.class))).thenReturn("attribute-value");
        assertEquals("attribute-value", getAttributeValue(element, "attribute-name"));
        verify(element).getAttribute("attribute-name");
    }

    @Test
    public void shouldGetAttributeValueContains() throws Exception {
        when(element.getAttribute(isA(String.class))).thenReturn("attribute-value");
        assertTrue(attributeValueContains(element, "attribute-name", "value"));
        verify(element).getAttribute("attribute-name");
    }

    @SuppressWarnings("unchecked")
    @Test(expected = WebDriverException.class)
    public void shouldNotGetTextValue() throws Exception {
        getTextValue(null);
    }

    @Test
    public void shouldGetTextValue() throws Exception {
        when(element.getText()).thenReturn("text-value");
        assertEquals("text-value", getTextValue(element));
        verify(element).getText();
    }

    @Test
    public void shouldGetTextValueContains() throws Exception {
        when(element.getText()).thenReturn("text-value");
        assertTrue(textValueContains(element, "value"));
        verify(element).getText();
    }

    @Test
    public void shouldFindElementAndGetTextValueContains() throws Exception {
        when(element.getText()).thenReturn("text-value");
        assertTrue(textValueContains(driver, by, "value"));
        verify(element).getText();
    }

    @Test
    public void shouldSelectOption() throws Exception {
        when(element.getTagName()).thenReturn("select");
        when(element.getAttribute("text")).thenReturn("text");
        selectOptionWithTextFromDropdownOrSelect(driver, by, "text");
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenNoSelectOptionWithTheGivenTextIsFound() throws Exception {
        when(element.getTagName()).thenReturn("select");
        when(element.findElements(isA(By.class))).thenThrow(NoSuchElementException.class);
        when(element.getAttribute("text")).thenReturn("text");
        selectOptionWithTextFromDropdownOrSelect(driver, by, "text");
    }

    @Test
    public void shouldThrowExceptionWithCallbackWhenNoSelectOptionWithTheGivenTextIsFound() throws Exception {
        when(element.getTagName()).thenReturn("select");
        when(element.findElements(isA(By.class))).thenThrow(NoSuchElementException.class);
        when(element.getAttribute("text")).thenReturn("text");
        final Counter counter = new Counter();
        try {
            selectOptionWithTextFromDropdownOrSelect(driver, by, "text", new NotSuchElementCallback() {
                @Override
                public void onNoSuchElementFound(Object... objects) {
                    counter.invoke();
                }
            });
            fail("No exception thrown for an element that couldn't be found.");
        } catch (Exception e) {
            assertEquals(1, counter.getCount());
            assertTrue("Thrown exception is not a WebDriverException", e instanceof WebDriverException);
        }
    }

    @Test
    public void shouldAddCookie() throws Exception {
        addCookie(context, driver, "name", "value", "path", "domain");
        verify(options).addCookie(new Cookie.Builder("name", "value").path("path").domain("domain").build());

        reset(context, options);

        when(options.getCookieNamed("name")).thenReturn(cookie);
        addCookie(context, driver, "name", "value", null, "domain");
        verify(options).addCookie(new Cookie.Builder("name", "value").domain("domain").build());

        reset(context, options);

        when(options.getCookieNamed("name")).thenReturn(cookie);
        addCookie(context, driver, "name", "value", null, null);
        verify(options).addCookie(new Cookie.Builder("name", "value").build());
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenBuildingCookieWhenDomainIsInvalid() throws Exception {
        addCookie(context, driver, "name", "value", null, ".bla");
    }

    @Test
    public void shouldCopyCookie() throws Exception {
        when(options.getCookieNamed("name")).thenReturn(cookie);
        when(cookie.getValue()).thenReturn("value");

        copyCookieToDomain(context, driver, "name", "domain");

        verify(options).addCookie(new Cookie.Builder("name", "value").build());
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenCookieNotFoundWhileCopyCookie() throws Exception {
        when(options.getCookieNamed("name")).thenReturn(null);

        copyCookieToDomain(context, driver, "name", "domain");
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenDomainIsInvalidWhileCopyCookie() throws Exception {
        when(options.getCookieNamed("name")).thenReturn(cookie);
        when(cookie.getValue()).thenReturn("value");

        copyCookieToDomain(context, driver, "name", ".domain");
    }

    @Test
    public void shouldClearAllCookiesOnDomain() throws Exception {
        clearAllCookiesOnDomain(context, driver, "domain");
        verify(options).deleteAllCookies();
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenDomainIsInvalidWhileClearAllCookiesOnDomain() throws Exception {
        clearAllCookiesOnDomain(context, driver, ".domain");
    }

    @Test
    public void shouldDeleteCookieOnDomain() throws Exception {
        deleteCookieWithNameOnDomain(context, driver, "cookieName", "domain");
        verify(options).deleteCookieNamed("cookieName");
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenDomainIsInvalidWhileDeleteCookieOnDomain() throws Exception {
        deleteCookieWithNameOnDomain(context, driver, "cookieName", ".domain");
    }

    @Test
    public void shouldIndicateCookieWithNameIsPresentOnDomain() throws Exception {
        assertFalse(isCookieWithNamePresentOnDomain(context, driver, "cookieName", "domain"));
        verify(options).getCookieNamed("cookieName");
    }

    @Test
    public void testName() throws Exception {
        when(options.getCookieNamed("cookieName")).thenReturn(cookie);
        when(cookie.getValue()).thenReturn("value");
        assertTrue(isCookieWithNamePresentOnDomain(context, driver, "cookieName", "domain"));
        verify(options).getCookieNamed("cookieName");
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenDomainIsInvalidWhileIndicateCookieWithNameIsPresentOnDomain() throws Exception {
        isCookieWithNamePresentOnDomain(context, driver, "cookieName", ".domain");
    }

    @Test
    public void shouldClickElement() throws Exception {
        when(element.getTagName()).thenReturn("div");

        click(driver, element);

        verify(element, times(1)).click();
    }

    @Test
    public void shouldClickFormElement() throws Exception {
        when(element.getTagName()).thenReturn("input");

        click(driver, element);

        verify(driver, never()).switchTo();
        verify(locator, never()).window(isA(String.class));
        verify(element, never()).sendKeys("\n");
        verify(element, times(1)).click();
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhileClickWhenNoElementIsSelected() throws Exception {
        when(driver.switchTo()).thenReturn(locator);
        when(locator.window(isA(String.class))).thenReturn(driver);
        click(driver, null);
    }

    @Test
    public void shouldIndicatePageContainsValue() throws Exception {
        when(driver.getPageSource())
                .thenReturn("not containing value") // first does not contain value
                .thenReturn("page-value"); // second contains value

        Assert.assertFalse(pageContains(driver, "page-value"));
        assertTrue(pageContains(driver, "page-value"));
    }

    @Test
    public void shouldRefresh() throws Exception {
        when(driver.navigate()).thenReturn(navigation);

        refresh(driver);
        verify(driver).navigate();
        verify(navigation).refresh();
    }

    @Test
    public void shouldSwitchToFrame() throws Exception {
        when(locator.frame(any(WebElement.class))).thenReturn(driver);

        switchToFrame(driver, id("id"));
        verify(driver.switchTo().frame(any(WebElement.class)));
    }

    @Test
    public void shouldSwitchToMainFrame() throws Exception {
        switchToMainFrame(driver);
        verify(locator, times(1)).defaultContent();
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhileSwitchingToFrameWithIdWhenFrameNotFound() throws Exception {
        when(driver.findElement(any(By.class))).thenThrow(NoSuchElementException.class);
        when(locator.frame(any(WebElement.class))).thenReturn(driver);

        switchToFrame(driver, id("id"));
    }

    @Test
    public void shouldAcceptAlertWindow() throws Exception {
        acceptAlertWindow(driver);

        verify(alert, times(1)).accept();
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhileAcceptingAlertWindowWithNoAlertWindowFound() throws Exception {
        when(locator.alert()).thenThrow(new NoAlertPresentException("No alert window present."));

        acceptAlertWindow(driver);
    }

    @Test
    public void shouldDismissAlertWindow() throws Exception {
        dismissAlertWindow(driver);

        verify(alert, times(1)).dismiss();
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhileDismissingAlertWindowWithNoAlertWindowFound() throws Exception {
        when(locator.alert()).thenThrow(new NoAlertPresentException("No alert window present."));

        dismissAlertWindow(driver);
    }

    @Test
    public void shouldReportAlertWindowPresent() throws Exception {
        assertEquals("Alert window not reported present.", true, isAlertWindowPresent(driver));
    }

    @Test
    public void shouldReportAlertWindowNotPresentForNoWindow() throws Exception {
        when(locator.alert()).thenThrow(new NoAlertPresentException("No alert window present."));

        assertEquals("Alert window reported present for no window.", false, isAlertWindowPresent(driver));
    }

    @Test
    public void shouldGetTextFromAlertWindow() throws Exception {
        when(alert.getText()).thenReturn("org/fitting/test");

        assertEquals("Proper text not returned from alert window.", "org/fitting/test", getAlertWindowText(driver));
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhileGettingTextFromAlertWindowWithNoAlertWindowFound() throws Exception {
        when(locator.alert()).thenThrow(new NoAlertPresentException("No alert window present."));

        getAlertWindowText(driver);
    }

    @Test
    public void shouldSelectRadioButtonValue() throws Exception {
        when(driver.findElements(any(By.class))).thenReturn(asList(new WebElement[] { element, element }));
        when(element.getAttribute("value")).thenReturn("not-the-value").thenReturn("value");

        selectRadioButtonValue(driver, by, "value");
        verify(element).click();
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenNoRadioButtonElementsMatchNameSelectRadioButtonValue() throws Exception {
        when(driver.findElements(any(By.class))).thenReturn(asList(new WebElement[] {element, element}));
        when(element.getAttribute("value")).thenReturn("not-the-value").thenReturn("not-the-value-either");

        selectRadioButtonValue(driver, by, "value");
        verify(element).click();
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhenNoRadioButtonElementsAreFoundWhileSelectRadioButtonValue() throws Exception {
        when(driver.findElements(any(By.class))).thenThrow(NoSuchElementException.class);

        selectRadioButtonValue(driver, by, "value");
    }

    @Test
    public void shouldReportCheckboxCheckedWithEmptyValue() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);
        when(element.getAttribute("checked")).thenReturn("");

        assertTrue("Checkbox not properly reported as checked for empty value", isCheckboxChecked(driver, by));
    }

    @Test
    public void shouldReportCheckboxCheckedWithTrueValue() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);
        when(element.getAttribute("checked")).thenReturn("true");

        assertTrue("Checkbox not properly reported as checked for true value", isCheckboxChecked(driver, by));
    }

    @Test
    public void shouldReportCheckboxCheckedWithCheckedValue() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);
        when(element.getAttribute("checked")).thenReturn("checked");

        assertTrue("Checkbox not properly reported as checked for empty value", isCheckboxChecked(driver, by));
    }

    @Test
    public void shouldReportCheckboxNotCheckedWithNullValue() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);
        when(element.getAttribute("checked")).thenReturn(null);

        assertFalse("Checkbox not properly reported as not checked for null value", isCheckboxChecked(driver, by));
    }

    @Test
    public void shouldReportCheckboxNotCheckedWithNonStandardValue() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);
        when(element.getAttribute("checked")).thenReturn("asdf");

        assertFalse("Checkbox not properly reported as not checked for non standard value",
                isCheckboxChecked(driver, by));
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionForCheckingNonExistingCheckbox() throws Exception {
        when(driver.findElement(any(By.class))).thenThrow(NoSuchElementException.class);

        isCheckboxChecked(driver, by);
    }

    @Test
    public void shouldToggleCheckBox() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);

        toggleCheckbox(driver, by);
        verify(element, times(1)).click();
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionForTogglingNonExistingCheckbox() throws Exception {
        when(driver.findElement(any(By.class))).thenThrow(NoSuchElementException.class);

        toggleCheckbox(driver, by);
    }

    @Test
    public void shouldSetValueForInputWithName() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);

        setValueForInput(driver, by, "value");

        verify(element).clear();
        verify(element).sendKeys("value");
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhileSettingValueForInputWithNameWhenNameNotFound() throws Exception {
        when(driver.findElement(any(By.class))).thenThrow(NoSuchElementException.class);

        setValueForInput(driver, by, "value");
    }

    @Test
    public void shouldSetValueForInputWithId() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);

        setValueForInput(driver, by, "value");

        verify(element).clear();
        verify(element).sendKeys("value");
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhileSettingValueForInputWithIdWhenIdNotFound() throws Exception {
        when(driver.findElement(any(By.class))).thenThrow(NoSuchElementException.class);

        setValueForInput(driver, by, "value");
    }

    @Test
    public void shouldGetValueForInputWithName() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);
        when(element.getAttribute("value")).thenReturn("value");

        valueForInputIs(driver, by);
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhileGettingValueForInputWithNameWhenNameNotFound() throws Exception {
        when(driver.findElement(any(By.class))).thenThrow(NoSuchElementException.class);

        valueForInputIs(driver, by);
    }

    @Test
    public void shouldGetValueForInputWithId() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);
        when(element.getAttribute("value")).thenReturn("value");

        valueForInputIs(driver, by);
    }

    @Test(expected = WebDriverException.class)
    public void shouldThrowExceptionWhileGettingValueForInputWithIdWhenIdNotFound() throws Exception {
        when(driver.findElement(any(By.class))).thenThrow(NoSuchElementException.class);

        valueForInputIs(driver, by);
    }

    @Test
    public void shouldSelectOptionWithTextFromDropdown() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);
        whenNew(Select.class).withArguments(element).thenReturn(select);
        when(element.getTagName()).thenReturn("select");

        selectOptionWithTextFromDropdownOrSelect(driver, by, "text");

        verify(select).selectByVisibleText("text");
    }

    @Test
    public void shouldGetSelectedOptionTextFromDropdownIs() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);
        whenNew(Select.class).withArguments(element).thenReturn(select);
        when(select.getFirstSelectedOption()).thenReturn(element);
        when(element.getText()).thenReturn("text");

        assertEquals("text", selectedOptionTextFromDropdownOrSelectIs(driver, by));
    }

    @Test
    public void shouldSendWordToElementAsKeyStrokes() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);

        sendKeysToElement(driver, by, "org/fitting/test");

        verify(element, times(1)).sendKeys("org/fitting/test");
    }

    @Test
    public void shouldSendEscapeToElementAsKeyStrokes() throws Exception {
        when(driver.findElement(any(By.class))).thenReturn(element);

        sendFunctionKeyToElement(driver, by, ESCAPE);

        verify(element, times(1)).sendKeys(ESCAPE);
    }

    @Test
    public void shouldGetStrippedDomain() throws Exception {
        assertEquals("domain.tld", getStrippedDomain("domain.tld"));
        assertEquals("www.domain.tld", getStrippedDomain("www.domain.tld"));
        assertEquals("www.domain.tld", getStrippedDomain("http://www.domain.tld"));
        assertEquals("www.domain.tld", getStrippedDomain("https://www.domain.tld"));
        assertEquals("www.domain.tld", getStrippedDomain("https://www.domain.tld:8080"));
        assertEquals("www.domain.tld", getStrippedDomain("ftp://www.domain.tld"));
        assertEquals("www.domain.tld", getStrippedDomain("https://www.domain.tld/path"));
        assertEquals("www.domain.tld", getStrippedDomain("https://www.domain.tld:8080/path"));
        assertEquals("www.domain.tld", getStrippedDomain("https://www.domain.tld?queryString"));
    }

    private String getStrippedDomain(final String domain) {
        try {
            final Method method = WebDriverUtil.class.getDeclaredMethod("getStrippedDomain", String.class);
            method.setAccessible(true);
            return (String) method.invoke(null, domain);
        } catch (NoSuchMethodException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        } catch (IllegalAccessException e) {
            fail();
        }
        return null;
    }

    private static class Counter {
        private int counter = 0;

        public void invoke() {
            counter++;
        }

        public int getCount() {
            return counter;
        }
    }
}
