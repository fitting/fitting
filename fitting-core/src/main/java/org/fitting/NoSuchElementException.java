package org.fitting;

import static java.lang.String.format;

public class NoSuchElementException extends FormattedFittingException {
    private static final String NO_ELEMENT_MESSAGE = "No element found on search context %s matching by-clause %s";

    public NoSuchElementException(SearchContext searchContext, By byClause) {
        super(format(NO_ELEMENT_MESSAGE, searchContext.toString(), byClause.toString()));
    }
}
