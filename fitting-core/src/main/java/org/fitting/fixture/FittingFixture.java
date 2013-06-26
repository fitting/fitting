package org.fitting.fixture;

import org.fitting.*;

/**
 * SeleniumFixture.
 */
public abstract class FittingFixture {
    /**
     * The search context providers to use.
     */
    private final ThreadLocal<SearchContextProviders> providers = new ThreadLocal<SearchContextProviders>();

    /**
     * Create a new selenium fixture, using WebDriver (and thus the window root) as search context.
     */
    public FittingFixture() {
        // TODO Add a default SearchContextProvider that uses the default window driver.
        this(null);
    }

    /**
     * Create a new selenium fixture, using a custom search context provider for providing the search context.
     *
     * @param searchContextProviders The SearchContextProviders.
     */
    public FittingFixture(final SearchContextProvider... searchContextProviders) {
        providers.set(new SearchContextProviders(searchContextProviders));
    }

    protected final FittingConnector getConnector() {
        return FittingContainer.get();
    }

    protected final By getByClause(String byTag, String query) {
        ByProvider provider = getConnector().getByProvider();
        return provider.getBy(byTag, query);
    }

    protected final FittingAction getFittingAction() {
        return getConnector().getFittingAction();
    }

    protected final ElementContainer getActiveContainer() {
        return getConnector().getElementContainerProvider().getActiveElementContainer();
    }

    /**
     * Get the search context by its id.
     * <p/>
     * The search context is either a WebElement or the WebDriver.
     *
     * @return The search context or null if there is no search context for the given id.
     */
    protected final SearchContext getSearchContext() {
        // TODO Implement a default (public static final) ID for the default search context provider.
        return getSearchContext(null);
    }

    /**
     * Get the search context provider by its id.
     * <p/>
     *
     * @param id The id of the search context.
     *
     * @return The search context provider  or null if there is no search context provider for the given id.
     */
    protected final SearchContextProvider getSearchContextProvider(final String id) {
        return providers.get().getSearchContextProvider(id);
    }

    /**
     * Get the search context by its id.
     * <p/>
     * The search context is either a WebElement or the WebDriver.
     *
     * @param id The id of the search context.
     *
     * @return The search context or null if there is no search context for the given id.
     */
    protected final SearchContext getSearchContext(final String id) {
        SearchContext context = null;
        if (providers.get().isSearchContextProviderKnown(id)) {
            final SearchContextProvider provider = providers.get().getSearchContextProvider(id);
            context = provider.getSearchContext();
        }
        return context;
    }
}
