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

import java.util.HashMap;
import java.util.Set;

/**
 * Container for holding multiple search contexts, indexed by their id.
 * Basically, this class extends HashMap<String, SearchContextProvider>
 * and provides some user friendly util methods on top of that.
 */
public final class SearchContextProviders extends HashMap<String, SearchContextProvider> {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new SearchContextProviders instance.
     * @param providers The providers.
     */
    public SearchContextProviders(final SearchContextProvider... providers) {
        if ((providers == null) || (providers.length == 0)) {
            throw new IllegalArgumentException("No SearchContextProviders provided.");
        }
        for (final SearchContextProvider provider : providers) {
            this.put(provider.getId(), provider);
        }
    }

    /**
     * Get the SearchContextProvider instance for the given id.
     * @param id The id of the provider.
     * @return The provider or null if no provider was known under the given id.
     */
    public SearchContextProvider getSearchContextProvider(final String id) {
        return this.get(id);
    }

    /**
     * Get the available provider ids.
     * @return The provider ids.
     */
    public Set<String> getProviderIds() {
        return this.keySet();
    }

    /**
     * Check if there is a search provider known for the given id.
     * @param id The id to check.
     * @return True if there as a SearchContextProvider known under the given id.
     */
    public boolean isSearchContextProviderKnown(final String id) {
        return id != null && this.containsKey(id);
    }
}