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
