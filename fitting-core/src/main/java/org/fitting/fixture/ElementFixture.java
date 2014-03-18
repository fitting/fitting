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

import org.fitting.Element;
import org.fitting.FittingException;
import org.fitting.FormattedFittingException;
import org.fitting.Selector;
import org.fitting.documentation.Fixture;
import org.fitting.documentation.FixtureMethod;
import org.fitting.documentation.FixtureParameter;

/** Fixture for basic querying and interaction with elements. */
@Fixture(description = "Fixture for basic querying and interaction with elements.")
public class ElementFixture extends FittingFixture {
    /**
     * Get the text value of an element.
     * <p>
     * FitNesse usage:
     * <pre>| get text value for element with | [selector] | value | [identifier] |</pre>
     * </p>
     *
     * @param selector   The tag of the selector type.
     * @param identifier The identifier/query for the selector.
     *
     * @return The value of the element.
     *
     * @throws org.fitting.FittingException If the selector or element could not be found.
     */
    @FixtureMethod(description = "Get the text-value for an element.", signature = {"get text value for element with", "selector", "identifier"})
    public String textForElementWithValue(@FixtureParameter(name = "selector", description = "The tag of the selector.") String selector,
                                          @FixtureParameter(name = "identifier", description = "The identifier for the selector.") String identifier) throws FittingException {
        final Selector select = getSelector(selector, identifier);
        return getFittingAction().getTextValue(getSearchContext(), select);
    }

    /**
     * Check if the text for a given element is the same as the provided text.
     * <p>
     * FitNesse usage:
     * <pre>| text for element with | [selector] | [identifier] | is | [text] |</pre>
     * </p>
     *
     * @param selector   The tag of the selector type.
     * @param identifier The identifier/query for the selector.
     * @param text       The text to compare with.
     *
     * @return <code>true</code> if the text is the same.
     *
     * @throws org.fitting.FittingException If the selector or element could not be found.
     */
    @FixtureMethod(description = "Compare the text-value of an element against the provided value.",
            signature = {"text value for element with", "selector", "identifier", "is", "text"})
    public boolean textForElementWithIsIs(@FixtureParameter(name = "selector", description = "The tag of the selector.") String selector,
                                          @FixtureParameter(name = "identifier", description = "The identifier for the selector.") String identifier,
                                          @FixtureParameter(name = "text", description = "The text to compare with.") String text) throws FittingException {
        if (text == null) {
            throw new FormattedFittingException("Null text provided found for comparison");
        }
        Selector select = getSelector(selector, identifier);
        String elementText = getFittingAction().getTextValue(getSearchContext(), select);
        return text.equalsIgnoreCase(elementText);
    }

    @FixtureMethod(description = "Compare the text-value of an element against the provided value.",
            signature = {"text for element with", "selector", "and value", "identifier", "is", "text"})
    public boolean textForElementWithAndValueIs(@FixtureParameter(name = "selector", description = "The tag of the selector.") final String selector,
                                                @FixtureParameter(name = "identifier", description = "The identifier for the selector.") final String identifier,
                                                @FixtureParameter(name = "text", description = "The text to compare with.") final String text) throws FittingException {
        if (text == null) {
            throw new FormattedFittingException("Null text provided found for comparison");
        }
        final Selector select = getSelector(selector, identifier);
        final String elementText = getFittingAction().getTextValue(getSearchContext(), select);
        return text.equalsIgnoreCase(elementText);
    }

    public boolean textForElementWithAndV2alueIs(@FixtureParameter(name = "selector", description = "The tag of the selector.") final String selector,
                                                 @FixtureParameter(name = "identifier", description = "The identifier for the selector.") final String identifier,
                                                 @FixtureParameter(name = "text", description = "The text to compare with.") final String text) throws FittingException {
        if (text == null) {
            throw new FormattedFittingException("Null text provided found for comparison");
        }
        final Selector select = getSelector(selector, identifier);
        final String elementText = getFittingAction().getTextValue(getSearchContext(), select);
        return text.equalsIgnoreCase(elementText);
    }

    /**
     * Check if an element exists.
     * <p>
     * FitNesse usage:
     * <pre>| element with | [selector] | [identifier] | exists |</pre>
     * </p>
     *
     * @param selector   The tag of the selector type.
     * @param identifier The identifier/query for the selector.
     *
     * @return <code>true</code> if the text is the same.
     *
     * @throws org.fitting.FittingException If the selector could not be found.
     */
    public boolean elementWithExists(String selector, String identifier) throws FittingException {
        Selector select = getSelector(selector, identifier);
        return getFittingAction().isElementPresent(getSearchContext(), select);
    }

    public void clickElementWithValue(String selector, String identifier) throws FittingException {
        Selector select = getSelector(selector, identifier);
        Element element = getFittingAction().getElement(getSearchContext(), select);
        element.click();
    }

    public void setValueForElementWithValue(String value, String selector, String identifier) throws FittingException {
        Selector select = getSelector(selector, identifier);
        Element element = getFittingAction().getElement(getSearchContext(), select);
        element.sendKeys(value);
    }


    /**
     * Wait for an element to be present on the page within the given time-out.
     *
     * @param seconds    The timeout in seconds.
     * @param selector   The tag of the selector type.
     * @param identifier The identifier/query for the selector.
     *
     * @throws org.fitting.FittingException When the selector could not be found or the element wasn't found within the time-out.
     */
    public void waitSecondsForElementWithValue(int seconds, String selector, String identifier) throws FittingException {
        Selector select = getSelector(selector, identifier);
        getFittingAction().waitForElement(getSearchContext(), select, seconds);
    }
}
