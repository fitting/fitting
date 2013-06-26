package org.fitting.selenium;

import java.util.ArrayList;
import java.util.List;

import org.fitting.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.fitting.selenium.SeleniumDataTypeConverter.convert;

/**
 * Selenium implementation of the {@link FittingSeleniumAction}.
 */
public class FittingSeleniumAction implements FittingAction {
    /**
     * Error message for when a non-existing search context has been provided.
     */
    private static final String ERROR_MESSAGE_NO_SEARCH_CONTEXT = "No search context provided.";
    /**
     * The id of an (highly likely to be) unknown element.
     */
    private static final String UNKNOWN_ELEMENT_ID = "unknown_element_id_x3asdf4hd462345";
    /**
     * The default search interval for element searches in milliseconds.
     */
    private static final int DEFAULT_SEARCH_INTERVAL = 300;

    /**
     * The WebDriver of the window.
     */
    private final WebDriver driver;

    /**
     * Create a new FittingSeleniumAction instance.
     *
     * @param driver The Selenium WebDriver of the active window.
     */
    public FittingSeleniumAction(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public Element getElement(final SearchContext searchContext, final By byClause) throws NoSuchElementException {
        return getElement(searchContext, byClause, new NoSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(Object... objects) {
                throw new NoSuchElementException(searchContext, byClause);
            }
        });
    }

    @Override
    public Element getElement(SearchContext searchContext, By byClause, NoSuchElementCallback noSuchElementCallback) throws FittingException {
        Element result = null;
        try {
            result = searchContext.findElementBy(byClause);
        } catch (NullPointerException e) {
            throw new FormattedFittingException(ERROR_MESSAGE_NO_SEARCH_CONTEXT, e);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            if (noSuchElementCallback != null) {
                noSuchElementCallback.onNoSuchElementFound(byClause);
            }
        }
        return result;
    }

    @Override
    public List<Element> getElements(org.fitting.SearchContext searchContext, By byClause) {
        List<Element> elements = new ArrayList<Element>();
        try {
            searchContext.findElementsBy(byClause);
        } catch (NullPointerException e) {
            throw new FormattedFittingException(ERROR_MESSAGE_NO_SEARCH_CONTEXT, e);
        }
        return elements;
    }

    @Override
    public void waitXSeconds(int duration) {
        // TODO Implement me, as soon as By.id equivalent is implemented.
        // waitForElementPresent(null, SeleniumBy.id(UNKNOWN_ELEMENT_ID), duration, null);
    }

    @Override
    public void waitForElement(final SearchContext searchContext, final By byClause, int timeout) throws NoSuchElementException {
        waitForElementPresent(new ExpectedCondition<Element>() {
            @Override
            public Element apply(WebDriver driver) {
                Element element = null;
                if (searchContext == null) {
                    element = convert(driver.findElement(convert(byClause)));
                } else {
                    element = searchContext.findElementBy(byClause);
                }
                return element;
            }
        }, timeout, createNoSuchElementExceptionCallback(searchContext, byClause));
    }

    @Override
    public void waitForElementWithContent(final SearchContext searchContext, final By byClause, final String content, int timeout) throws NoSuchElementException {
        waitForElementPresent(new ExpectedCondition<Element>() {
            @Override
            public Element apply(WebDriver driver) {
                boolean found = false;
                Element element = null;
                if (searchContext == null) {
                    element = convert(driver.findElement(convert(byClause)));
                } else {
                    element = searchContext.findElementBy(byClause);
                }
                if (element != null) {
                    final String text = element.getValue();
                    found = text != null && text.contains(content);
                }
                return found ? element : null;
            }
        }, timeout, createNoSuchElementExceptionCallback(searchContext, byClause));
    }

    private <E> boolean waitForElementPresent(final ExpectedCondition<E> expectedCondition, final int seconds, final NoSuchElementCallback callback) {
        boolean present = false;
        try {
            new WebDriverWait(driver, seconds, DEFAULT_SEARCH_INTERVAL).until(expectedCondition);
            present = true;
        } catch (TimeoutException e) {
            if (callback != null) {
                callback.onNoSuchElementFound();
            }
        }
        return present;
    }

    @Override
    public String getAttributeValue(SearchContext searchContext, By byClause, String attributeName) throws NoSuchElementException {
        return getElement(searchContext, byClause).getAttributeValue(attributeName);
    }

    @Override
    public boolean elementAttributeValueContains(SearchContext searchContext, By byClause, String attributeName, String text) throws NoSuchElementException {
        boolean contains = false;
        String attributeValue = getAttributeValue(searchContext, byClause, attributeName);
        if (!isEmpty(attributeValue)) {
            contains = attributeValue.contains(text);
        }
        return contains;
    }

    @Override
    public String getTextValue(SearchContext searchContext, By byClause) throws NoSuchElementException {
        return getElement(searchContext, byClause).getValue();
    }

    @Override
    public boolean elementTextValueContains(SearchContext searchContext, By byClause, String text) throws NoSuchElementException {
        boolean contains = false;
        String textValue = getElement(searchContext, byClause).getValue();
        if (!isEmpty(textValue)) {
            contains = textValue.contains(text);
        }
        return contains;
    }

    @Override
    public void clickElement(SearchContext searchContext, By byClause) throws NoSuchElementException {
        getElement(searchContext, byClause).click();
    }

    @Override
    public void refresh(ElementContainer elementContainer) {
        // TODO Figure out if we want this on this interface actually...
        elementContainer.refresh();
    }

    @Override
    public boolean isContainerPresent(SearchContext searchContext, By byClause) {
        // TODO Figure out if we want this on this interface actually...
        boolean present = false;
        try {
            searchContext.findElementBy(byClause);
            present = true;
        } catch (Exception e) {
            // Ignore.
        }
        return present;
    }

    @Override
    public ElementContainer getContainer(SearchContext searchContext, By byClause) throws NoSuchElementException {
        // TODO Implement me, but first figure out if we want this on this interface actually...
        return null;
    }

    @Override
    public ElementContainer getContainer(SearchContext searchContext, By byClause, NoSuchElementCallback noSuchElementCallback) {
        // TODO Implement me, but first figure out if we want this on this interface actually...
        return null;
    }

    @Override
    public boolean isModalContainerPresent() {
        // TODO Implement me, but first figure out if we want this on this interface actually...
        return false;
    }

    @Override
    public String getModalContainerText() {
        // TODO Implement me, but first figure out if we want this on this interface actually...
        return null;
    }

    @Override
    public void acceptModalContainer() {
        // TODO Implement me, but first figure out if we want this on this interface actually...
    }

    @Override
    public void dismissModalContainer() {
        // TODO Implement me, but first figure out if we want this on this interface actually...
    }

    @Override
    public ElementContainer getModalContainer() {
        // TODO Implement me, but first figure out if we want this on this interface actually...
        return null;
    }

    @Override
    public void isElementValueSelectable(SearchContext searchContext, By byClause, String value) throws NoSuchElementException {
        // TODO Implement me!
    }

    @Override
    public void selectElementValue(SearchContext searchContext, By byClause, String value) throws NoSuchElementException {
        // TODO Implement me!
    }

    @Override
    public void selectElementValue(SearchContext searchContext, By byClause, String value, NoSuchElementCallback noSuchElementCallback) {
        // TODO Implement me!
    }

    @Override
    public void isElementCheckbox(SearchContext searchContext, By byClause) throws NoSuchElementException {
        // TODO Implement me!
    }

    @Override
    public boolean isElementValueSelected(SearchContext searchContext, By byClause, String value) throws NoSuchElementException {
        // TODO Implement me!
        return false;
    }

    @Override
    public boolean isElementValueSelected(SearchContext searchContext, By byClause, String value, NoSuchElementCallback noSuchElementCallback) {
        // TODO Implement me!
        return false;
    }

    @Override
    public boolean isElementValueSettable(SearchContext searchContext, By byClause) throws NoSuchElementException {
        // TODO Implement me!
        return false;
    }

    @Override
    public void setValueForElement(SearchContext searchContext, By byClause, String value) {
        // TODO Implement me!
    }

    @Override
    public boolean isElementPresent(SearchContext searchContext, By byClause) {
        return getElement(searchContext, byClause, null) != null;
    }

    @Override
    public boolean isElementDisplayed(SearchContext searchContext, By byClause) throws NoSuchElementException {
        return getElement(searchContext, byClause).isDisplayed();
    }

    private NoSuchElementCallback createNoSuchElementExceptionCallback(final SearchContext searchContext, final By byClause) {
        return new NoSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(Object... objects) {
                throw new NoSuchElementException(searchContext, byClause);
            }
        };
    }
}
