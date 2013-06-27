package org.fitting;

import java.util.List;

/**
 * By-clause for selecting elements.
 */
public interface By {
    /**
     * Find all elements on the given search context using the By-clause.
     *
     * @param context The search context to search on.
     *
     * @return The found elements.
     */
    List<Element> findElements(SearchContext context);

    /**
     * Find a single element on the given search context using the By-clause.
     *
     * @param context The search context to search on.
     *
     * @return The element.
     */
    Element findElement(SearchContext context);
}
