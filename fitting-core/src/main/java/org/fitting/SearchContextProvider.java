package org.fitting;

import org.openqa.selenium.SearchContext;

/** Provider for creation / retrieval of the base search context for the selenium fixture. */
public interface SearchContextProvider {
    /**
     * Get the unique id of the search context provider.
     * @return The id.
     */
    String getId();

    /**
     * Create the search context to use as base location for searches.
     * @return The search context.
     */
    SearchContext getSearchContext();
}
