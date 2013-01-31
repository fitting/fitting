package org.fitting;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.openqa.selenium.By.id;

/** WebDriverUtil provides utility methods for selenium webdriver. */
public final class WebDriverUtil {
    private static final String UNKNOWN_ELEMENT_ID = "unknown";
    private static final String NO_PARENT_ELEMENT_SELECTED = "No parent element selected.";
    private static final String NO_ELEMENT_SELECTED = "No element selected.";
    private static final String NO_SUCH_ELEMENT_WITH_IDENTIFIER_FOUND = "No such element with identifier [%s] found.";
    private static final String NO_RADIOBUTTON_WITH_VALUE_FOUND = "No radio button with value [%s] found";
    private static final String NO_OPTION_WITH_VISIBLE_TEXT_FOUND = "No option with visible text [%s] found.";
    private static final String NO_VALID_DOMAIN = "Given domain [%s] is not valid.";
    private static final String NO_ALERT_WINDOW = "There is not alert window present.";
    private static final int DEFAULT_SEARCH_INTERVAL = 300;
    private static final Pattern DOMAIN_PATTERN = Pattern.compile("^\\w+://([^\\/:\\?]+)([\\w\\W])*$");

    /** static final class should not have public constructor. */
    private WebDriverUtil() {
    }

    /**
     * Opens the given url.
     * @param driver The WebDriver.
     * @param url    The url.
     */
    public static void open(final WebDriver driver, final String url) {
        final String strippedUrl = stripUrl(url);
        driver.get(strippedUrl);
    }

    /**
     * Opens an url and adds a param and value to the query string.
     * @param driver The WebDriver.
     * @param url    The url.
     * @param param  The param.
     * @param value  The value.
     */
    public static void openUrlWithParameterAndValue(final WebDriver driver, final String url, final String param,
            final String value) {
        final String strippedUrl = stripUrl(url);
        final StringBuilder urlBuilder = new StringBuilder(strippedUrl);
        final String paramPrefix = strippedUrl.contains("?") ? "&" : "?";
        urlBuilder.append(paramPrefix);
        urlBuilder.append(param);
        urlBuilder.append("=");
        urlBuilder.append(value);
        open(driver, urlBuilder.toString());
    }

    /**
     * Utility method that strips all html markup from the given parameter.
     * @param param The parameter.
     * @return strippedParam The parameter without the html markup.
     */
    public static String stripUrl(final String param) {
        final String strippedParam;
        if (param.startsWith("<a") && param.endsWith("</a>")) {
            strippedParam = param.split(">", 2)[1].split("<", 2)[0];
        } else {
            strippedParam = param;
        }
        return strippedParam;
    }

    /**
     * Indicates if the element we are looking for is present on the context.
     * @param context The search context, usually being a WebDriver or WebElement implementation.
     * @param by      The by to find the element for.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public static boolean isElementPresent(final SearchContext context, final By by) {
        boolean present = true;
        try {
            context.findElement(by); // if not found NSEE
        } catch (NullPointerException e) {
            throw new org.fitting.WebDriverException(NO_PARENT_ELEMENT_SELECTED, e);
        } catch (NoSuchElementException e) {
            present = false;
        }
        return present;
    }

    /**
     * Indicates if the element we are looking for is present on the context.
     * @param context The search context, usually being a WebDriver or WebElement implementation.
     * @param by      The by to find the element for.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public static boolean isElementDisplayed(final SearchContext context, final By by) {
        boolean visible = false;
        try {
            WebElement e = context.findElement(by); // if not found NSEE
            visible = e.isDisplayed();
        } catch (NullPointerException e) {
            throw new org.fitting.WebDriverException(NO_PARENT_ELEMENT_SELECTED, e);
        } catch (NoSuchElementException e) {
            visible = false;
        }
        return visible;
    }

    /**
     * Gets the element by the given By.
     * @param context The search context, usually being a WebDriver or WebElement implementation.
     * @param by      The by to find the element for.
     * @return element The web element.
     */
    public static WebElement getElement(final SearchContext context, final By by) {
        return getElement(context, by, new NotSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(final Object... objects) {
                throw new org.fitting.WebDriverException(format(NO_SUCH_ELEMENT_WITH_IDENTIFIER_FOUND, by.toString()));
            }
        });
    }

    /**
     * Gets the element by the given By.
     * @param context  The search context, usually being a WebDriver or WebElement implementation.
     * @param by       The by to find the element for.
     * @param callback The NotSuchElementCallback.
     * @return element The web element.
     */
    public static WebElement getElement(final SearchContext context, final By by,
            final NotSuchElementCallback callback) {
        try {
            return context.findElement(by);
        } catch (NullPointerException e) {
            throw new org.fitting.WebDriverException(NO_PARENT_ELEMENT_SELECTED, e);
        } catch (NoSuchElementException e) {
            callback.onNoSuchElementFound(by);
            return null;
        }
    }

    /**
     * Gets the elements by the given By.
     * @param context The search context, usually being a WebDriver or WebElement implementation.
     * @param by      The by to find the element for.
     * @return elements The web elements.
     */
    public static List<WebElement> getElements(final SearchContext context, final By by) {
        List<WebElement> elements;
        try {
            elements = context.findElements(by);
        } catch (NullPointerException e) {
            throw new org.fitting.WebDriverException(NO_PARENT_ELEMENT_SELECTED, e);
        } catch (NoSuchElementException e) {
            elements = new ArrayList<WebElement>();
        }
        return elements;
    }

    /**
     * Wait for the given milliseconds.
     * @param driver The webdriver.
     * @param x      the number of seconds to wait.
     */
    public static void waitXSecond(final WebDriver driver, final int x) {
        waitForElementPresent(driver, id(UNKNOWN_ELEMENT_ID), x, null);
    }

    /**
     * Waits for element present for the given seconds.
     * @param driver   The webdriver.
     * @param by       The by.
     * @param seconds  The seconds to wait.
     * @param callback The NotSuchElementCallback, may be null.
     * @return element True if the element was found within in time.
     */
    public static boolean waitForElementPresent(final WebDriver driver, final By by, final int seconds) {
        return waitForElementPresent(driver, by, seconds, null);
    }

    /**
     * Waits for element present for the given seconds.
     * @param driver   The webdriver.
     * @param by       The by.
     * @param seconds  The seconds to wait.
     * @param callback The NotSuchElementCallback, may be null.
     * @return element True if the element was found within in time.
     */
    public static boolean waitForElementPresent(final WebDriver driver, final By by, final String content, 
    		final int seconds) {
        return waitForElementPresent(driver,
                new ExpectedCondition<WebElement>() {
		            public WebElement apply(final WebDriver webDriver) {
		                boolean found = false;
		                WebElement e = driver.findElement(by);
		                if (e != null) {
		                        final String text = e.getText();
		                        found = text != null && text.contains(content);
		                }
		                return found ? e : null;
		            }
		        }, seconds, null);
    }

    /**
     * Waits for element present for the given seconds.
     * @param driver   The webdriver.
     * @param by       The by.
     * @param seconds  The seconds to wait.
     * @param callback The NotSuchElementCallback, may be null.
     * @return element True if the element was found within in time.
     */
    public static boolean waitForElementPresent(final WebDriver driver, final By by, final int seconds,
            final NotSuchElementCallback callback) {
        return waitForElementPresent(driver, 
        		new ExpectedCondition<WebElement>() {
                    public WebElement apply(final WebDriver webDriver) {
                        return driver.findElement(by);
                    }
                }, seconds, callback);
    }

    /**
     * Waits for element present for the given seconds.
     * @param driver   The webdriver.
     * @param expectedCondition The content that should be present.
     * @param seconds  The seconds to wait.
     * @param callback The NotSuchElementCallback, may be null.
     * @return element True if the element was found within in time.
     */
    public static <E> boolean waitForElementPresent(final WebDriver driver,
    		final ExpectedCondition<E> expectedCondition,
    		final int seconds, final NotSuchElementCallback callback) {
        boolean present = false;
        try {
            new WebDriverWait(driver, seconds, DEFAULT_SEARCH_INTERVAL).until(
                    expectedCondition);
            present = true;
        } catch (TimeoutException e) {
        	if (callback != null) {
        		callback.onNoSuchElementFound();
        	}
        }
        return present;
    }


    /**
     * Gets the attribute value of the given attribute name of the selected element.
     * @param element       The WebElement.
     * @param attributeName The attribute name.
     * @return value The value.
     */
    public static String getAttributeValue(final WebElement element, final String attributeName) {
        try {
            return element.getAttribute(attributeName);
        } catch (NullPointerException e) {
            throw new org.fitting.WebDriverException(NO_ELEMENT_SELECTED, e);
        }
    }

    /**
     * Indicates if the attribute value of the given attribute name of the selected element contains the given value.
     * @param element       The WebElement.
     * @param attributeName The attribute name.
     * @param contains      The value to contain.
     * @return <code>true</code> if the attribute value contains the given value, else <code>false</code>.
     */
    public static boolean attributeValueContains(final WebElement element, final String attributeName,
            final String contains) {
        return getAttributeValue(element, attributeName).contains(contains);
    }

    /**
     * Gets the text value for the selected element.
     * @param element The WebElement.
     * @return value The value.
     */
    public static String getTextValue(final WebElement element) {
        try {
            return element.getText();
        } catch (NullPointerException e) {
            throw new org.fitting.WebDriverException(NO_ELEMENT_SELECTED, e);
        }
    }

    /**
     * Indicates if the text value of the selected element contains the given value.
     * @param element  The WebElement.
     * @param contains The value to contain.
     * @return <code>true</code> if the text value contains the given value, else <code>false</code>.
     */
    public static boolean textValueContains(final WebElement element, final String contains) {
        return getTextValue(element).contains(contains);
    }

    /**
     * Indicates if the text value of an element contains the given value.
     * @param context The context to search the WebElement from.
     * @param by The By clause to search the element with.
     * @param contains The value to contain.
     * @return <code>true</code> if the text value contains the given value, else <code>false</code>.
     */
    public static boolean textValueContains(final SearchContext context, final By by, final String contains) {
        return textValueContains(getElement(context, by), contains);
    }

    /**
     * Clicks the selected element.
     * @param driver  The WebDriver.
     * @param element The WebElement.
     */
    public static void click(final WebDriver driver, final WebElement element) {
        try { // if the element is an anchor or form element then click
            element.click();
        } catch (NullPointerException e) {
            throw new org.fitting.WebDriverException(NO_ELEMENT_SELECTED, e);
        }
    }

    /**
     * Indicates if the given page contains the given value.
     * @param driver   The WebDriver.
     * @param contains The value to contain.
     * @return <code>true</code> if the pagecontains the given value, else <code>false</code>.
     */
    public static boolean pageContains(final WebDriver driver, final String contains) {
        return driver.getPageSource().contains(contains);
    }

    /**
     * Refresh the page.
     * @param driver The WebDriver.
     */
    public static void refresh(final WebDriver driver) {
        driver.navigate().refresh();
        waitXSecond(driver, 1);
    }

    /**
     * Switches the focus to frame or iFrame that matches the given byClause. * @param driver The WebDriver.
     * @param byClause The selection by-clause of the frame.
     */
    public static void switchToFrame(final WebDriver driver, final By byClause) {
        final WebElement element = getElement(driver, byClause);
        driver.switchTo().frame(element);
    }

    /**
     * Switch to the default frame.
     * @param driver The WebDriver.
     */
    public static void switchToMainFrame(final WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    /**
     * Press the ok/accept button on a javascript alert window.
     * @param driver The WebDriver.
     */
    public static void acceptAlertWindow(final WebDriver driver) {
        getAlertWindow(driver).accept();
    }

    /**
     * Dismiss a javascript alert window, by cancelling if possible.
     * @param driver The WebDriver.
     */
    public static void dismissAlertWindow(final WebDriver driver) {
        getAlertWindow(driver).dismiss();
    }

    /**
     * Get the text from the alert window.
     * @param driver The WebDriver.
     * @return The text.
     */
    public static String getAlertWindowText(final WebDriver driver) {
        return getAlertWindow(driver).getText();
    }

    /**
     * Check if an alert window is present.
     * @param driver The WebDriver.
     * @return <code>true</code> if an alert window is present.
     */
    public static boolean isAlertWindowPresent(final WebDriver driver) {
        boolean present;
        try {
            getAlertWindow(driver);
            present = true;
        } catch (org.fitting.WebDriverException e) {
            present = false;
        }
        return present;
    }

    /**
     * Get the alert window.
     * @param driver The WebDriver.
     * @return The alert window.
     * @throws org.fitting.WebDriverException When there was no alert window present.
     */
    private static Alert getAlertWindow(final WebDriver driver) throws org.fitting.WebDriverException {
        final Alert alert;
        try {
            alert = driver.switchTo().alert();
        } catch (NoAlertPresentException e) {
            throw new org.fitting.WebDriverException(NO_ALERT_WINDOW, e);
        }
        return alert;
    }

    /**
     * Select a radio button with the given by and value. Note: the value does not indicate the name shown on the
     * screen, it is the value of the radiobutton value.
     * @param driver The WebDriver.
     * @param by     The By.
     * @param value  The value of the radio button.
     */
    public static void selectRadioButtonValue(final WebDriver driver, final By by, final String value) {
        selectRadioButtonValue(driver, by, value, new NotSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(final Object... objects) {
                throw new org.fitting.WebDriverException(format(NO_SUCH_ELEMENT_WITH_IDENTIFIER_FOUND, by));
            }
        });
    }

    /**
     * Select a radio button with the given by and value. Note: the value does not indicate the name shown on the
     * screen, it is the value of the radiobutton value.
     * @param driver   The WebDriver.
     * @param by       The By.
     * @param value    The value of the radio button.
     * @param callback The ElementNotFoundCallback.
     */
    public static void selectRadioButtonValue(final WebDriver driver, final By by, final String value,
            final NotSuchElementCallback callback) {
        final List<WebElement> buttons = getElements(driver, by);
        if (!buttons.isEmpty()) {
            WebElement found = null;
            for (final WebElement button : buttons) {
                if (button.getAttribute("value").equals(value)) {
                    found = button;
                    break;
                }
            }
            if (found == null) {
                throw new org.fitting.WebDriverException(format(NO_RADIOBUTTON_WITH_VALUE_FOUND, value));
            }
            found.click();
        } else {
            callback.onNoSuchElementFound();
        }
    }

    /**
     * Check if a checkbox is checked.
     * @param context The search context to find the checkbox on.
     * @param by      The By-clause to find the checkbox with.
     * @return <code>true</code> if the checkbox is checked.
     */
    public static boolean isCheckboxChecked(final SearchContext context, final By by) {
        return isCheckboxChecked(context, by, new NotSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(final Object... objects) {
                throw new org.fitting.WebDriverException(format(NO_SUCH_ELEMENT_WITH_IDENTIFIER_FOUND, by.toString()));
            }
        });
    }

    /**
     * Check if a checkbox is checked.
     * @param context  The search context to find the checkbox on.
     * @param by       The By-clause to find the checkbox with.
     * @param callback The callback to execute when the checkbox couldn't be found.
     * @return <code>true</code> if the checkbox is checked.
     */
    public static boolean isCheckboxChecked(final SearchContext context, final By by,
            final NotSuchElementCallback callback) {
        final WebElement checkbox = getElement(context, by, callback);
        boolean checked = false;
        if (checkbox != null) {
            final String checkedValue = getAttributeValue(checkbox, "checked");
            checked = ((checkedValue != null) && (isEmpty(checkedValue) || Boolean.TRUE.toString()
                    .equalsIgnoreCase(checkedValue) || "checked".equalsIgnoreCase(checkedValue)));
        }
        return checked;
    }

    /**
     * Toggle a checkbox.
     * @param context The search context to find the checkbox on.
     * @param by      The By-clause to find the checkbox with.
     */
    public static void toggleCheckbox(final SearchContext context, final By by) {
        toggleCheckbox(context, by, new NotSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(final Object... objects) {
                throw new org.fitting.WebDriverException(format(NO_SUCH_ELEMENT_WITH_IDENTIFIER_FOUND, by.toString()));
            }
        });
    }

    /**
     * Toggle a checkbox.
     * @param context  The search context to find the checkbox on.
     * @param by       The By-clause to find the checkbox with.
     * @param callback The callback to execute when the checkbox couldn't be found.
     */
    public static void toggleCheckbox(final SearchContext context, final By by, final NotSuchElementCallback callback) {
        final WebElement checkbox = getElement(context, by, callback);
        if (checkbox != null) {
            checkbox.click();
        }
    }

    /**
     * Sets the value for the input field with the given name.
     * @param context The SearchContext to search from (either a WebDriver or a WebElement).
     * @param by      The By.
     * @param value   The value.
     */
    public static void setValueForInput(final SearchContext context, final By by, final String value) {
        final WebElement element = getElement(context, by);
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Gets the value for the input field with the given name.
     * @param context The SearchContext to search from (either a WebDriver or a WebElement).
     * @param by      The By.
     * @return value The value.
     */
    public static String valueForInputIs(final SearchContext context, final By by) {
        final WebElement element = getElement(context, by);
        return element.getAttribute("value");
    }

    /**
     * Select the option with text from the dropdown with the given id.
     * @param driver The WebDriver.
     * @param by     The By.
     * @param text   The text.
     */
    public static void selectOptionWithTextFromDropdownOrSelect(final WebDriver driver, final By by,
            final String text) {
        selectOptionWithTextFromDropdownOrSelect(driver, by, text, new NotSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(final Object... objects) {
                throw new org.fitting.WebDriverException(format(NO_OPTION_WITH_VISIBLE_TEXT_FOUND, text));
            }
        });
    }

    /**
     * Select the option with text from the dropdown with the given id.
     * @param driver   The WebDriver.
     * @param by       The By.
     * @param text     The text.
     * @param callback The NotSuchElementCallback.
     */
    public static void selectOptionWithTextFromDropdownOrSelect(final WebDriver driver, final By by,
            final String text, final NotSuchElementCallback callback) {
        final WebElement element = getElement(driver, by);
        final Select select = new Select(element);
        try {
            select.selectByVisibleText(text);
            waitXSecond(driver, 1);
        } catch (NoSuchElementException e) {
            callback.onNoSuchElementFound();
            throw new org.fitting.WebDriverException(format(NO_OPTION_WITH_VISIBLE_TEXT_FOUND, text), e);
        }
    }

    /**
     * Gets the selected option from the dropdown with the given name.
     * @param searchContext The WebDriver.
     * @param by            The By.
     * @return text The option text.
     */
    public static String selectedOptionTextFromDropdownOrSelectIs(final SearchContext searchContext, final By by) {
        final WebElement element = getElement(searchContext, by);
        final Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Send a series of characters as sequential key strokes to an element.
     * @param searchContext The search context to find the element on (e.g. an element or the WebDriver).
     * @param by            The By clause to select the element with.
     * @param keys          The character sequence to send.
     */
    public static void sendKeysToElement(final SearchContext searchContext, final By by, final CharSequence keys) {
        final WebElement element = getElement(searchContext, by);
        element.sendKeys(keys);
    }

    /**
     * Send a function key to an element.
     * @param searchContext The search context to find the element on (e.g. an element or the WebDriver).
     * @param by            The By clause to select the element with.
     * @param key           The function key, as defined by {@link org.openqa.selenium.Keys}.
     */
    public static void sendFunctionKeyToElement(final SearchContext searchContext, final By by, final Keys key) {
        final WebElement element = getElement(searchContext, by);
        element.sendKeys(key);
    }

    /**
     * Gets the value of the cookie matching the given cookie name.
     * @param driver The webdriver.
     * @param name   The name of the cookie.
     * @return value The value of the cookie.
     */
    public static String getCookieValue(final WebDriver driver, final String name) {
        try {
            return getCookie(driver, name).getValue();
        } catch (NullPointerException e) {
            throw new org.fitting.WebDriverException("No cookie with name [" + name + "] found.", e);
        }
    }

    /**
     * Indicates if the given cookie value contains the given value.
     * @param driver     The webdriver.
     * @param cookieName The name of the cookie.
     * @param contains   The value to contain.
     * @return <code>true</code> if the cookie value contains the given value, else <code>false</code>.
     */
    public static boolean cookieValueContains(final WebDriver driver, final String cookieName, final String contains) {
        return getCookieValue(driver, cookieName).contains(contains);
    }

    /**
     * Gets the cookie matching the given cookie name.
     * @param driver The webdriver.
     * @param name   The name of the cookie.
     * @return cookie The cookie.
     */
    public static Cookie getCookie(final WebDriver driver, final String name) {
        return driver.manage().getCookieNamed(name);
    }

    /**
     * Adds a cookie with the given data.
     * @param context The FitnesseContext.
     * @param driver  The WebDriver.
     * @param name    The name.
     * @param value   The value.
     * @param path    The path.
     * @param domain  The domain.
     */
    public static void addCookie(final FitnesseContext context, final WebDriver driver, final String name,
            final String value, final String path, final String domain) {

        handleCookie(context, domain, new CookieCallback() {
            @Override
            public void doWithCookie() {
                final Cookie.Builder builder = new Cookie.Builder(name, value);
                if (isNotEmpty(path)) {
                    builder.path(path);
                }
                if (isNotEmpty(domain)) {
                    builder.domain(getStrippedDomain(domain));
                }

                driver.manage().addCookie(builder.build());
            }
        });
    }

    /**
     * Copy the cookie matching the given name to the given domain.
     * @param context The FitnesseContext.
     * @param driver  The webdriver.
     * @param name    The name of the cookie.
     * @param domain  The domain.
     */
    public static void copyCookieToDomain(final FitnesseContext context, final WebDriver driver, final String name,
            final String domain) {
        try {
            final Cookie cookie = getCookie(driver, name);
            addCookie(context, driver, name, cookie.getValue(), cookie.getPath(), domain);
        } catch (NullPointerException e) {
            throw new org.fitting.WebDriverException("No cookie with name [" + name + "] found.", e);
        }
    }

    /**
     * Clears all cookies on the given domain.
     * @param context The FitnesseContext.
     * @param driver  The webdriver.
     * @param domain  The domain.
     */
    public static void clearAllCookiesOnDomain(final FitnesseContext context, final WebDriver driver,
            final String domain) {
        handleCookie(context, domain, new CookieCallback() {
            @Override
            public void doWithCookie() {
                driver.manage().deleteAllCookies();
            }
        });
    }

    /**
     * Clears all cookies on the given domain.
     * @param context    The FitnesseContext.
     * @param driver     The webdriver.
     * @param cookieName The name of the cookie.
     * @param domain     The domain.
     */
    public static void deleteCookieWithNameOnDomain(final FitnesseContext context, final WebDriver driver,
            final String cookieName, final String domain) {
        handleCookie(context, domain, new CookieCallback() {
            @Override
            public void doWithCookie() {
                driver.manage().deleteCookieNamed(cookieName);
            }
        });
    }

    /**
     * Indicates if a cookie with the given name exists on the given domain.
     * @param context    The FitnesseContext.
     * @param driver     The webdriver.
     * @param domain     The domain.
     * @param cookieName The name of the cookie.
     * @return <code>true</code> if cookie is present, else <code>false</code>.
     */
    public static boolean isCookieWithNamePresentOnDomain(final FitnesseContext context, final WebDriver driver,
            final String cookieName, final String domain) {
        final boolean[] exists = {false};
        handleCookie(context, domain, new CookieCallback() {
            @Override
            public void doWithCookie() {
                if (driver.manage().getCookieNamed(cookieName) != null) {
                    exists[0] = true;
                }
            }
        });
        return exists[0];
    }

    /**
     * Based on the domain something is done with a cookie. If the domain is not null a window on that domain is opened
     * and something is done with the cookie on that domain.
     * @param context  The FitnesseContext.
     * @param domain   The domain.
     * @param callback The CookieCallback.
     */
    private static void handleCookie(final FitnesseContext context, final String domain,
            final CookieCallback callback) {
        if (isNotEmpty(domain) && domain.startsWith(".")) {
            throw new org.fitting.WebDriverException(format(NO_VALID_DOMAIN, domain));
        }
        if (isNotEmpty(domain)) {
            context.createNewWindow(domain, true); // open the domain so we can add the cookie.
        }
        callback.doWithCookie();
        if (isNotEmpty(domain)) {
            context.closeActiveWindow(); // go back to previous window.
        }
    }

    /** Cookie callback Interface. */
    public interface CookieCallback {
        /** Do something with the cookie. */
        void doWithCookie();
    }

    /**
     * Gets the stripped domain.
     * @param domain The domain.
     * @return strippedDomain The stripped domain.
     */
    private static String getStrippedDomain(final String domain) {
        String strippedDomain = domain;
        final Matcher m = DOMAIN_PATTERN.matcher(domain);
        if (m.matches()) {
            strippedDomain = m.group(1);
        }
        return strippedDomain;
    }
}