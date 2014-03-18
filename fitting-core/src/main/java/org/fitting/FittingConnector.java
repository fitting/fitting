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
 * Connector for connecting Fitting implementation modules to Fitting.
 */
public interface FittingConnector {

    /**
     * The name of the implementation.
     * @return The name.
     */
    String getName();

    /**
     * Get the {@link SelectorProvider} implementation for the implementation.
     * @return The {@link SelectorProvider}.
     */
    SelectorProvider getSelectorProvider();

    /**
     * Get the {@link org.fitting.FittingAction} implementation for the implementation.
     * @return The {@link org.fitting.FittingAction}.
     */
    FittingAction getFittingAction();

    /**
     * Get the {@link ElementContainerProvider} implementation for the implementation.
     * @return The {@link ElementContainerProvider}.
     */
    ElementContainerProvider getElementContainerProvider();

    /**
     * Get the default {@link org.fitting.SearchContext}, generally providing the root as search context.
     * @return The default search context.
     */
    SearchContext getDefaultSearchContext();

    /**
     * Destroy/tear-down the current connector implementation.
     */
    void destroy();
}
