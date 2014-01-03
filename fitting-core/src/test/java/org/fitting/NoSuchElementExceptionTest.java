package org.fitting;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class NoSuchElementExceptionTest {

    private static final String SEARCH_CONTEXT_TO_STRING = "SEARCH_CONTEXT_TO_STRING";
    private static final String BY_TO_STRING = "BY_TO_STRING";

    @Test
    public void testException() {
        SearchContext searchContext = new SearchContext() {
            @Override
            public List<Element> findElementsBy(final By byClause) throws FittingException {
                return null;
            }
            @Override
            public Element findElementBy(final By byClause) throws NoSuchElementException, FittingException {
                return null;
            }
            @Override
            public String toString() {
                return SEARCH_CONTEXT_TO_STRING;
            }
        };
        By by = new By() {
            @Override
            public Element findElement(final SearchContext context) {
                return null;
            }
            @Override
            public List<Element> findElements(final SearchContext context) {
                return null;
            }
            @Override
            public String toString() {
                return BY_TO_STRING;
            };
        };
        NoSuchElementException fittingException = new NoSuchElementException(searchContext, by);
        Assert.assertEquals("message:<<FITTING_ERROR No element found on search context SEARCH_CONTEXT_TO_STRING matching by-clause BY_TO_STRING>>", fittingException.getMessage());
    }

}
