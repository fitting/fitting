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
 * Provider for providing a {@link org.fitting.SearchContext} for the {@link SearchContextProviders}.
 * @see SearchContextProviders
 */
public interface SearchContextProvider {
    /**
     * Get the id of the search context.
     * @return The id.
     */
    String getId();

    /**
     * Get the search context.
     * @param root The {@link SearchContext} to apply the search context on.
     * @param provider The {@link SelectorProvider} to use.
     * @return The search context.
     */
    SearchContext getSearchContext(final SearchContext root, SelectorProvider provider);
}
