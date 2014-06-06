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

package org.fitting;

/**
 * SearchContextProvider that uses {@link org.fitting.Selector}-based searches to locate elements.
 * <p></p>
 * The selector can be updated so that the search context of the implementing provider can be changed at runtime.
 * </p>
 */
public abstract class UpdatableSearchContextProvider implements SearchContextProvider {
    /** The selector. */
    private Selector selector;

    /**
     * Create a new {@link UpdatableSearchContextProvider}.
     * @param selector The selector used to locate the root element.
     */
    public UpdatableSearchContextProvider(final Selector selector) {
        updateSelector(selector);
    }

    /**
     * Update the selector.
     * @param selector The selector used to locate elements.
     */
    public void updateSelector(final Selector selector) {
        if (selector == null) {
            throw new IllegalArgumentException("Can't set a null selector.");
        }
        this.selector = selector;
    }

    @Override
    public SearchContext getSearchContext(final SearchContext searchContext, final SelectorProvider provider) {
        if (searchContext == null) {
            throw new FormattedFittingException("No root search context provided.");
        }
        return searchContext.findElementBy(selector);
    }
}
