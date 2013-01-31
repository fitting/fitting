package org.fitting.fixture;

import org.fitting.FitnesseContainer;
import org.fitting.FitnesseContext;
import org.fitting.SeleniumWindow;
import org.fitting.WebDriverUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.openqa.selenium.By.*;
import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.Keys.ESCAPE;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/** Test class for SeleniumElementFixture. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FitnesseContainer.class, FitnesseContext.class, Select.class, SeleniumElementFixture.class,
        WebDriverUtil.class, SeleniumWindow.class})
public class SeleniumElementFixtureTest {
    /** The instance of the class under test. */
    private SeleniumElementFixture fixture;

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
    @Mock
    private Select select;

    @Before
    public void setUp() throws Exception {
        mockStatic(FitnesseContainer.class);
        mockStatic(WebDriverUtil.class);
        when(FitnesseContainer.get()).thenReturn(context);
        when(context.getDriver()).thenReturn(driver);
        when(context.getActiveWindow()).thenReturn(window);
        Set<String> handles = new HashSet<String>();
        handles.add("windowHandle");
        when(context.getWindowHandles()).thenReturn(handles);
        when(window.getId()).thenReturn("windowHandle");
        fixture = new SeleniumElementFixture();
    }

    @Test
    public void shouldGetElementWithIdIsPresent() throws Exception {
        fixture.elementWithIdIsPresent("id");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetElementWithNameIsPresent() throws Exception {
        fixture.elementWithNameIsPresent("name");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetElementWithClassNameIsPresent() throws Exception {
        fixture.elementWithClassNameIsPresent("className");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetElementWithTagNameIsPresent() throws Exception {
        fixture.elementWithTagNameIsPresent("tagName");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetElementWithXpathIsPresent() throws Exception {
        fixture.elementWithXpathIsPresent("xpath");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetElementWithLinkTextIsPresent() throws Exception {
        fixture.elementWithLinkTextIsPresent("linkText");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetSubElementWithIdIsPresent() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.subElementWithIdIsPresent("id");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetSubElementWithNameIsPresent() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.subElementWithNameIsPresent("name");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetSubElementWithClassNameIsPresent() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.subElementWithClassNameIsPresent("className");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetSubElementWithTagNameIsPresent() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.subElementWithTagNameIsPresent("tagName");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetSubElementWithXpathIsPresent() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.subElementWithXpathIsPresent("xpath");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldGetSubElementWithLinkTextIsPresent() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.subElementWithLinkTextIsPresent("linkText");
        verifyStatic();
        WebDriverUtil.isElementPresent(isA(SearchContext.class), isA(By.class));
    }

    @Test
    public void shouldSelectElementWithId() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(driver.findElement(isA(By.class))).thenReturn(element);
        fixture.selectElementWithId("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectElementWithName() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(driver.findElement(isA(By.class))).thenReturn(element);
        fixture.selectElementWithName("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectElementWithClassName() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(driver.findElement(isA(By.class))).thenReturn(element);
        fixture.selectElementWithClassName("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectElementWithTagName() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(driver.findElement(isA(By.class))).thenReturn(element);
        fixture.selectElementWithTagName("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectElementWithXpath() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(driver.findElement(isA(By.class))).thenReturn(element);
        fixture.selectElementWithXpath("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectElementWithLinkText() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(driver.findElement(isA(By.class))).thenReturn(element);
        fixture.selectElementWithLinkText("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectSubElementWithId() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(element.findElement(isA(By.class))).thenReturn(element);
        fixture.selectSubElementWithId("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectSubElementWithName() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(element.findElement(isA(By.class))).thenReturn(element);
        fixture.selectSubElementWithName("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectSubElementWithClassName() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(element.findElement(isA(By.class))).thenReturn(element);
        fixture.selectSubElementWithClassName("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectSubElementWithTagName() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(element.findElement(isA(By.class))).thenReturn(element);
        fixture.selectSubElementWithTagName("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectSubElementWithXpath() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(element.findElement(isA(By.class))).thenReturn(element);
        fixture.selectSubElementWithXpath("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldSelectSubElementWithLinkText() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        when(element.findElement(isA(By.class))).thenReturn(element);
        fixture.selectSubElementWithLinkText("by");
        Assert.assertEquals(window.getSelectedWebElement(), element);
    }

    @Test
    public void shouldGetAttributeValue() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.attributeValue("attributeName");
        verifyStatic();
        WebDriverUtil.getAttributeValue(isA(WebElement.class), eq("attributeName"));
    }

    @Test
    public void shouldIndicateIfAttributeValueContains() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.attributeValueContains("attributeName", "value");
        verifyStatic();
        WebDriverUtil.attributeValueContains(isA(WebElement.class), eq("attributeName"), eq("value"));
    }

    @Test
    public void shouldGetTextValue() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.textValue();
        verifyStatic();
        WebDriverUtil.getTextValue(isA(WebElement.class));
    }

    @Test
    public void shouldIndicateIfTextValueContains() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.textValueContains("value");
        verifyStatic();
        WebDriverUtil.textValueContains(isA(WebElement.class), eq("value"));
    }

    @Test
    public void shouldClick() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.click();
        verifyStatic();
        WebDriverUtil.click(isA(WebDriver.class), isA(WebElement.class));
    }

    @Test
    public void shouldSelectRadioButtonWithNameAndValue() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.selectRadioButtonWithNameAndValue("name", "value");
        verifyStatic();
        WebDriverUtil.selectRadioButtonValue(isA(WebDriver.class), isA(By.class), eq("value"));
    }

    @Test
    public void shouldSelectRadioButtonWithIdAndValue() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.selectRadioButtonWithIdAndValue("id", "value");
        verifyStatic();
        WebDriverUtil.selectRadioButtonValue(isA(WebDriver.class), isA(By.class), eq("value"));
    }

    @Test
    public void shouldSetValueForInputWithName() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.setValueForInputWithName("value", "name");
        verifyStatic();
        WebDriverUtil.setValueForInput(isA(WebDriver.class), isA(By.class), eq("value"));
    }

    @Test
    public void shouldSetValueForInputWithId() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.setValueForInputWithId("value", "id");
        verifyStatic();
        WebDriverUtil.setValueForInput(isA(WebDriver.class), isA(By.class), eq("value"));
    }

    @Test
    public void shouldGetValueForInputWithName() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.valueForInputWithNameIs("name");
        verifyStatic();
        WebDriverUtil.valueForInputIs(isA(WebDriver.class), isA(By.class));
    }

    @Test
    public void shouldGetValueForInputWithId() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.valueForInputWithIdIs("id");
        verifyStatic();
        WebDriverUtil.valueForInputIs(isA(WebDriver.class), isA(By.class));
    }

    @Test
    public void shouldSelectOptionWithTextFromDropdownWithName() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.selectOptionWithTextFromDropdownWithName("text", "name");
        verifyStatic();
        WebDriverUtil.selectOptionWithTextFromDropdownOrSelect(isA(WebDriver.class), isA(By.class), eq("text"));
    }

    @Test
    public void shouldGetSelectedOptionTextFromDropdownWithNameIs() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.selectedOptionTextFromDropdownWithNameIs("name");
        verifyStatic();
        WebDriverUtil.selectedOptionTextFromDropdownOrSelectIs(isA(WebDriver.class), isA(By.class));
    }

    @Test
    public void shouldSelectOptionWithTextFromDropdownWithId() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.selectOptionWithTextFromDropdownWithId("text", "id");
        verifyStatic();
        WebDriverUtil.selectOptionWithTextFromDropdownOrSelect(isA(WebDriver.class), isA(By.class), eq("text"));
    }

    @Test
    public void shouldGetSelectedOptionTextFromDropdownWithIdIs() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.selectedOptionTextFromDropdownWithIdIs("id");
        verifyStatic();
        WebDriverUtil.selectedOptionTextFromDropdownOrSelectIs(isA(WebDriver.class), isA(By.class));
    }

    @Test
    public void shouldIndicatePageContainsValue() throws Exception {
        when(window.getSelectedWebElement()).thenReturn(element);
        fixture.pageContains("page-value");
        verifyStatic();
        WebDriverUtil.pageContains(isA(WebDriver.class), eq("page-value"));
    }

    @Test
    public void shouldPressEscapeOnElementWithId() throws Exception {
        fixture.pressEscapeForElementWithId("id");
        verifyStatic();
        WebDriverUtil.sendFunctionKeyToElement(isA(WebDriver.class), eq(id("id")), eq(ESCAPE));
    }

    @Test
    public void shouldPressEscapeOnElementWithName() throws Exception {
        fixture.pressEscapeForElementWithName("name");
        verifyStatic();
        WebDriverUtil.sendFunctionKeyToElement(isA(WebDriver.class), eq(name("name")), eq(ESCAPE));
    }

    @Test
    public void shouldPressEscapeOnElementWithClassName() throws Exception {
        fixture.pressEscapeForElementWithClassName("className");
        verifyStatic();
        WebDriverUtil.sendFunctionKeyToElement(isA(WebDriver.class), eq(className("className")), eq(ESCAPE));
    }

    @Test
    public void shouldPressEscapeOnElementWithXpath() throws Exception {
        fixture.pressEscapeForElementWithXpath("xpath");
        verifyStatic();
        WebDriverUtil.sendFunctionKeyToElement(isA(WebDriver.class), eq(xpath("xpath")), eq(ESCAPE));
    }

    @Test
    public void shouldPressEnterOnElementWithId() throws Exception {
        fixture.pressEnterForElementWithId("id");
        verifyStatic();
        WebDriverUtil.sendFunctionKeyToElement(isA(WebDriver.class), eq(id("id")), eq(ENTER));
    }

    @Test
    public void shouldPressEnterOnElementWithName() throws Exception {
        fixture.pressEnterForElementWithName("name");
        verifyStatic();
        WebDriverUtil.sendFunctionKeyToElement(isA(WebDriver.class), eq(name("name")), eq(ENTER));
    }

    @Test
    public void shouldPressEnterOnElementWithClassName() throws Exception {
        fixture.pressEnterForElementWithClassName("className");
        verifyStatic();
        WebDriverUtil.sendFunctionKeyToElement(isA(WebDriver.class), eq(className("className")), eq(ENTER));
    }

    @Test
    public void shouldPressEnterOnElementWithXpath() throws Exception {
        fixture.pressEnterForElementWithXpath("xpath");
        verifyStatic();
        WebDriverUtil.sendFunctionKeyToElement(isA(WebDriver.class), eq(xpath("xpath")), eq(ENTER));
    }

    @Test
    public void shouldToggleCheckboxWithXpath() throws Exception {
        fixture.toggleCheckboxWithXpath("xpath");
        verifyStatic();
        WebDriverUtil.toggleCheckbox(isA(WebDriver.class), eq(xpath("xpath")));
    }

    @Test
    public void shouldToggleCheckboxWithName() throws Exception {
        fixture.toggleCheckboxWithName("name");
        verifyStatic();
        WebDriverUtil.toggleCheckbox(isA(WebDriver.class), eq(name("name")));
    }

    @Test
    public void shouldToggleCheckboxWithId() throws Exception {
        fixture.toggleCheckboxWithId("id");
        verifyStatic();
        WebDriverUtil.toggleCheckbox(isA(WebDriver.class), eq(id("id")));
    }

    @Test
    public void shouldGetCheckForCheckboxWithId() throws Exception {
        fixture.isCheckboxWithIdChecked("id");
        verifyStatic();
        WebDriverUtil.isCheckboxChecked(isA(WebDriver.class), eq(id("id")));
    }

    @Test
    public void shouldGetCheckForCheckboxWithName() throws Exception {
        fixture.isCheckboxWithNameChecked("name");
        verifyStatic();
        WebDriverUtil.isCheckboxChecked(isA(WebDriver.class), eq(name("name")));
    }

    @Test
    public void shouldGetCheckForCheckboxWithXpath() throws Exception {
        fixture.isCheckboxWithXpathChecked("xpath");
        verifyStatic();
        WebDriverUtil.isCheckboxChecked(isA(WebDriver.class), eq(xpath("xpath")));
    }

    @Test
    public void shouldIndicateIfElementWithIdContainsText() throws Exception {
        fixture.elementWithIdContainsText("id", "text");
        verifyStatic();
        WebDriverUtil.textValueContains(isA(SearchContext.class), eq(By.id("id")), eq("text"));
    }

    @Test
    public void shouldIndicateIfElementWithNameContainsText() throws Exception {
        fixture.elementWithNameContainsText("name", "text");
        verifyStatic();
        WebDriverUtil.textValueContains(isA(SearchContext.class), eq(By.name("name")), eq("text"));
    }

    @Test
    public void shouldIndicateIfElementWithClassNameContainsText() throws Exception {
        fixture.elementWithClassNameContainsText("className", "text");
        verifyStatic();
        WebDriverUtil.textValueContains(isA(SearchContext.class), eq(By.className("className")), eq("text"));
    }

    @Test
    public void shouldIndicateIfElementWithTagNameContainsText() throws Exception {
        fixture.elementWithTagNameContainsText("tagName", "text");
        verifyStatic();
        WebDriverUtil.textValueContains(isA(SearchContext.class), eq(By.tagName("tagName")), eq("text"));
    }

    @Test
    public void shouldIndicateIfElementWithXpathContainsText() throws Exception {
        fixture.elementWithXpathContainsText("xpath", "text");
        verifyStatic();
        WebDriverUtil.textValueContains(isA(SearchContext.class), eq(By.xpath("xpath")), eq("text"));
    }

    @Test
    public void shouldIndicateIfElementWithLinkTextContainsText() throws Exception {
        fixture.elementWithLinkTextContainsText("linkText", "text");
        verifyStatic();
        WebDriverUtil.textValueContains(isA(SearchContext.class), eq(By.linkText("linkText")), eq("text"));
    }

}

