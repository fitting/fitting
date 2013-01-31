package org.fitting.fixture;

import static org.fitting.WebDriverUtil.getElement;
import static org.fitting.WebDriverUtil.isCheckboxChecked;
import static org.fitting.WebDriverUtil.isElementDisplayed;
import static org.fitting.WebDriverUtil.isElementPresent;
import static org.fitting.WebDriverUtil.selectOptionWithTextFromDropdownOrSelect;
import static org.fitting.WebDriverUtil.selectRadioButtonValue;
import static org.fitting.WebDriverUtil.selectedOptionTextFromDropdownOrSelectIs;
import static org.fitting.WebDriverUtil.sendFunctionKeyToElement;
import static org.fitting.WebDriverUtil.setValueForInput;
import static org.fitting.WebDriverUtil.toggleCheckbox;
import static org.fitting.WebDriverUtil.valueForInputIs;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.name;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.Keys.ESCAPE;
import org.fitting.WebDriverUtil;

/**
 * SeleniumElementFixture is a fixture that exposes all the WebDriverUtil element methods.
 * These methods allow for interaction with the element and obtaining element information.
 */
public final class SeleniumElementFixture extends SeleniumFixture {
    /**
     * Indicates if the element we are looking for is present.
     *
     * @param id The id.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean elementWithIdIsPresent(final String id) {
        return isElementPresent(getDriver(), id(id));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param name The name.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean elementWithNameIsPresent(final String name) {
        return isElementPresent(getDriver(), name(name));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param className The className.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean elementWithClassNameIsPresent(final String className) {
        return isElementPresent(getDriver(), className(className));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param tagName The tagName.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean elementWithTagNameIsPresent(final String tagName) {
        return isElementPresent(getDriver(), tagName(tagName));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param xpath The xpath.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean elementWithXpathIsPresent(final String xpath) {
        return isElementPresent(getDriver(), xpath(xpath));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param linkText The linkText.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean elementWithLinkTextIsPresent(final String linkText) {
        return isElementPresent(getDriver(), linkText(linkText));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param id The id.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean subElementWithIdIsPresent(final String id) {
        return isElementPresent(getSelectedElement(), id(id));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param name The name.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean subElementWithNameIsPresent(final String name) {
        return isElementPresent(getSelectedElement(), name(name));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param className The className.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean subElementWithClassNameIsPresent(final String className) {
        return isElementPresent(getSelectedElement(), className(className));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param tagName The tagName.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean subElementWithTagNameIsPresent(final String tagName) {
        return isElementPresent(getSelectedElement(), tagName(tagName));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param xpath The xpath.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean subElementWithXpathIsPresent(final String xpath) {
        return isElementPresent(getSelectedElement(), xpath(xpath));
    }

    /**
     * Indicates if the element we are looking for is present.
     *
     * @param linkText The linkText.
     * @return <code>true</code> if present, else <code>false</code>.
     */
    public boolean subElementWithLinkTextIsPresent(final String linkText) {
        return isElementPresent(getSelectedElement(), linkText(linkText));
    }

    /**
     * Indicates if the element we are looking for is Displayed.
     * @param id The id.
     * @return <code>true</code> if Displayed, else <code>false</code>.
     */
    public boolean elementWithIdIsDisplayed(final String id) {
        return isElementDisplayed(getDriver(), id(id));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param name The name.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean elementWithNameIsDisplayed(final String name) {
        return isElementDisplayed(getDriver(), name(name));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param className The className.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean elementWithClassNameIsDisplayed(final String className) {
        return isElementDisplayed(getDriver(), className(className));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param tagName The tagName.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean elementWithTagNameIsDisplayed(final String tagName) {
        return isElementDisplayed(getDriver(), tagName(tagName));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param xpath The xpath.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean elementWithXpathIsDisplayed(final String xpath) {
        return isElementDisplayed(getDriver(), xpath(xpath));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param linkText The linkText.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean elementWithLinkTextIsDisplayed(final String linkText) {
        return isElementDisplayed(getDriver(), linkText(linkText));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param id The id.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean subElementWithIdIsDisplayed(final String id) {
        return isElementDisplayed(getSelectedElement(), id(id));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param name The name.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean subElementWithNameIsDisplayed(final String name) {
        return isElementDisplayed(getSelectedElement(), name(name));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param className The className.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean subElementWithClassNameIsDisplayed(final String className) {
        return isElementDisplayed(getSelectedElement(), className(className));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param tagName The tagName.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean subElementWithTagNameIsDisplayed(final String tagName) {
        return isElementDisplayed(getSelectedElement(), tagName(tagName));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param xpath The xpath.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean subElementWithXpathIsDisplayed(final String xpath) {
        return isElementDisplayed(getSelectedElement(), xpath(xpath));
    }

    /**
     * Indicates if the element we are looking for is displayed.
     * @param linkText The linkText.
     * @return <code>true</code> if displayed, else <code>false</code>.
     */
    public boolean subElementWithLinkTextIsDisplayed(final String linkText) {
        return isElementDisplayed(getSelectedElement(), linkText(linkText));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param id The id.
     */
    public void selectElementWithId(final String id) {
        setSelectedElement(getElement(getDriver(), id(id)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param name The name.
     */
    public void selectElementWithName(final String name) {
        setSelectedElement(getElement(getDriver(), name(name)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param className The className.
     */
    public void selectElementWithClassName(final String className) {
        setSelectedElement(getElement(getDriver(), className(className)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param tagName The tagName.
     */
    public void selectElementWithTagName(final String tagName) {
        setSelectedElement(getElement(getDriver(), tagName(tagName)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param xpath The xpath.
     */
    public void selectElementWithXpath(final String xpath) {
        setSelectedElement(getElement(getDriver(), xpath(xpath)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param linkText The linkText.
     */
    public void selectElementWithLinkText(final String linkText) {
        setSelectedElement(getElement(getDriver(), linkText(linkText)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param id The id.
     */
    public void selectSubElementWithId(final String id) {
        setSelectedElement(getElement(getSelectedElement(), id(id)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param name The name.
     */
    public void selectSubElementWithName(final String name) {
        setSelectedElement(getElement(getSelectedElement(), name(name)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param className The className.
     */
    public void selectSubElementWithClassName(final String className) {
        setSelectedElement(getElement(getSelectedElement(), className(className)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param tagName The tagName.
     */
    public void selectSubElementWithTagName(final String tagName) {
        setSelectedElement(getElement(getSelectedElement(), tagName(tagName)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param xpath The xpath.
     */
    public void selectSubElementWithXpath(final String xpath) {
        setSelectedElement(getElement(getSelectedElement(), xpath(xpath)));
    }

    /**
     * Selects the element we are looking for.
     *
     * @param linkText The linkText.
     */
    public void selectSubElementWithLinkText(final String linkText) {
        setSelectedElement(getElement(getSelectedElement(), linkText(linkText)));
    }

    /**
     * Gets the attribute value of the given attribute name of the selected element.
     *
     * @param attributeName The attribute name.
     * @return value The value.
     */
    public String attributeValue(final String attributeName) {
        return WebDriverUtil.getAttributeValue(getSelectedElement(), attributeName);
    }

    /**
     * Indicates if the attribute value of the given attribute name of the selected element contains the given value.
     *
     * @param attributeName The attribute name.
     * @param contains      The value to contain.
     * @return <code>true</code> if the attribute value contains the givenvalue, else <code>false</code>.
     */
    public boolean attributeValueContains(final String attributeName, final String contains) {
        return WebDriverUtil.attributeValueContains(getSelectedElement(), attributeName, contains);
    }

    /**
     * Gets the text value for the selected element.
     *
     * @return value The value.
     */
    public String textValue() {
        return WebDriverUtil.getTextValue(getSelectedElement());
    }

    /**
     * Indicates if the text value of the selected element contains the given value.
     *
     * @param contains The value to contain.
     * @return <code>true</code> if the text value contains the given value, else <code>false</code>.
     */
    public boolean textValueContains(final String contains) {
        return WebDriverUtil.textValueContains(getSelectedElement(), contains);
    }

    public String title() {
        return getDriver().getTitle();
    }
    
    /**
     * Clicks the selected element.
     */
    public void click() {
        WebDriverUtil.click(getDriver(), getSelectedElement());
    }

    /**
     * Select a radio button with the given name and value. Note: the value does
     * not indicate the name shown on the screen, it is the value of the radiobutton value.
     *
     * @param name  The name of the radio button.
     * @param value The value of the radio button.
     */
    public void selectRadioButtonWithNameAndValue(final String name, final String value) {
        selectRadioButtonValue(getDriver(), name(name), value);
    }

    /**
     * Select a radio button with the given id and value. Note: the value does
     * not indicate the name shown on the screen, it is the value of the radiobutton value.
     *
     * @param id    The id of the radio button.
     * @param value The value of the radio button.
     */
    public void selectRadioButtonWithIdAndValue(final String id, final String value) {
        selectRadioButtonValue(getDriver(), id(id), value);
    }

    /**
     * Sets the value for the input field with the given name.
     *
     * @param value The value.
     * @param name  The name.
     */
    public void setValueForInputWithName(final String value, final String name) {
        setValueForInput(getDriver(), name(name), value);
    }

    /**
     * Sets the value for the input field with the given id.
     *
     * @param value The value.
     * @param id    The id.
     */
    public void setValueForInputWithId(final String value, final String id) {
        setValueForInput(getDriver(), id(id), value);
    }

    /**
     * Sets the value for the input field with the given class name.
     *
     * @param value The value.
     * @param name  The name.
     */
    public void setValueForInputWithClassName(final String value, final String name) {
        setValueForInput(getDriver(), className(name), value);
    }

    /**
     * Gets the value for the input field with the given name.
     *
     * @param name The name.
     * @return value The value.
     */
    public String valueForInputWithNameIs(final String name) {
        return valueForInputIs(getDriver(), name(name));
    }

    /**
     * Gets the value for the input field with the given name.
     *
     * @param id The id.
     * @return value The value.
     */
    public String valueForInputWithIdIs(final String id) {
        return valueForInputIs(getDriver(), id(id));
    }

    /**
     * Select the option with text from the dropdown with the given name.
     *
     * @param text The text.
     * @param name The name.
     */
    public void selectOptionWithTextFromDropdownWithName(final String text, final String name) {
        selectOptionWithTextFromDropdownOrSelect(getDriver(), name(name), text);
    }

    /**
     * Select the option with text from the dropdown with the given id.
     *
     * @param text The text.
     * @param id   The id.
     */
    public void selectOptionWithTextFromDropdownWithId(final String text, final String id) {
        selectOptionWithTextFromDropdownOrSelect(getDriver(), id(id), text);
    }

    /**
     * Gets the selected option from the dropdown with the given name.
     *
     * @param name The name.
     * @return text The option text.
     */
    public String selectedOptionTextFromDropdownWithNameIs(final String name) {
        return selectedOptionTextFromDropdownOrSelectIs(getDriver(), name(name));
    }

    /**
     * Gets the selected option from the dropdown with the given id.
     *
     * @param id The id.
     * @return text The option text.
     */
    public String selectedOptionTextFromDropdownWithIdIs(final String id) {
        return selectedOptionTextFromDropdownOrSelectIs(getDriver(), id(id));
    }

    /**
     * Indicates if the given page contains the given value.
     *
     * @param contains The value to contain.
     * @return <code>true</code> if the pagecontains the given value, else <code>false</code>.
     */
    public boolean pageContains(final String contains) {
        return WebDriverUtil.pageContains(getDriver(), contains);
    }

    /**
     * Press the escape key, while having the element with the given id selected.
     *
     * @param id The ID of the element.
     */
    public void pressEscapeForElementWithId(final String id) {
        WebDriverUtil.sendFunctionKeyToElement(getDriver(), id(id), ESCAPE);
    }

    /**
     * Press the escape key, while having the element with the given name selected.
     *
     * @param name The name of the element.
     */
    public void pressEscapeForElementWithName(final String name) {
        sendFunctionKeyToElement(getDriver(), name(name), ESCAPE);
    }

    /**
     * Press the escape key, while having the element with the given class name selected.
     *
     * @param name The class name of the element.
     */
    public void pressEscapeForElementWithClassName(final String name) {
        sendFunctionKeyToElement(getDriver(), className(name), ESCAPE);
    }

    /**
     * Press the escape key, while having the element with the given xpath selected.
     *
     * @param xpath The xpath of the element.
     */
    public void pressEscapeForElementWithXpath(final String xpath) {
        sendFunctionKeyToElement(getDriver(), xpath(xpath), ESCAPE);
    }

    /**
     * Press the enter key, while having the element with the given id selected.
     *
     * @param id The ID of the element.
     */
    public void pressEnterForElementWithId(final String id) {
        sendFunctionKeyToElement(getDriver(), id(id), ENTER);
    }

    /**
     * Press the enter key, while having the element with the given name selected.
     *
     * @param name The name of the element.
     */
    public void pressEnterForElementWithName(final String name) {
        sendFunctionKeyToElement(getDriver(), name(name), ENTER);
    }

    /**
     * Press the enter key, while having the element with the given class name selected.
     *
     * @param name The class name of the element.
     */
    public void pressEnterForElementWithClassName(final String name) {
        sendFunctionKeyToElement(getDriver(), className(name), ENTER);
    }

    /**
     * Press the enter key, while having the element with the given xpath selected.
     *
     * @param xpath The xpath of the element.
     */
    public void pressEnterForElementWithXpath(final String xpath) {
        sendFunctionKeyToElement(getDriver(), xpath(xpath), ENTER);
    }

    /**
     * Check is the checkbox, identified by the given xpath, is checked.
     *
     * @param xpath The xpath of the checkbox.
     * @return <code>true</code> if the checkbox is checked.
     */
    public boolean isCheckboxWithXpathChecked(final String xpath) {
        return isCheckboxChecked(getSearchContext(), xpath(xpath));
    }

    /**
     * Check is the checkbox, identified by the given name, is checked.
     *
     * @param name The name of the checkbox.
     * @return <code>true</code> if the checkbox is checked.
     */
    public boolean isCheckboxWithNameChecked(final String name) {
        return isCheckboxChecked(getSearchContext(), name(name));
    }

    /**
     * Check is the checkbox, identified by the given id, is checked.
     *
     * @param id The id of the checkbox.
     * @return <code>true</code> if the checkbox is checked.
     */
    public boolean isCheckboxWithIdChecked(final String id) {
        return isCheckboxChecked(getSearchContext(), id(id));
    }

    /**
     * Toggle the checkbox identified by the given xpath.
     *
     * @param xpath The xpath of the checkbox.
     */
    public void toggleCheckboxWithXpath(final String xpath) {
        toggleCheckbox(getSearchContext(), xpath(xpath));
    }

    /**
     * Toggle the checkbox identified by the given name.
     *
     * @param name The name of the checkbox.
     */
    public void toggleCheckboxWithName(final String name) {
        toggleCheckbox(getSearchContext(), name(name));
    }

    /**
     * Toggle the checkbox identified by the given id.
     *
     * @param id The id of the checkbox.
     */
    public void toggleCheckboxWithId(final String id) {
        toggleCheckbox(getSearchContext(), id(id));
    }

    /**
     * Verifies if the element contains the given text
     *
     * @param id   The id.
     * @param text The text value
     * @return <code>true</code> if the text is present within the element.
     */
    public boolean elementWithIdContainsText(final String id, final String text) {
        return WebDriverUtil.textValueContains(getDriver(), id(id), text);
    }

    /**
     * Verifies if the element contains the given text
     *
     * @param name The name.
     * @param text The text value
     * @return <code>true</code> if the text is present within the element.
     */
    public boolean elementWithNameContainsText(final String name, final String text) {
        return WebDriverUtil.textValueContains(getDriver(), name(name), text);
    }

    /**
     * Verifies if the element contains the given text
     *
     * @param className The className.
     * @param text      The text value
     * @return <code>true</code> if the text is present within the element.
     */
    public boolean elementWithClassNameContainsText(final String className, final String text) {
        return WebDriverUtil.textValueContains(getDriver(), className(className), text);
    }

    /**
     * Verifies if the element contains the given text
     *
     * @param tagName The tagName.
     * @param text    The text value
     * @return <code>true</code> if the text is present within the element.
     */
    public boolean elementWithTagNameContainsText(final String tagName, final String text) {
        return WebDriverUtil.textValueContains(getDriver(), tagName(tagName), text);
    }

    /**
     * Verifies if the element contains the given text
     *
     * @param xpath The xpath.
     * @param text  The text value
     * @return <code>true</code> if the text is present within the element.
     */
    public boolean elementWithXpathContainsText(final String xpath, final String text) {
        return WebDriverUtil.textValueContains(getDriver(), xpath(xpath), text);
    }

    /**
     * Verifies if the element contains the given text
     *
     * @param linkText The linkText.
     * @param text     The text value
     * @return <code>true</code> if the text is present within the element.
     */
    public boolean elementWithLinkTextContainsText(final String linkText, final String text) {
        return WebDriverUtil.textValueContains(getDriver(), linkText(linkText), text);
    }

}
