/*
 * Licensed to the Fitting Project under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Fitting Project licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.fitting.selenium;

import org.fitting.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.fitting.selenium.SeleniumDataTypeConverter.convert;

/**
 * Selenium implementation of the {@link org.fitting.selenium.SeleniumAction}.
 * @author Barre Dijkstra
 * @since 1.0
 */
public class SeleniumAction implements FittingAction {
    /** Error message for when a non-existing search context has been provided. */
    private static final String ERROR_MESSAGE_NO_SEARCH_CONTEXT = "No search context provided.";
    /** The id of an (highly likely to be) unknown element. */
    private static final String UNKNOWN_ELEMENT_ID = "unknown_element_id_x3asdf4hd462345";
    /** The default search interval for element searches in milliseconds. */
    private static final int DEFAULT_SEARCH_INTERVAL = 300;
    /** The WebDriver of the window. */
    private final WebDriver driver;

    /**
     * Create a new SeleniumAction instance.
     * @param driver The Selenium WebDriver of the active window.
     */
    public SeleniumAction(WebDriver driver) {
        this.driver = driver;
    }

    /** {@inheritDoc} */
    @Override
    public Element getElement(final SearchContext searchContext, final Selector selector) throws NoSuchElementException {
        return getElement(searchContext, selector, new NoSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(Object... objects) {
                throw new NoSuchElementException(searchContext, selector);
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public Element getElement(SearchContext searchContext, Selector selector, NoSuchElementCallback noSuchElementCallback) throws FittingException {
        Element result = null;
        try {
            result = searchContext.findElementBy(selector);
        } catch (NullPointerException e) {
            throw new FormattedFittingException(ERROR_MESSAGE_NO_SEARCH_CONTEXT, e);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            if (noSuchElementCallback != null) {
                noSuchElementCallback.onNoSuchElementFound(selector);
            }
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public List<Element> getElements(org.fitting.SearchContext searchContext, Selector selector) {
        List<Element> elements = new ArrayList<Element>();
        try {
            searchContext.findElementsBy(selector);
        } catch (NullPointerException e) {
            throw new FormattedFittingException(ERROR_MESSAGE_NO_SEARCH_CONTEXT, e);
        }
        return elements;
    }

    /** {@inheritDoc} */
    @Override
    public void waitXSeconds(int duration) {
        try {
            waitForElement(null, SeleniumSelector.byId(UNKNOWN_ELEMENT_ID), duration);
        } catch (NoSuchElementException e) {
            // Ignore.
        }
    }

    /** {@inheritDoc} */
    @Override
    public void waitForElement(final SearchContext searchContext, final Selector selector, int timeout) throws NoSuchElementException {
        waitForElementPresent(new ExpectedCondition<Element>() {
            @Override
            public Element apply(WebDriver driver) {
                Element element;
                if (searchContext == null) {
                    element = convert(driver.findElement(convert(selector)));
                } else {
                    element = searchContext.findElementBy(selector);
                }
                return element;
            }
        }, timeout, createNoSuchElementExceptionCallback(searchContext, selector));
    }

    /** {@inheritDoc} */
    @Override
    public void waitForElementWithContent(final SearchContext searchContext, final Selector selector, final String content, int timeout) throws NoSuchElementException {
        waitForElementPresent(new ExpectedCondition<Element>() {
            @Override
            public Element apply(WebDriver driver) {
                boolean found = false;
                Element element;
                if (searchContext == null) {
                    element = convert(driver.findElement(convert(selector)));
                } else {
                    element = searchContext.findElementBy(selector);
                }
                if (element != null) {
                    final String text = element.getValue();
                    found = text != null && text.contains(content);
                }
                return found ? element : null;
            }
        }, timeout, createNoSuchElementExceptionCallback(searchContext, selector));
    }

    /** {@inheritDoc} */
    @Override
    public String getAttributeValue(SearchContext searchContext, Selector selector, String attributeName) throws NoSuchElementException {
        return getElement(searchContext, selector).getAttributeValue(attributeName);
    }

    /** {@inheritDoc} */
    @Override
    public boolean elementAttributeValueContains(SearchContext searchContext, Selector selector, String attributeName, String text) throws NoSuchElementException {
        boolean contains = false;
        String attributeValue = getAttributeValue(searchContext, selector, attributeName);
        if (!isEmpty(attributeValue)) {
            contains = attributeValue.contains(text);
        }
        return contains;
    }

    /** {@inheritDoc} */
    @Override
    public String getTextValue(SearchContext searchContext, Selector selector) throws NoSuchElementException {
        return getElement(searchContext, selector).getValue();
    }

    /** {@inheritDoc} */
    @Override
    public boolean elementTextValueContains(SearchContext searchContext, Selector selector, String text) throws NoSuchElementException {
        boolean contains = false;
        String textValue = getElement(searchContext, selector).getValue();
        if (!isEmpty(textValue)) {
            contains = textValue.contains(text);
        }
        return contains;
    }

    /** {@inheritDoc} */
    @Override
    public void clickElement(SearchContext searchContext, Selector selector) throws NoSuchElementException {
        getElement(searchContext, selector).click();
    }

    /** {@inheritDoc} */
    @Override
    public void refresh(ElementContainer elementContainer) {
        // TODO Figure out if we want this on this interface actually...
        elementContainer.refresh();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isContainerPresent(SearchContext searchContext, Selector selector) {
        // TODO Figure out if we want this on this interface actually...
        boolean present = false;
        try {
            searchContext.findElementBy(selector);
            present = true;
        } catch (Exception e) {
            // Ignore.
        }
        return present;
    }

    /** {@inheritDoc} */
    @Override
    public ElementContainer getContainer(SearchContext searchContext, Selector selector) throws NoSuchElementException {
        // TODO Implement me, but first figure out if we want this on this interface actually...
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ElementContainer getContainer(SearchContext searchContext, Selector selector, NoSuchElementCallback noSuchElementCallback) {
        // TODO Implement me, but first figure out if we want this on this interface actually...
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isModalContainerPresent() {
        // TODO Implement me, but first figure out if we want this on this interface actually...
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public String getModalContainerText() {
        // TODO Implement me, but first figure out if we want this on this interface actually...
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void acceptModalContainer() {
        // TODO Implement me, but first figure out if we want this on this interface actually...
    }

    /** {@inheritDoc} */
    @Override
    public void dismissModalContainer() {
        // TODO Implement me, but first figure out if we want this on this interface actually...
    }

    /** {@inheritDoc} */
    @Override
    public ElementContainer getModalContainer() {
        // TODO Implement me, but first figure out if we want this on this interface actually...
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isElementValueSelectable(SearchContext searchContext, Selector selector, String value) throws NoSuchElementException {
        // TODO Implement me!
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void selectElementValue(SearchContext searchContext, Selector selector, String value) throws NoSuchElementException {
        // TODO Implement me!
    }

    /** {@inheritDoc} */
    @Override
    public void selectElementValue(SearchContext searchContext, Selector selector, String value, NoSuchElementCallback noSuchElementCallback) {
        // TODO Implement me!
    }

    /** {@inheritDoc} */
    @Override
    public boolean isElementCheckbox(SearchContext searchContext, Selector selector) throws NoSuchElementException {
        // TODO Implement me!
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isElementValueSelected(SearchContext searchContext, Selector selector, String value) throws NoSuchElementException {
        // TODO Implement me!
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isElementValueSelected(SearchContext searchContext, Selector selector, String value, NoSuchElementCallback noSuchElementCallback) {
        // TODO Implement me!
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isElementValueSettable(SearchContext searchContext, Selector selector) throws NoSuchElementException {
        // TODO Implement me!
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void setValueForElement(SearchContext searchContext, Selector selector, String value) {
        getElement(searchContext, selector).sendKeys(value);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isElementPresent(SearchContext searchContext, Selector selector) {
        return getElement(searchContext, selector, null) != null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isElementDisplayed(SearchContext searchContext, Selector selector) throws NoSuchElementException {
        return getElement(searchContext, selector).isDisplayed();
    }

    /**
     * Wait for the expected condition to occur within the given time-out.
     * @param expectedCondition The condition to wait for.
     * @param seconds           The timeout in seconds.
     * @param callback          The callback to execute when the condition was not met within the timeout.
     * @param <E>               The type of condition.
     * @return <code>true</code> if the condition was met within the timeout period.
     */
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

    /**
     * Create a {@link org.fitting.NoSuchElementCallback} instance which throws a {@link org.fitting.NoSuchElementException} for the given search context and selector.
     * @param searchContext The search context where was searched on.
     * @param selector      The selector used.
     * @return The callback.
     */
    private NoSuchElementCallback createNoSuchElementExceptionCallback(final SearchContext searchContext, final Selector selector) {
        return new NoSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(Object... objects) {
                throw new NoSuchElementException(searchContext, selector);
            }
        };
    }
}