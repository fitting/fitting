package org.fitting;

/**
 * Provider for providing a {@link SearchContext} for the {@link SearchContextProviders}.
 *
 * @see SearchContextProviders
 */
public interface SearchContextProvider {
    /**
     * Get the id of the search context.
     *
     * @return The id.
     */
    String getId();

    /**
     * Get the search context.
     *
     * @return The search context.
     */
    SearchContext getSearchContext();
}
