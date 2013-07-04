package org.fitting;

/**
 * Provider for creating {@link By}-clause instances.
 */
public interface ByProvider {
    /**
     * Get the By-clause with a given tag.
     *
     * @param byTag The tag fo the clause.
     * @param query The query for the By-clause.
     *
     * @return The By-clause.
     *
     * @throws FittingException When no matching By-clause could be found.
     */
    By getBy(String byTag, String query) throws FittingException;

    /**
     * Get the By-clause tags available via the provider.
     *
     * @return The available tags.
     */
    String[] getAvailableTags();
}
