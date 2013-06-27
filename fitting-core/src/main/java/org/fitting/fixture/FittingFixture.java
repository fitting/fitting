package org.fitting.fixture;

import org.fitting.*;

import static java.lang.String.format;

/**
 * Base fixture for all fitting fixtures, allowing access to the managed resources.
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

    /**
     * Get the currently active {@link FittingConnector}.
     *
     * @return The {@link FittingConnector} instance.
     */
    protected final FittingConnector getConnector() {
        return FittingContainer.get();
    }

    /**
     * Create a {@link By}-clause based on the tag and query.
     *
     * @param byTag The tag of the By-clause.
     * @param query The query.
     *
     * @return The {@link By} instance.
     *
     * @throws FittingAction When the By-clause could not be created.
     */
    protected final By getByClause(String byTag, String query) throws FittingException {
        ByProvider provider = getConnector().getByProvider();
        By byClause = provider.getBy(byTag, query);
        if (byClause == null) {
            throw new FormattedFittingException(format("No By-clause was available for tag [%s] with query [%s].", byTag, query));
        }
        return byClause;
    }

    /**
     * Get the currently active {@link FittingAction} implementation.
     *
     * @return The {@link FittingAction}.
     */
    protected final FittingAction getFittingAction() {
        return getConnector().getFittingAction();
    }

    /**
     * Get the currently active {@link ElementContainer}.
     *
     * @return The active {@link ElementContainer}.
     */
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
