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

import org.fitting.*;

import static java.lang.String.format;

/**
 * Base fixture for all fitting fixtures, allowing access to the managed resources.
 */
public abstract class FittingFixture {
    /** The search context providers to use. */
    private final ThreadLocal<SearchContextProviders> providers = new ThreadLocal<SearchContextProviders>();

    public FittingFixture() {
    }

    /**
     * Create a fixture, using a custom search context provider for providing the search context.
     * @param searchContextProviders The SearchContextProviders.
     */
    public FittingFixture(final SearchContextProvider... searchContextProviders) {
        providers.set(new SearchContextProviders(searchContextProviders));
    }

    /**
     * Get the currently active {@link org.fitting.FittingConnector}.
     * @return The {@link org.fitting.FittingConnector} instance.
     */
    protected final FittingConnector getConnector() {
        return FittingContainer.get();
    }

    /**
     * Create a {@link org.fitting.Selector}-clause based on the tag and query.
     * @param selector The tag of the selector.
     * @param query The query.
     * @return The {@link org.fitting.Selector} instance.
     * @throws FittingException When the selector could not be created.
     */
    protected final Selector getSelector(String selector, String query) throws FittingException {
        SelectorProvider provider = getConnector().getSelectorProvider();
        Selector select = provider.getSelector(selector, query);
        if (select == null) {
            throw new FormattedFittingException(format("No selector was available for tag [%s] with query [%s].", selector, query));
        }
        return select;
    }

    /**
     * Get the currently active {@link org.fitting.FittingAction} implementation.
     * @return The {@link org.fitting.FittingAction}.
     */
    protected final FittingAction getFittingAction() {
        return getConnector().getFittingAction();
    }

    /**
     * Get the currently active {@link org.fitting.ElementContainer}.
     * @return The active {@link org.fitting.ElementContainer}.
     */
    protected final ElementContainer getActiveContainer() {
        return getConnector().getElementContainerProvider().getActiveElementContainer();
    }

    /**
     * Get the search context by its id.
     * <p/>
     * The search context is either a WebElement or the WebDriver.
     * @return The search context or null if there is no search context for the given id.
     */
    protected final SearchContext getSearchContext() {
        // @TODO Implement a default (public static final) ID for the default search context provider.
        return FittingContainer.get().getDefaultSearchContext(); //getSearchContext(null);
    }

    /**
     * Get the search context provider by its id.
     * <p/>
     * @param id The id of the search context.
     * @return The search context provider  or null if there is no search context provider for the given id.
     */
    protected final SearchContextProvider getSearchContextProvider(final String id) {
        return providers.get().getSearchContextProvider(id);
    }

    /**
     * Get the search context by its id.
     * <p/>
     * The search context is either a WebElement or the WebDriver.
     * @param id The id of the search context.
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
