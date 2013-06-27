package org.fitting;

import static java.lang.String.format;

/**
 * Exception for when an element could not been found.
 */
public class NoSuchElementException extends FormattedFittingException {
    /**
     * The default message.
     */
    private static final String NO_ELEMENT_MESSAGE = "No element found on search context %s matching by-clause %s";

    /**
     * Create a new NoSuchElementException.
     *
     * @param searchContext The search context where the element was searched on.
     * @param byClause      The By-clause used.
     */
    public NoSuchElementException(SearchContext searchContext, By byClause) {
        super(format(NO_ELEMENT_MESSAGE, searchContext.toString(), byClause.toString()));
    }
}
