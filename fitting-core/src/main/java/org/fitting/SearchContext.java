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
     */
    List<Element> findElementsBy(By byClause);

    /**
     * Find a single element on the context matching the given By-clause.
     *
     * @param byClause The By-clause.
     *
     * @return The matching element.
     */
    Element findElementBy(By byClause);
}
