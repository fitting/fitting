package org.fitting;

/**
 * SearchContextProvider that uses {@link By}-based searches to locate elements.
 * <p></p>
 * The By-clause can be updated so that the search context of the implementing provider can be changed at runtime.
 * </p>
 */
public abstract class UpdatableSearchContextProvider implements SearchContextProvider {
    /**
     * The By-clause.
     */
    private By by;

    /**
     * Constructor.
     *
     * @param by The by mechanism used to locate elements.
     */
    public UpdatableSearchContextProvider(final By by) {
        this.by = by;
    }

    /**
     * Update the By-clause.
     *
     * @param by The By-clause used to locate elements.
     */
    public void updateBy(final By by) {
        this.by = by;
    }

    @Override
    public SearchContext getSearchContext() {
        // TODO Implement me!
        return null;
    }
}
