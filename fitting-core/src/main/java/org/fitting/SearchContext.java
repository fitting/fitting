package org.fitting;

import java.util.List;

/**
 * Search context for finding elements on.
 */
public interface SearchContext {
    /**
     * Find all elements on the context matching the given By-clause.
     *
     * @param byClause The By-clause.
     *
     * @return The matching elements.
     *
     * @throws FittingException When the query failed to execute.
     */
    List<Element> findElementsBy(By byClause) throws FittingException;

    /**
     * Find a single element on the context matching the given By-clause.
     *
     * @param byClause The By-clause.
     *
     * @return The matching element.
     *
     * @throws NoSuchElementException When the element could not be found.
     * @throws FittingException       When the query failed to execute.
     */
    Element findElementBy(By byClause) throws NoSuchElementException, FittingException;
}
