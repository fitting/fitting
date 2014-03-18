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

package org.fitting;

import java.util.List;

/**
 * Generic actions for elements.
 */
public interface FittingAction {
    /**
     * Find a single element matching a selector, starting from the given search context.
     * @param searchContext The search context to start searcing from.
     * @param selector      The selector.
     * @return The element.
     * @throws NoSuchElementException When no element matching the selector was found on the search context.
     */
    Element getElement(SearchContext searchContext, Selector selector) throws NoSuchElementException;

    /**
     * Find a single element matching a selector, starting from the given search context.
     * @param searchContext         The search context to start searcing from.
     * @param selector              The selector.
     * @param noSuchElementCallback Callback to execute when no matching element was found.
     * @return The element or <code>null</code> if no matching element was found.
     */
    Element getElement(SearchContext searchContext, Selector selector, NoSuchElementCallback noSuchElementCallback);

    /**
     * Get all elements matching the selector, starting from the given search context.
     * @param searchContext The search context.
     * @param selector      The selector.
     * @return All matching elements.
     */
    List<Element> getElements(SearchContext searchContext, Selector selector);

    /**
     * Tell the implementation to wait/pause execution for a number of seconds.
     * @param duration The duration to wait in seconds.
     */
    void waitXSeconds(int duration);

    /**
     * Wait for an element to load.
     * @param searchContext The search context to look for the element on.
     * @param selector      The selector of the element.
     * @param timeout       The timeout in seconds to wait.
     * @throws NoSuchElementException When no matching element was found within the timeout duration.
     */
    void waitForElement(SearchContext searchContext, Selector selector, int timeout) throws NoSuchElementException;

    /**
     * Wait for an element with specific content to load.
     * @param searchContext The search context to look for the element on.
     * @param selector      The selector of the element.
     * @param content       The content of the element.
     * @param timeout       The timeout in seconds to wait.
     * @throws NoSuchElementException When no matching element was found within the timeout duration.
     */
    void waitForElementWithContent(SearchContext searchContext, Selector selector, String content, int timeout) throws NoSuchElementException;

    /**
     * Get the attribute value of an element.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @param attributeName The attribute name.
     * @return The attribute value.
     * @throws NoSuchElementException When the element couldn't be found or no attribute was available on the element with the given name.
     */
    String getAttributeValue(SearchContext searchContext, Selector selector, String attributeName) throws NoSuchElementException;

    /**
     * Check if the attribute value for an element attribute contains a given text.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @param attributeName The attribute name.
     * @param text          The text to look for.
     * @return <code>true</code> if the attribute value contains the given text.
     * @throws NoSuchElementException When the element couldn't be found or no attribute was available on the element with the given name.
     */
    boolean elementAttributeValueContains(SearchContext searchContext, Selector selector, String attributeName, String text) throws NoSuchElementException;

    /**
     * Get the text value of an element.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @return The text value.
     * @throws NoSuchElementException When no matching element could be found.
     */
    String getTextValue(SearchContext searchContext, Selector selector) throws NoSuchElementException;

    /**
     * Check if an element text value contains a given text.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @param text          The text to look for.
     * @return <code>true</code> if the element text value contains the given text.
     * @throws NoSuchElementException When no matching element could be found.
     */
    boolean elementTextValueContains(SearchContext searchContext, Selector selector, String text) throws NoSuchElementException;

    /**
     * Click an element.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @throws NoSuchElementException When no matching element could be found.
     */
    void clickElement(SearchContext searchContext, Selector selector) throws NoSuchElementException;

    /**
     * Refresh an element container.
     * <p>
     * TODO Decide whether this method is valid on here or if it should be moved to {@link ElementContainerProvider} or just {@link ElementContainer#refresh()}.
     * </p>
     * @param elementContainer The container.
     */
    void refresh(ElementContainer elementContainer);

    /**
     * Check if an element container is present on the search context.
     * <p>
     * TODO Decide whether this method is valid on here or if it should be moved to {@link ElementContainerProvider}.
     * </p>
     * @param searchContext The search context to find the container on.
     * @param selector      The selector for the container.
     * @return <code>true</code> if the container is present.
     */
    boolean isContainerPresent(SearchContext searchContext, Selector selector);

    /**
     * Get an element container from the given search context.
     * <p>
     * TODO Decide whether this method is valid on here or if it should be moved to {@link ElementContainerProvider}.
     * </p>
     * @param searchContext The search context to find the container on.
     * @param selector      The selector for the container.
     * @return The container.
     * @throws NoSuchElementException When no matching container was found.
     */
    ElementContainer getContainer(SearchContext searchContext, Selector selector) throws NoSuchElementException;

    /**
     * Get an element container from the given search context.
     * <p>
     * TODO Decide whether this method is valid on here or if it should be moved to {@link ElementContainerProvider}.
     * </p>
     * @param searchContext         The search context to find the container on.
     * @param selector              The selector for the container.
     * @param noSuchElementCallback The callback to execute if no matching container could be found.
     * @return The container or <code>null</code> if no matching container was found.
     */
    ElementContainer getContainer(SearchContext searchContext, Selector selector, NoSuchElementCallback noSuchElementCallback);

    /**
     * Check if a modal container is present.
     * <p>
     * TODO Decide whether this method is valid on here or if it should be moved to {@link ElementContainerProvider}.
     * </p>
     * @return <code>true</code> if a modal container is present.
     */
    boolean isModalContainerPresent();

    /**
     * Get the text of the modal container.
     * <p>
     * TODO Decide whether this method is valid on here or if it should be moved to {@link ElementContainerProvider}.
     * </p>
     * @return The text.
     * @throws NoSuchElementException When no modal container was present.
     * @see org.fitting.FittingAction#isModalContainerPresent()
     */
    String getModalContainerText() throws NoSuchElementException;

    /**
     * Accept the modal container.
     * <p>
     * TODO Decide whether this method is valid on here or if it should be moved to {@link ElementContainerProvider}.
     * </p>
     * @throws NoSuchElementException When no modal container was present.
     * @see org.fitting.FittingAction#isModalContainerPresent()
     */
    void acceptModalContainer();

    /**
     * Dismiss the modal container.
     * <p>
     * TODO Decide whether this method is valid on here or if it should be moved to {@link ElementContainerProvider}.
     * </p>
     * @throws NoSuchElementException When no modal container was present.
     * @see org.fitting.FittingAction#isModalContainerPresent()
     */
    void dismissModalContainer();

    /**
     * Get the modal container.
     * <p>
     * TODO Decide whether this method is valid on here or if it should be moved to {@link ElementContainerProvider}.
     * </p>
     * @return The modal container.
     * @throws NoSuchElementException When no modal container was present.
     * @see org.fitting.FittingAction#isModalContainerPresent()
     */
    ElementContainer getModalContainer();

    /**
     * Check if an element allows for value selection and has a certain selectable value.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @param value         The value to look for.
     * @return <code>true</code> if the element has the selectable value.
     * @throws NoSuchElementException When no matching element was found.
     */
    boolean isElementValueSelectable(SearchContext searchContext, Selector selector, String value) throws NoSuchElementException;

    /**
     * Select a value on an element.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @param value         The value to look select.
     * @throws NoSuchElementException When no matching element was found.
     */
    void selectElementValue(SearchContext searchContext, Selector selector, String value) throws NoSuchElementException;

    /**
     * Select a value on an element.
     * @param searchContext         The search context to find the element on.
     * @param selector              The selector for the element.
     * @param value                 The value to look select.
     * @param noSuchElementCallback Callback to execute when no matching element was found.
     */
    void selectElementValue(SearchContext searchContext, Selector selector, String value, NoSuchElementCallback noSuchElementCallback);

    /**
     * Check if the given element is a checkbox.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @return <code>true</code> if the element is a checkbox.
     * @throws NoSuchElementException When no matching element was found.
     */
    boolean isElementCheckbox(SearchContext searchContext, Selector selector) throws NoSuchElementException;

    /**
     * Check if a given value for an element has been selected.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @param value         The value to look select.
     * @return <code>true</code> when the given value has been selected.
     * @throws NoSuchElementException When no matching element was found.
     */
    boolean isElementValueSelected(SearchContext searchContext, Selector selector, String value) throws NoSuchElementException;

    /**
     * Check if a given value for an element has been selected.
     * @param searchContext         The search context to find the element on.
     * @param selector              The selector for the element.
     * @param value                 The value to look select.
     * @param noSuchElementCallback Callback to execute when no matching element was found.
     * @return <code>true</code> when the given value has been selected.
     */
    boolean isElementValueSelected(SearchContext searchContext, Selector selector, String value, NoSuchElementCallback noSuchElementCallback);

    /**
     * Check if the value of an element can be set/changed.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @return <code>true</code> if the element value can be set/changed.
     * @throws NoSuchElementException When no matching element was found.
     */
    boolean isElementValueSettable(SearchContext searchContext, Selector selector) throws NoSuchElementException;

    /**
     * Set the value for an element.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @param value         The value to set.
     * @throws NoSuchElementException When no matching element was found.
     */
    void setValueForElement(SearchContext searchContext, Selector selector, String value) throws NoSuchElementException;

    /**
     * Check if an element is present on the search context.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @return <code>true</code> if the element is present.
     */
    boolean isElementPresent(SearchContext searchContext, Selector selector);

    /**
     * Check if an element is displayed (visible) on the search context.
     * @param searchContext The search context to find the element on.
     * @param selector      The selector for the element.
     * @return <code>true</code> if the element is displayed.
     */
    boolean isElementDisplayed(SearchContext searchContext, Selector selector);
}
