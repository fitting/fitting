/*
 * Licensed to the Fitting Project under one
 * or more contributor license agreements.  See the NOTICE.txt file
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

import static org.fitting.selenium.SeleniumDataTypeConverter.convert;

/**
 * Collection of generic Selenium utility methods.
 */
public class SeleniumUtil {
    /** The id of an (highly likely to be) unknown element. */
    private static final String UNKNOWN_ELEMENT_ID = "unknown_element_id_x3asdf4hd462345";

    public static void wait(final WebDriver driver, final SearchContext source, final int timeout) {
        waitForElement(driver, source, SeleniumSelector.byId(UNKNOWN_ELEMENT_ID), timeout, new NoSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(final Object... objects) {
                // Ignore.
            }
        });
    }

    /**
     * Wait for an element to be present in a search context within a given timeout.
     *
     * @param driver        The Selenium web driver of the window the search context is on.
     * @param searchContext The search context to search on.
     * @param selector      The selector of the element to wait for.
     * @param timeout       The timeout in seconds.
     *
     * @throws NoSuchElementException When the element was not found on the search context within the given timeout.
     */
    public static void waitForElement(final WebDriver driver, final SearchContext searchContext, final Selector selector, int timeout) throws NoSuchElementException {
        waitForElement(driver, searchContext, selector, timeout, createNoSuchElementCallback(searchContext, selector));
    }

    /**
     * Wait for an element to be present in a search context within a given timeout.
     *
     * @param driver        The Selenium web driver of the window the search context is on.
     * @param searchContext The search context to search on.
     * @param selector      The selector of the element to wait for.
     * @param timeout       The timeout in seconds.
     * @param callback      The callback to call if the element was not found within the timeout.
     */
    public static void waitForElement(final WebDriver driver, final SearchContext searchContext, final Selector selector, int timeout, final NoSuchElementCallback callback)
            throws NoSuchElementException {
        waitFor(driver, new ExpectedCondition<Element>() {
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
        }, timeout, callback);
    }

    /**
     * Wait for an element with specific content to be present in a search context within a given timeout.
     *
     * @param driver        The Selenium web driver of the window the search context is on.
     * @param searchContext The search context to search on.
     * @param selector      The selector of the element to wait for.
     * @param content       The content of the element to wait for.
     * @param timeout       The timeout in seconds.
     */
    public static void waitForElementWithContent(final WebDriver driver, final SearchContext searchContext, final Selector selector, final String content, int timeout)
            throws NoSuchElementException {
        waitFor(driver, new ExpectedCondition<Element>() {
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
        }, timeout, createNoSuchElementCallback(searchContext, selector));
    }

    /**
     * Wait for an element with specific content to be present in a search context within a given timeout.
     *
     * @param driver        The Selenium web driver of the window the search context is on.
     * @param searchContext The search context to search on.
     * @param selector      The selector of the element to wait for.
     * @param content       The content of the element to wait for.
     * @param timeout       The timeout in seconds.
     * @param callback      The callback to call if the element was not found within the timeout.
     */
    public static void waitForElementWithContent(final WebDriver driver, final SearchContext searchContext, final Selector selector, final String content, int timeout,
            final NoSuchElementCallback callback)
            throws NoSuchElementException {
        waitFor(driver, new ExpectedCondition<Element>() {
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
        }, timeout, callback);
    }

    /**
     * Wait for the expected condition to occur within the given time-out.
     *
     * @param driver            The Selenium web driver to use.
     * @param expectedCondition The condition to wait for.
     * @param seconds           The timeout in seconds.
     * @param callback          The callback to execute when the condition was not met within the timeout.
     * @param <E>               The type of condition.
     *
     * @return <code>true</code> if the condition was met within the timeout period.
     */
    public static <E> boolean waitFor(final WebDriver driver, final ExpectedCondition<E> expectedCondition, final int seconds, final NoSuchElementCallback callback) {
        boolean present = false;
        try {
            new WebDriverWait(driver, seconds).until(expectedCondition);
            present = true;
        } catch (TimeoutException e) {
            if (callback != null) {
                callback.onNoSuchElementFound();
            }
        }
        return present;
    }

    /**
     * Create a {@link NoSuchElementCallback} that throws a {@link NoSuchElementException}.
     *
     * @param context  The {@link SearchContext} used to search for the element.
     * @param selector The {@link Selector} used to search for the element.
     *
     * @return The {@link NoSuchElementCallback}.
     */
    private static NoSuchElementCallback createNoSuchElementCallback(final SearchContext context, final Selector selector) {
        return new NoSuchElementCallback() {
            @Override
            public void onNoSuchElementFound(final Object... objects) {
                throw new NoSuchElementException(context, selector);
            }
        };
    }
}
