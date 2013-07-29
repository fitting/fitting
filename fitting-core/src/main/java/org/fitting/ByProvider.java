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
 * Provider for creating {@link By}-clause instances.
 */
public interface ByProvider {
    /**
     * Get the By-clause with a given tag.
     *
     * @param byTag The tag fo the clause.
     * @param query The query for the By-clause.
     *
     * @return The By-clause.
     *
     * @throws FittingException When no matching By-clause could be found.
     */
    By getBy(String byTag, String query) throws FittingException;

    /**
     * Get the By-clause tags available via the provider.
     *
     * @return The available tags.
     */
    String[] getAvailableTags();
}
