package org.fitting.fixture;

import org.fitting.FitnesseContainer;
import org.fitting.FitnesseContext;
import org.fitting.SeleniumWindow;
import org.fitting.WebDriverUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.fitting.WebDriverUtil.*;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.name;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;

/** Contains all the deprecated methods. */
@Deprecated
public final class DeprecatedSeleniumFixture extends SeleniumFixture {
    /**
     * Gets the getWebDriver().
     * @return getWebDriver() The WebDriver.
     * @deprecated As of release 0.0.34, replaced by {@link SeleniumFixture#getDriver()}.
     */
    @Deprecated
    public WebDriver getWebDriver() {
        final FitnesseContext context = FitnesseContainer.get();
        return context.getDriver();
    }

    /**
     * Gets the currently selected element.
     * @return element The WebElement.
     * @deprecated As of release 0.0.34, replaced by {@link SeleniumFixture#getSelectedElement()}.
     */
    @Deprecated
    public WebElement getWebElement() {
        final FitnesseContext context = FitnesseContainer.get();
        final SeleniumWindow activeWindow = context.getActiveWindow();
        return activeWindow.getSelectedWebElement();
    }

    /**
     * Sets the selected element.
     * @param element The element.
     * @deprecated As of release 0.0.34, replaced by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)}.
     */
    @Deprecated
    public void setWebElement(final WebElement element) {
        final FitnesseContext context = FitnesseContainer.get();
        final SeleniumWindow activeWindow = context.getActiveWindow();
        activeWindow.setSelectedWebElement(element);
    }

    /**
     * Opens the given url.
     * @param url The url.
     * @deprecated As of release 0.0.34, replaced by {@link SeleniumNavigationFixture#open(String)}.
     */
    @Deprecated
    public void open(final String url) {
        WebDriverUtil.open(getWebDriver(), url);
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param id The id.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#elementWithIdIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean elementWithIdIsPresent(final String id) {
        return isElementPresent(getWebDriver(), id(id));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param name The name.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#elementWithNameIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean elementWithNameIsPresent(final String name) {
        return isElementPresent(getWebDriver(), name(name));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param className The className.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#elementWithClassNameIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean elementWithClassNameIsPresent(final String className) {
        return isElementPresent(getWebDriver(), className(className));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param tagName The tagName.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#elementWithTagNameIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean elementWithTagNameIsPresent(final String tagName) {
        return isElementPresent(getWebDriver(), tagName(tagName));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param xpath The xpath.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#elementWithXpathIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean elementWithXpathIsPresent(final String xpath) {
        return isElementPresent(getWebDriver(), xpath(xpath));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param linkText The linkText.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#elementWithLinkTextIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean elementWithLinkTextIsPresent(final String linkText) {
        return isElementPresent(getWebDriver(), linkText(linkText));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param id The id.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#subElementWithIdIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean subElementWithIdIsPresent(final String id) {
        return isElementPresent(getSelectedElement(), id(id));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param name The name.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#subElementWithNameIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean subElementWithNameIsPresent(final String name) {
        return isElementPresent(getSelectedElement(), name(name));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param className The className.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#subElementWithClassNameIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean subElementWithClassNameIsPresent(final String className) {
        return isElementPresent(getSelectedElement(), className(className));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param tagName The tagName.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#subElementWithTagNameIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean subElementWithTagNameIsPresent(final String tagName) {
        return isElementPresent(getSelectedElement(), tagName(tagName));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param xpath The xpath.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#subElementWithXpathIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean subElementWithXpathIsPresent(final String xpath) {
        return isElementPresent(getSelectedElement(), xpath(xpath));
    }

    /**
     * Indicates if the element we are looking for is present.
     * @param linkText The linkText.
     * @return <code>true</code> if present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#subElementWithLinkTextIsPresent(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#isElementPresent(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public boolean subElementWithLinkTextIsPresent(final String linkText) {
        return isElementPresent(getSelectedElement(), linkText(linkText));
    }

    /**
     * Selects the element we are looking for.
     * @param id The id.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectElementWithId(final String id) {
        setSelectedElement(getElement(getWebDriver(), id(id)));
    }

    /**
     * Selects the element we are looking for.
     * @param name The name.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectElementWithName(final String name) {
        setSelectedElement(getElement(getWebDriver(), name(name)));
    }

    /**
     * Selects the element we are looking for.
     * @param className The className.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectElementWithClassName(final String className) {
        setSelectedElement(getElement(getWebDriver(), className(className)));
    }

    /**
     * Selects the element we are looking for.
     * @param tagName The tagName.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectElementWithTagName(final String tagName) {
        setSelectedElement(getElement(getWebDriver(), tagName(tagName)));
    }

    /**
     * Selects the element we are looking for.
     * @param xpath The xpath.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectElementWithXpath(final String xpath) {
        setSelectedElement(getElement(getWebDriver(), xpath(xpath)));
    }

    /**
     * Selects the element we are looking for.
     * @param linkText The linkText.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectElementWithLinkText(final String linkText) {
        setSelectedElement(getElement(getWebDriver(), linkText(linkText)));
    }

    /**
     * Selects the element we are looking for.
     * @param id The id.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectSubElementWithId(final String id) {
        setSelectedElement(getElement(getSelectedElement(), id(id)));
    }

    /**
     * Selects the element we are looking for.
     * @param name The name.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectSubElementWithName(final String name) {
        setSelectedElement(getElement(getSelectedElement(), name(name)));
    }

    /**
     * Selects the element we are looking for.
     * @param className The className.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectSubElementWithClassName(final String className) {
        setSelectedElement(getElement(getSelectedElement(), className(className)));
    }

    /**
     * Selects the element we are looking for.
     * @param tagName The tagName.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectSubElementWithTagName(final String tagName) {
        setSelectedElement(getElement(getSelectedElement(), tagName(tagName)));
    }

    /**
     * Selects the element we are looking for.
     * @param xpath The xpath.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectSubElementWithXpath(final String xpath) {
        setSelectedElement(getElement(getSelectedElement(), xpath(xpath)));
    }

    /**
     * Selects the element we are looking for.
     * @param linkText The linkText.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectElementWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)} with value
     *             {@link WebDriverUtil#getElement(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public void selectSubElementWithLinkText(final String linkText) {
        setSelectedElement(getElement(getSelectedElement(), linkText(linkText)));
    }

    /**
     * Gets the attribute value of the given attribute name of the selected element.
     * @param attributeName The attribute name.
     * @return value The value.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#attributeValue(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#getAttributeValue(org.openqa.selenium.WebElement, String)}.
     */
    @Deprecated
    public String attributeValue(final String attributeName) {
        return getAttributeValue(getSelectedElement(), attributeName);
    }

    /**
     * Indicates if the attribute value of the given attribute name of the selected element contains the given value.
     * @param attributeName The attribute name.
     * @param contains      The value to contain.
     * @return <code>true</code> if the attribute value contains the givenvalue, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#attributeValueContains(String, String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#attributeValueContains(org.openqa.selenium.WebElement, String, String)}.
     */
    @Deprecated
    public boolean attributeValueContains(final String attributeName, final String contains) {
        return WebDriverUtil.attributeValueContains(getSelectedElement(), attributeName, contains);
    }

    /**
     * Gets the text value for the selected element.
     * @return value The value.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#textValue()}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link org.openqa.selenium.WebElement#getText()}.
     */
    @Deprecated
    public String textValue() {
        return WebDriverUtil.getTextValue(getSelectedElement());
    }

    /**
     * Indicates if the text value of the selected element contains the given value.
     * @param contains The value to contain.
     * @return <code>true</code> if the text value contains the given value, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#textValueContains(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#textValueContains(org.openqa.selenium.WebElement, String)}.
     */
    @Deprecated
    public boolean textValueContains(final String contains) {
        return WebDriverUtil.textValueContains(getSelectedElement(), contains);
    }

    /**
     * Clicks the selected element.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link org.fitting.fixture.SeleniumElementFixture#click()}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#click(org.openqa.selenium.WebDriver, org.openqa.selenium.WebElement)}.
     */
    @Deprecated
    public void click() {
        WebDriverUtil.click(getWebDriver(), getSelectedElement());
    }

    /**
     * Wait for the given milliseconds.
     * @param seconds The number of seconds.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to
     *             {@link org.fitting.fixture.SeleniumTimingFixture#waitSeconds(int)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#waitXSecond(org.openqa.selenium.WebDriver, int)}.
     */
    @Deprecated
    public void waitSeconds(final int seconds) {
        waitXSecond(getWebDriver(), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param id      The id.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link org.fitting.fixture.SeleniumTimingFixture#waitForElementWithIdPresentForSeconds(String, int)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#waitForElementPresent(org.openqa.selenium.WebDriver, org.openqa.selenium.By, int)}.
     */
    @Deprecated
    public boolean waitForElementWithIdPresentForSeconds(final String id, final int seconds) {
        return waitForElementPresent(getWebDriver(), id(id), seconds);

    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param name    The name.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link org.fitting.fixture.SeleniumTimingFixture#waitForElementWithNamePresentForSeconds(String, int)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#waitForElementPresent(org.openqa.selenium.WebDriver, org.openqa.selenium.By, int)}.
     */
    @Deprecated
    public boolean waitForElementWithNamePresentForSeconds(final String name, final int seconds) {
        return waitForElementPresent(getWebDriver(), name(name), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param className The className.
     * @param seconds   The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link org.fitting.fixture.SeleniumTimingFixture#waitForElementWithClassNamePresentForSeconds(String, int)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#waitForElementPresent(org.openqa.selenium.WebDriver, org.openqa.selenium.By, int)}.
     */
    @Deprecated
    public boolean waitForElementWithClassNamePresentForSeconds(final String className, final int seconds) {
        return waitForElementPresent(getWebDriver(), className(className), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param tagName The tagName.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link org.fitting.fixture.SeleniumTimingFixture#waitForElementWithTagNamePresentForSeconds(String, int)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#waitForElementPresent(org.openqa.selenium.WebDriver, org.openqa.selenium.By, int)}.
     */
    @Deprecated
    public boolean waitForElementWithTagNamePresentForSeconds(final String tagName, final int seconds) {
        return waitForElementPresent(getWebDriver(), tagName(tagName), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param xpath   The xpath.
     * @param seconds The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link org.fitting.fixture.SeleniumTimingFixture#waitForElementWithXpathPresentForSeconds(String, int)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#waitForElementPresent(org.openqa.selenium.WebDriver, org.openqa.selenium.By, int)}.
     */
    @Deprecated
    public boolean waitForElementWithXpathPresentForSeconds(final String xpath, final int seconds) {
        return waitForElementPresent(getWebDriver(), xpath(xpath), seconds);
    }

    /**
     * Waits for the element we are looking for for the given amount of seconds.
     * If the element is not found an exception will be thrown.
     * @param linkText linkText xpath.
     * @param seconds  The number of seconds.
     * @return <code>true</code> if element is present, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link org.fitting.fixture.SeleniumTimingFixture#waitForElementWithLinkTextPresentForSeconds(String, int)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#waitForElementPresent(org.openqa.selenium.WebDriver, org.openqa.selenium.By, int)}.
     */
    @Deprecated
    public boolean waitForElementWithLinkTextPresentForSeconds(final String linkText, final int seconds) {
        return waitForElementPresent(getWebDriver(), linkText(linkText), seconds);
    }

    /**
     * Gets the value of the cookie matching the given cookie name.
     * @param cookieName The name of the cookie.
     * @return value The value of the cookie.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link org.fitting.fixture.SeleniumCookieFixture#valueForCookieWithNameIs(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#getCookieValue(org.openqa.selenium.WebDriver, String)}.
     */
    @Deprecated
    public String cookieValue(final String cookieName) {
        return WebDriverUtil.getCookieValue(getWebDriver(), cookieName);
    }

    /**
     * Indicates if the given cookie value contains the given value.
     * @param cookieName The name of the cookie.
     * @param contains   The value to contain.
     * @return <code>true</code> if the cookie value contains the given value, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link org.fitting.fixture.SeleniumCookieFixture#valueForCookieWithNameContains(String, String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#cookieValueContains(org.openqa.selenium.WebDriver, String, String)}.
     */
    @Deprecated
    public boolean cookieValueContains(final String cookieName, final String contains) {
        return WebDriverUtil.cookieValueContains(getWebDriver(), cookieName, contains);
    }

    /**
     * Indicates if the given page contains the given value.
     * @param contains The value to contain.
     * @return <code>true</code> if the pagecontains the given value, else <code>false</code>.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link org.fitting.fixture.SeleniumElementFixture#pageContains(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#pageContains(org.openqa.selenium.WebDriver, String)}.
     */
    @Deprecated
    public boolean pageContains(final String contains) {
        return WebDriverUtil.pageContains(getWebDriver(), contains);
    }

    /**
     * Refresh the page.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumNavigationFixture#refresh()}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#refresh(org.openqa.selenium.WebDriver)}.
     */
    @Deprecated
    public void refresh() {
        WebDriverUtil.refresh(getWebDriver());
    }

    /**
     * Switches the focus to iframe with id.
     * @param id The id of the iframe.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumNavigationFixture#switchToIframeWithId(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#switchToIframeWithId(org.openqa.selenium.WebDriver, String)}.
     */
    @Deprecated
    public void switchToIframeWithId(final String id) {
        WebDriverUtil.switchToFrame(getWebDriver(), id(id));
    }

    /**
     * Switch to the default frame.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumNavigationFixture#switchToMainFrame()}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#switchToMainFrame(org.openqa.selenium.WebDriver)}.
     */
    @Deprecated
    public void switchToMainFrame() {
        WebDriverUtil.switchToMainFrame(getWebDriver());
    }

    /**
     * Select a radio button with the given name and value. Note: the value does
     * not indicate the name shown on the screen, it is the value of the radiobutton value.
     * @param name  The name of the radio button.
     * @param value The value of the radio button.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectRadioButtonWithNameAndValue(String, String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#selectRadioButtonValue(org.openqa.selenium.WebDriver, org.openqa.selenium.By, String)}.
     */
    @Deprecated
    public void selectRadioButtonWithNameAndValue(final String name, final String value) {
        selectRadioButtonValue(getWebDriver(), name(name), value);
    }

    /**
     * Select a radio button with the given id and value. Note: the value does
     * not indicate the name shown on the screen, it is the value of the radiobutton value.
     * @param id    The id of the radio button.
     * @param value The value of the radio button.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectRadioButtonWithIdAndValue(String, String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#selectRadioButtonValue(org.openqa.selenium.WebDriver, org.openqa.selenium.By, String)}.
     */
    @Deprecated
    public void selectRadioButtonWithIdAndValue(final String id, final String value) {
        selectRadioButtonValue(getWebDriver(), id(id), value);
    }

    /**
     * Sets the value for the input field with the given name.
     * @param value The value.
     * @param name  The name.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#setValueForInputWithName(String, String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#setValueForInput(org.openqa.selenium.SearchContext, org.openqa.selenium.By, String)}.
     */
    @Deprecated
    public void setValueForInputWithName(final String value, final String name) {
        setValueForInput(getWebDriver(), name(name), value);
    }

    /**
     * Sets the value for the input field with the given id.
     * @param value The value.
     * @param id    The id.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#setValueForInputWithId(String, String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#setValueForInput(org.openqa.selenium.SearchContext, org.openqa.selenium.By, String)}.
     */
    @Deprecated
    public void setValueForInputWithId(final String value, final String id) {
        setValueForInput(getWebDriver(), id(id), value);
    }

    /**
     * Gets the value for the input field with the given name.
     * @param name The name.
     * @return value The value.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#valueForInputWithNameIs(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#valueForInputIs(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public String valueForInputWithNameIs(final String name) {
        return valueForInputIs(getWebDriver(), name(name));
    }

    /**
     * Gets the value for the input field with the given name.
     * @param id The id.
     * @return value The value.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#valueForInputWithIdIs(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#valueForInputIs(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public String valueForInputWithIdIs(final String id) {
        return valueForInputIs(getWebDriver(), id(id));
    }

    /**
     * Select the option with text from the dropdown with the given name.
     * @param text The text.
     * @param name The name.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectOptionWithTextFromDropdownWithName(String, String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#selectOptionWithTextFromDropdownOrSelect(org.openqa.selenium.WebDriver, org.openqa.selenium.By, String)}.
     */
    @Deprecated
    public void selectOptionWithTextFromDropdownWithName(final String text, final String name) {
        selectOptionWithTextFromDropdownOrSelect(getWebDriver(), name(name), text);
    }

    /**
     * Select the option with text from the dropdown with the given id.
     * @param text The text.
     * @param id   The id.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectOptionWithTextFromDropdownWithId(String, String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#selectOptionWithTextFromDropdownOrSelect(org.openqa.selenium.WebDriver, org.openqa.selenium.By, String)}.
     */
    @Deprecated
    public void selectOptionWithTextFromDropdownWithId(final String text, final String id) {
        selectOptionWithTextFromDropdownOrSelect(getWebDriver(), id(id), text);
    }

    /**
     * Gets the selected option from the dropdown with the given name.
     * @param name The name.
     * @return text The option text.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectedOptionTextFromDropdownWithNameIs(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#selectedOptionTextFromDropdownOrSelectIs(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public String selectedOptionTextFromDropdownWithNameIs(final String name) {
        return selectedOptionTextFromDropdownOrSelectIs(getWebDriver(), name(name));
    }

    /**
     * Gets the selected option from the dropdown with the given id.
     * @param id The id.
     * @return text The option text.
     * @deprecated As of release 0.0.34.
     *             The fixture method is moved to {@link SeleniumElementFixture#selectedOptionTextFromDropdownWithIdIs(String)}.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#selectedOptionTextFromDropdownOrSelectIs(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}.
     */
    @Deprecated
    public String selectedOptionTextFromDropdownWithIdIs(final String id) {
        return selectedOptionTextFromDropdownOrSelectIs(getWebDriver(), id(id));
    }

    /**
     * Gets the elements with x path. If elements are found, the first element
     * of the list will be selected.
     * @param xpath the xpath
     * @return the elements with x path
     * @deprecated As of release 0.0.34.
     *             The fixture method is removed.
     *             Calling this method another fixture should not be done, but should be replace by
     *             {@link WebDriverUtil#getElements(org.openqa.selenium.SearchContext, org.openqa.selenium.By)}
     *             and setting the first element by {@link SeleniumFixture#setSelectedElement(org.openqa.selenium.WebElement)}
     */
    @Deprecated
    public List<WebElement> getElementsWithXPath(final String xpath) {
        final List<WebElement> elements = getElements(getWebDriver(), xpath(xpath));
        if (elements.size() > 0) {
            setWebElement(elements.get(0));
        }
        return elements;
    }
}
