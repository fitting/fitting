/*
 * Licensed to the Fitting Project under one
 * or more contributor license agreements.  See the NOTICE.txt file
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

package org.fitting;

/**
 * {@link SearchContextProvider} implementation that caches the search context, updating only when the root changes.
 *
 * <p>
 * The search context is looked up only once, at time of first call given the provided context.
 * Subsequent calls will always return the same context.
 * </p>
 *
 * <p>
 * This class is inherently thread <em>unsafe</em>, expecting to be used in a single-thread (or thread-bound) context.
 * </p>
 */
public class CachedSearchContextProvider implements SearchContextProvider {
    /** The id of the SearchContextProvider. */
    private final String id;
    /** The identifier for the selector. */
    private final String selectorIdentifier;
    /** The query for the selector. */
    private final String selectorQuery;
    /** The search context. */
    private SearchContext context;

    /**
     * Create a new CachedSearchContextProvider.
     *
     * @param id                 The id of the SearchContextProvider.
     * @param selectorIdentifier The identifier for the selector.
     * @param selectorQuery      The query for the selector.
     */
    public CachedSearchContextProvider(String id, String selectorIdentifier, String selectorQuery) {
        this.id = id;
        this.selectorIdentifier = selectorIdentifier;
        this.selectorQuery = selectorQuery;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * When no matching element is found on the provided root context, an exception is thrown. Subsequent calls will attempt to find the element again.
     * </p>
     *
     * @throws NoSuchElementException   When no matching element was found on the provided root search context.
     * @throws IllegalArgumentException When an invalid {@link SearchContext} or {@link SelectorProvider} are provided.
     */
    @Override
    public SearchContext getSearchContext(SearchContext root, SelectorProvider provider) throws IllegalArgumentException, NoSuchElementException {
        if (root == null) {
            throw new IllegalArgumentException("No root search context provided.");
        }
        if (provider == null) {
            throw new IllegalArgumentException("No selector provider provided.");
        }
        if (context == null) {
            this.context = root.findElementBy(provider.getSelector(selectorIdentifier, selectorQuery));
        }
        return this.context;
    }
}
