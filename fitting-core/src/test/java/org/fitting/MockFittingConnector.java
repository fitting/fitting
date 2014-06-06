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

/** Mock {@link FittingConnector}. */
public class MockFittingConnector implements FittingConnector {
    public static final String NAME = "mock-fitting-connector";
    private final SelectorProvider selectorProvider;
    private final ElementContainerProvider elementContainerProvider;

    public MockFittingConnector() {
        this(null, null);
    }

    public MockFittingConnector(final SelectorProvider selectorProvider, final ElementContainerProvider elementContainerProvider) {
        this.selectorProvider = selectorProvider;
        this.elementContainerProvider = elementContainerProvider;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public SelectorProvider getSelectorProvider() {
        return selectorProvider;
    }

    @Override
    public ElementContainerProvider getElementContainerProvider() {
        return elementContainerProvider;
    }

    @Override
    public SearchContext getDefaultSearchContext() {
        return null;
    }

    @Override
    public void destroy() {
    }
}
