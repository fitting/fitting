/*
 * Licensed to the Fitting Project under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Fitting Project licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.fitting.fixture;

import org.fitting.By;
import org.fitting.ByProvider;
import org.fitting.ElementContainer;
import org.fitting.FittingAction;
import org.fitting.FittingConnector;
import org.fitting.FittingContainer;
import org.fitting.FittingException;
import org.fitting.SearchContext;
import org.fitting.SearchContextProvider;
import org.fitting.SearchContextProviders;

/**
 * Base fixture for all fitting fixtures, allowing access to the managed resources.
 */
public abstract class FittingFixture {

    /**
     * The fixed id for the default search context provider.
     */
    public static final String DEFAULT_SEARCH_CONTEXT_PROVIDER_ID = "DefaultSearchContextProvider";

    /**
     * The search context providers to use.
     */
    private final ThreadLocal<SearchContextProviders> providers = new ThreadLocal<SearchContextProviders>();

    /**
     * Create a new fitting fixture, using the default search context from the connector.
     */
    public FittingFixture() {
        // Create an anonymous inner class provider with the default search context and the default id.
        this(new SearchContextProvider() {
            @Override
            public SearchContext getSearchContext() {
                return FittingContainer.get().getDefaultSearchContext();
            }
            @Override
            public String getId() {
                return DEFAULT_SEARCH_CONTEXT_PROVIDER_ID;
            }
        });
    }

    /**
     * Create a new fitting fixture, using a custom search context provider for providing the search context.
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
    protected final By getByClause(final String byTag, final String query) throws FittingException {
        ByProvider provider = getConnector().getByProvider();
        return provider.getBy(byTag, query);
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
     * Get the default search context.
     *
     * @return The search context.
     */
    protected final SearchContext getSearchContext() {
        return getSearchContext(DEFAULT_SEARCH_CONTEXT_PROVIDER_ID);
    }

    /**
     * Get the search context provider by its id.
     *
     * @param id The id of the search context.
     *
     * @return The search context provider  or null if there is no search context provider for the given id.
     */
    protected final SearchContextProvider getSearchContextProvider(final String id) {
        return providers.get().getSearchContextProvider(id);
    }

    /**
     * Get the search context by its search context provider id.
     *
     * @param providerId The id of the search context provider.
     *
     * @return The search context or null if there is no search context provider for the given id.
     */
    protected final SearchContext getSearchContext(final String providerId) {
        SearchContext context = null;
        final SearchContextProvider provider = this.getSearchContextProvider(providerId);
        if (provider != null) {
            context = provider.getSearchContext();
        }
        return context;
    }
}
