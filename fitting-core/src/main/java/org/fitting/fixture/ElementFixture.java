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

import org.fitting.*;

import static java.lang.String.format;

/**
 * Fixture for basic interaction with UI elements and querying of elements.
 */
public class ElementFixture extends FittingFixture {
    /**
     * Get the text value of an element.
     * <p>
     * FitNesse usage:
     * <pre>| get text value for element with | [selector] | being | [identifier] |</pre>
     * </p>
     *
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     *
     * @return The value of the element.
     *
     * @throws FittingException When the selector or element could not be found.
     */
    public String textForElementWithBeing(String selector, String identifier) throws FittingException {
        return getElement(selector, identifier).getValue();
    }

    /**
     * Check if the text for a given element is the same as the provided text.
     * <p>
     * FitNesse usage:
     * <pre>| text for element with | [selector] | being | [identifier] | is | [text] |</pre>
     * </p>
     *
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @param text       The text to compare with.
     *
     * @return <code>true</code> if the text is the same.
     *
     * @throws FittingException When the selector or element could not be found.
     */
    public boolean textForElementWithBeingIs(String selector, String identifier, String text) throws FittingException {
        if (text == null) {
            throw new FormattedFittingException("Null text provided for comparison");
        }
        String elementText = getElement(selector, identifier).getValue();
        return text.equalsIgnoreCase(elementText);
    }

    /**
     * Check if the text for a given element contains the provided text.
     * <p>
     * FitNesse usage:
     * <pre>| text for element with | [selector] | being | [identifier] | contains | [text] |</pre>
     * </p>
     *
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @param text       The text to search for.
     *
     * @return <code>true</code> if the text is the same.
     *
     * @throws FittingException When the selector or element could not be found.
     */
    public boolean textForElementWithBeingContains(String selector, String identifier, String text) throws FittingException {
        if (text == null) {
            throw new FormattedFittingException("Null text provided for comparison");
        }
        String elementText = getElement(selector, identifier).getValue();
        return elementText.contains(text);
    }

    /**
     * Check if an element exists.
     * <p>
     * FitNesse usage:
     * <pre>| element with | [selector] | being | [identifier] | exists |</pre>
     * </p>
     *
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     *
     * @return <code>true</code> if an element exists matching the selector and identifier.
     *
     * @throws FittingException When the selector could not be found.
     */
    public boolean elementWithBeingExists(String selector, String identifier) throws FittingException {
        boolean present;
        try {
            Element element = getElement(selector, identifier);
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }
        return present;
    }

    /**
     * Click an element.
     * <p>
     * FitNesse usage:
     * <pre>| click element with | [selector] | being | [identifier] |</pre>
     * </p>
     *
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     *
     * @throws FittingException If no matching element could not be found.
     */
    public void clickElementWithBeing(String selector, String identifier) throws FittingException {
        getElement(selector, identifier).click();
    }

    /**
     * Set the value for an element.
     * <p>
     * FitNesse usage:
     * <pre>| set value | [value] | for element with | [selector] | being | [identifier] |</pre>
     * </p>
     *
     * @param value      The value to set.
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     *
     * @throws FittingException When the selector or element could not be found.
     */
    public void setValueForElementWithBeing(String value, String selector, String identifier) throws FittingException {
        if (value == null || value.length() < 1) {
            throw new FormattedFittingException("Unable to send an empty String to element ");
        }
        getElement(selector, identifier).sendKeys(value);
    }

    /**
     * Wait for an element to be present on the page within the given time-out before failing with an exception.
     *
     * <p>
     * FitNesse usage:
     * <pre>| wait | [seconds] | for element with | [selector] | being | [identifier] |</pre>
     * </p>
     *
     * @param seconds    The timeout in seconds.
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     *
     * @throws FittingException When the selector or element could not be found or the time-out was reached.
     */
    public void waitSecondsForElementWithBeing(int seconds, String selector, String identifier) throws FittingException {
        Selector select = getSelector(selector, identifier);
        getFittingAction().waitForElement(getSearchContext(), select, seconds);
    }

    /**
     * Check if an element is displayed, e.g. existing and being visible.
     *
     * <p>
     * FitNesse usage:
     * <pre>| element with | [selector] | being | [identifier] | is displayed |</pre>
     * </p>
     *
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     *
     * @return <code>true</code> if an element matching the selector and query exists and is visible.
     *
     * @throws FittingException When the selector or element could not be found.
     */
    public boolean elementWithBeingIsDisplayed(String selector, String identifier) throws FittingException {
        return getElement(selector, identifier).isDisplayed();
    }

    /**
     * Check if the value of an element can be set.
     *
     * <p>
     * FitNesse usage:
     * <pre>| value of element with | [selector] | being | [identifier] | is settable |</pre>
     * </p>
     *
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     *
     * @return <code>true</code> if the value can be set.
     *
     * @throws FittingException When the selector or element could not be found.
     */
    public boolean valueOfElementWithBeingIsSettable(String selector, String identifier) throws FittingException {
        Selector select = getSelector(selector, identifier);
        return !getFittingAction().isElementValueSettable(getSearchContext(), select);
    }

    /**
     * Get the value of an attribute on an element.
     *
     * <p>
     * FitNesse usage:
     * <pre>| attribute value of | [attributeName] | for element with | [selector] | being | [identifier] |</pre>
     * </p>
     *
     * @param attributeName The name of the attribute.
     * @param selector      The name of the selector.
     * @param identifier    The identifier/query for the selector.
     *
     * @return The value of the attribute.
     *
     * @throws FittingException When the selector, element or attribute could not be found.
     */
    public String attributeValueOfForElementWithBeing(String attributeName, String selector, String identifier) throws FittingException {
        return getAttributeValue(selector, identifier, attributeName);
    }

    /**
     * Check if the value of an attribute on an element contains a provided text.
     *
     * <p>
     * FitNesse usage:
     * <pre>| attribute value of | [attributeName] | for element with | [selector] | being | [identifier] | contains | [value] |</pre>
     * </p>
     *
     * @param attributeName The name of the attribute.
     * @param selector      The name of the selector.
     * @param identifier    The identifier/query for the selector.
     * @param value         The value to check the attribute value for.
     *
     * @return <code>true</code> if the attribute value contains the provided text.
     *
     * @throws FittingException When the selector, element or attribute could not be found.
     */
    public boolean attributeValueOfForElementWithBeingContains(String attributeName, String selector, String identifier, String value) throws FittingException {
        String attributeValue = getAttributeValue(selector, identifier, attributeName);
        return attributeValue.contains(value);
    }

    /**
     * Get the element from the base search context.
     *
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     *
     * @return The element.
     *
     * @throws NoSuchElementException When no matching element was found.
     */
    private Element getElement(String selector, String identifier) throws NoSuchElementException {
        return getSearchContext().findElementBy(getSelector(selector, identifier));
    }

    /**
     * Get the value of an attribute on an element.
     *
     * @param selector      The name of the selector.
     * @param identifier    The identifier/query for the selector.
     * @param attributeName The name of the attribute.
     *
     * @return The value of the attribute.
     *
     * @throws NoSuchElementException    When no matching element was found.
     * @throws FormattedFittingException When no attribute was found with the given name.
     */
    private String getAttributeValue(String selector, String identifier, String attributeName) throws NoSuchElementException, FormattedFittingException {
        String attributeValue = getElement(selector, identifier).getAttributeValue(attributeName);
        if (attributeValue == null) {
            throw new FormattedFittingException(format("No attribute found with name %s on element %s[%s]", attributeName, selector, identifier));
        }
        return attributeValue;
    }
}
