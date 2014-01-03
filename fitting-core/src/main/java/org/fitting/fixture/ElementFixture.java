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

import org.fitting.By;
import org.fitting.FittingException;
import org.fitting.FormattedFittingException;
import org.fitting.documentation.Fixture;
import org.fitting.documentation.FixtureMethod;
import org.fitting.documentation.FixtureParameter;

/**
 * Fixture for basic querying and interaction with elements.
 */
@Fixture(description = "Fixture for basic querying and interaction with elements.")
public class ElementFixture extends FittingFixture {

    /**
     * Get the text value of an element.
     *
     * <p>
     * FitNesse usage:
     * <pre>| get text value for element with | [byTag] | [identifier] |</pre>
     * </p>
     *
     * @param byTag      The tag of the by-clause type.
     * @param identifier The identifier/query for the By-clause.
     *
     * @return The value of the element.
     *
     * @throws FittingException If the by-clause or element could not be found.
     */
    @FixtureMethod(description = "Get the text-value for an element.", signature = {"get text value for element with", "byTag", "identifier"})
    public String getTextValueForElementWith(@FixtureParameter(name = "byTag", description = "The tag of the by-clause.") final String byTag,
            @FixtureParameter(name = "identifier", description = "The identifier for the by-clause.") final String identifier) throws FittingException {
        By byClause = getByClause(byTag, identifier);
        return getFittingAction().getTextValue(getSearchContext(), byClause);
    }

    /**
     * Check if the text for a given element is the same as the provided text.
     *
     * <p>
     * FitNesse usage:
     * <pre>| text for element with | [byTag] | [identifier] | is | [text] |</pre>
     * </p>
     *
     * @param byTag      The tag of the by-clause type.
     * @param identifier The identifier/query for the By-clause.
     * @param text       The text to compare with.
     *
     * @return <code>true</code> if the text is the same.
     *
     * @throws FittingException If the by-clause or element could not be found.
     */
    @FixtureMethod(description = "Compare the text-value of an element against the provided value.",
            signature = {"text value for element with", "byTag", "identifier", "is", "text"})
    public boolean textForElementWithIs(@FixtureParameter(name = "byTag", description = "The tag of the by-clause.") final String byTag,
            @FixtureParameter(name = "identifier", description = "The identifier for the by-clause.") final String identifier,
            @FixtureParameter(name = "text", description = "The text to compare with.") final String text) throws FittingException {
        if (text == null) {
            throw new FormattedFittingException("Null text provided found for comparison");
        }
        By byClause = getByClause(byTag, identifier);
        String elementText = getFittingAction().getTextValue(getSearchContext(), byClause);
        return text.equalsIgnoreCase(elementText);
    }

    /**
     * Check if an element exists.
     *
     * <p>
     * FitNesse usage:
     * <pre>| element with | [byTag] | [identifier] | exists |</pre>
     * </p>
     *
     * @param byTag      The tag of the by-clause type.
     * @param identifier The identifier/query for the By-clause.
     *
     * @return <code>true</code> if the text is the same.
     *
     * @throws FittingException If the by-clause could not be found.
     */
    public boolean elementWithExists(final String byTag, final String identifier) throws FittingException {
        By byClause = getByClause(byTag, identifier);
        return getFittingAction().isElementPresent(getSearchContext(), byClause);
    }

    /**
     * Wait for an element to be present on the page within the given time-out.
     *
     * @param seconds    The timeout in seconds.
     * @param byTag      The tag of the by-clause type.
     * @param identifier The identifier/query for the By-clause.
     *
     * @throws FittingException When the by-clause could not be found or the element wasn't found within the time-out.
     */
    public void waitSecondsForElementWith(final int seconds, final String byTag, final String identifier) throws FittingException {
        By byClause = getByClause(byTag, identifier);
        getFittingAction().waitForElement(getSearchContext(), byClause, seconds);
    }
}
