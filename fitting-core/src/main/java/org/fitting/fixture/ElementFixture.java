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
     * <pre>| text value for element with | [byTag] | [identifier] |</pre>
     * </p>
     *
     * @param byTag      The tag of the by-clause type.
     * @param identifier The identifier/query for the By-clause.
     *
     * @return The value of the element.
     *
     * @throws FittingException If the by-clause or element could not be found.
     */
    @FixtureMethod(description = "Select an element and get the text-value.", signature = {"text value for element with", "byTag", "identifier"})
    public String elementWithTextValue(@FixtureParameter(name = "byTag", description = "The tag of the by-clause.") String byTag,
            @FixtureParameter(name = "identifier", description = "The identifier for the by-clause.") String identifier) throws FittingException {
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
    public boolean textForElementWithIs(String byTag, String identifier, String text) throws FittingException {
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
    public boolean elementWithExists(String byTag, String identifier) throws FittingException {
        By byClause = getByClause(byTag, identifier);
        return getFittingAction().isElementPresent(getSearchContext(), byClause);
    }

    /**
     * Wait for an element to be present on the page within the given time-out.
     *
     * @param byTag      The tag of the by-clause type.
     * @param identifier The identifier/query for the By-clause.
     * @param seconds    The timeout in seconds.
     *
     * @throws FittingException When the by-clause could not be found or the element wasn't found within the time-out.
     */
    public void waitSecondsForElementWith(String byTag, String identifier, int seconds) throws FittingException {
        By byClause = getByClause(byTag, identifier);
        getFittingAction().waitForElement(getSearchContext(), byClause, seconds);
    }
}
