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

package org.fitting.fixture;

import java.util.ArrayList;
import java.util.List;

import org.fitting.Element;
import org.fitting.FittingException;
import org.fitting.FormattedFittingException;
import org.fitting.NoSuchElementException;

import static java.lang.String.format;

/** Fixture for basic interaction with UI elements and querying of elements. */
public class ElementFixture extends FittingFixture {
    /**
     * Get the text value of an element.
     * <p>
     * FitNesse usage:
     * <pre>| get text value for element with | [selector] | being | [identifier] |</pre>
     * </p>
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @return The value of the element.
     * @throws FittingException When the selector or element could not be found.
     */
    public String textForElementWithBeing(final String selector, final String identifier) throws FittingException {
        return getElement(selector, identifier).getValue();
    }

    /**
     * Check if the text for a given element is the same as the provided text.
     * <p>
     * FitNesse usage:
     * <pre>| text for element with | [selector] | being | [identifier] | is | [text] |</pre>
     * </p>
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @param text The text to compare with.
     * @return <code>true</code> if the text is the same.
     * @throws FittingException When the selector or element could not be found.
     */
    public boolean textForElementWithBeingIs(final String selector, final String identifier, final String text) throws FittingException {
        if (text == null) {
            throw new FormattedFittingException("Null text provided for comparison");
        }
        final String elementText = getElement(selector, identifier).getValue();
        return text.equalsIgnoreCase(elementText);
    }

    /**
     * Check if the text for a given element contains the provided text.
     * <p>
     * FitNesse usage:
     * <pre>| text for element with | [selector] | being | [identifier] | contains | [text] |</pre>
     * </p>
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @param text The text to search for.
     * @return <code>true</code> if the text is the same.
     * @throws FittingException When the selector or element could not be found.
     */
    public boolean textForElementWithBeingContains(final String selector, final String identifier, final String text) throws FittingException {
        if (text == null) {
            throw new FormattedFittingException("Null text provided for comparison");
        }
        final String elementText = getElement(selector, identifier).getValue();
        return elementText.contains(text);
    }

    /**
     * Check if an element exists.
     * <p>
     * FitNesse usage:
     * <pre>| element with | [selector] | being | [identifier] | exists |</pre>
     * </p>
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @return <code>true</code> if an element exists matching the selector and identifier.
     * @throws FittingException When the selector could not be found.
     */
    public boolean elementWithBeingExists(final String selector, final String identifier) throws FittingException {
        boolean present;
        try {
            getElement(selector, identifier);
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }
        return present;
    }

    /**
     * Get the number of times an element matching the criteria exists.
     * <p>
     * FitNesse usage:
     * <pre>| number of elements with | [selector] | being | [identifier] | is |</pre>
     * </p>
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @return The times the elements matching the criteria exist.
     * @throws FittingException When the selector could not be found.
     */
    public int numberOfElementsWithBeingIs(final String selector, final String identifier) throws FittingException {
        return getElements(selector, identifier).size();
    }

    /**
     * Click an element.
     * <p>
     * FitNesse usage:
     * <pre>| click element with | [selector] | being | [identifier] |</pre>
     * </p>
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @throws FittingException If no matching element could not be found.
     */
    public void clickElementWithBeing(final String selector, final String identifier) throws FittingException {
        getElement(selector, identifier).click();
    }

    /**
     * Send a series of keys to an element.
     * <p>
     * FitNesse usage:
     * <pre>| send keys | [keys] | to element with | [selector] | being | [identifier] |</pre>
     * </p>
     * @param keys The keys to send.
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @throws FittingException If no matching element could not be found.
     */
    public void sendKeysToElementWithBeing(final CharSequence keys, final String selector, final String identifier) throws FittingException {
        getElement(selector, identifier).sendKeys(keys);
    }

    /**
     * Set the value for an element.
     * <p>
     * FitNesse usage:
     * <pre>| set value | [value] | for element with | [selector] | being | [identifier] |</pre>
     * </p>
     * @param value The value to set.
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @throws FittingException When the selector or element could not be found or no value could be set.
     */
    public void setValueForElementWithBeing(final String value, final String selector, final String identifier) throws FittingException {
        getElement(selector, identifier).setValue(value);
    }

    /**
     * Set the value for an element by it's displayed text.
     * <p>
     * FitNesse usage:
     * <pre>| set value with text | [text] | for element with | [selector] | being | [identifier] |</pre>
     * </p>
     * @param text The display text of the value to set.
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @throws FittingException When the selector or element could not be found or no value was found with the given text.
     */
    public void setValueWithTextForElementWithBeing(final String text, final String selector, final String identifier) throws FittingException {
        getElement(selector, identifier).setValueWithText(text);
    }

    /**
     * Wait for an element to be present on the page within the given time-out before failing with an exception.
     * <p>
     * FitNesse usage:
     * <pre>| wait | [seconds] | for element with | [selector] | being | [identifier] |</pre>
     * </p>
     * @param seconds The timeout in seconds.
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @throws FittingException When the selector or element could not be found or the time-out was reached.
     */
    public void waitSecondsForElementWithBeing(final int seconds, final String selector, final String identifier) throws FittingException {
        getSearchContext().waitForElement(getSelector(selector, identifier), seconds);
    }

    /**
     * Check if an element is displayed, e.g. existing and being visible.
     * <p>
     * FitNesse usage:
     * <pre>| element with | [selector] | being | [identifier] | is displayed |</pre>
     * </p>
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @return <code>true</code> if an element matching the selector and query exists and is visible.
     * @throws FittingException When the selector or element could not be found.
     */
    public boolean elementWithBeingIsDisplayed(final String selector, final String identifier) throws FittingException {
        return getElement(selector, identifier).isDisplayed();
    }

    /**
     * Check if the value of an element can be set.
     * <p>
     * FitNesse usage:
     * <pre>| value of element with | [selector] | being | [identifier] | is settable |</pre>
     * </p>
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @return <code>true</code> if the value can be set.
     * @throws FittingException When the selector or element could not be found.
     */
    public boolean valueOfElementWithBeingIsSettable(final String selector, final String identifier) throws FittingException {
        final Element element = getSearchContext().findElementBy(getSelector(selector, identifier));
        return element.isInput();
    }

    /**
     * Get the value of an element.
     * <p>
     * FitNesse usage:
     * <pre>| value of element with | [selector] | being | [identifier] |</pre>
     * </p>
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @return The value.
     * @throws FittingException When the selector or element could not be found.
     */
    public String valueOfElementWithBeing(final String selector, final String identifier) throws FittingException {
        final Element element = getSearchContext().findElementBy(getSelector(selector, identifier));
        return element.getValue();
    }

    /**
     * Get the value of an attribute on an element.
     * <p>
     * FitNesse usage:
     * <pre>| attribute value of | [attributeName] | for element with | [selector] | being | [identifier] |</pre>
     * </p>
     * @param attributeName The name of the attribute.
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @return The value of the attribute.
     * @throws FittingException When the selector, element or attribute could not be found.
     */
    public String attributeValueOfForElementWithBeing(final String attributeName, final String selector, final String identifier) throws FittingException {
        return getAttributeValue(selector, identifier, attributeName);
    }

    /**
     * Check if the value of an attribute on an element contains a provided text.
     * <p>
     * FitNesse usage:
     * <pre>| attribute value of | [attributeName] | for element with | [selector] | being | [identifier] | contains | [value] |</pre>
     * </p>
     * @param attributeName The name of the attribute.
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @param value The value to check the attribute value for.
     * @return <code>true</code> if the attribute value contains the provided text.
     * @throws FittingException When the selector, element or attribute could not be found.
     */
    public boolean attributeValueOfForElementWithBeingContains(final String attributeName, final String selector, final String identifier, final String value) throws FittingException {
        final String attributeValue = getAttributeValue(selector, identifier, attributeName);
        return attributeValue.contains(value);
    }

    /**
     * Get the element from the base search context.
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @return The element.
     * @throws NoSuchElementException When no matching element was found.
     */
    private Element getElement(final String selector, final String identifier) throws NoSuchElementException {
        return getSearchContext().findElementBy(getSelector(selector, identifier));
    }

    /**
     * Get all the elements that match the given criteria from the base search context.
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @return The elements.
     * @throws FittingException When no matching selector could be found.
     */
    private List<Element> getElements(final String selector, final String identifier) throws FittingException {
        final List<Element> elements = new ArrayList<Element>();
        final List<Element> foundElements = getSearchContext().findElementsBy(getSelector(selector, identifier));
        if (foundElements != null) {
            elements.addAll(foundElements);
        }
        return elements;
    }

    /**
     * Get the value of an attribute on an element.
     * @param selector The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @param attributeName The name of the attribute.
     * @return The value of the attribute.
     * @throws NoSuchElementException When no matching element was found.
     * @throws FormattedFittingException When no attribute was found with the given name.
     */
    private String getAttributeValue(final String selector, final String identifier, final String attributeName) throws NoSuchElementException, FormattedFittingException {
        final String attributeValue = getElement(selector, identifier).getAttributeValue(attributeName);
        if (attributeValue == null) {
            throw new FormattedFittingException(format("No attribute found with name %s on element %s[%s]", attributeName, selector, identifier));
        }
        return attributeValue;
    }
}
