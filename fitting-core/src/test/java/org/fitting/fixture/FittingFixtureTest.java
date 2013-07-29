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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link FittingFixture}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FittingContainer.class)
public class FittingFixtureTest {
    @Mock
    private FittingConnector connector;
    @Mock
    private ByProvider byProvider;
    @Mock
    private SearchContextProvider searchContextProvider;
    @Mock
    private By by;
    @Mock
    private FittingAction action;
    @Mock
    private ElementContainerProvider containerProvider;

    /**
     * The class under test.
     */
    private FittingFixture fixture;

    /**
     * Set up the test.
     *
     * <p>
     * This method is called before every test method.
     * </p>
     */
    @Before
    public void setUp() {
        when(connector.getByProvider()).thenReturn(byProvider);
        when(connector.getElementContainerProvider()).thenReturn(containerProvider);
        when(connector.getFittingAction()).thenReturn(action);

        PowerMockito.mockStatic(FittingContainer.class);
        PowerMockito.when(FittingContainer.get()).thenReturn(connector);

        fixture = new FittingFixture(searchContextProvider) {
        };
    }

    /**
     * Ensure that the correct default search context provider is set upon creation.
     *
     * @see FittingFixture#FittingFixture()
     */
    @Test
    public void shouldSetDefaultSearchContextProviderOnCreation() {
        // TODO Implement once {@link FittingFixture#FittingFixture()} has been implemented with a default search context provider.
    }

    /**
     * Ensure that a single search context provider is set on creation.
     *
     * @see FittingFixture#FittingFixture(SearchContextProvider...)
     */
    @Test
    public void shouldSetSingleProvidedSearchContextProviderOnCreation() {
        fail("Implement me!");
    }

    /**
     * Ensure that multiple search context providers are set on creation.
     *
     * @see FittingFixture#FittingFixture(SearchContextProvider...)
     */
    @Test
    public void shouldSetMultipleProvidedSearchContextProviderOnCreation() {
        fail("Implement me!");
    }

    /**
     * Ensure that providing a null value as a search context provider is not allowed.
     *
     * @see FittingFixture#FittingFixture(SearchContextProvider...)
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenProvidingNullSearchContextProvider() {
        new FittingFixture(null) {
        };

        fail("Managed to create a new fixture with a null search context provider.");
    }

    /**
     * Ensure that providing an empty array as search context providers is not allowed.
     *
     * @see FittingFixture#FittingFixture(SearchContextProvider...)
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenProvidingNoSearchContextProvider() {
        new FittingFixture(new SearchContextProvider[] {}) {
        };

        fail("Managed to create a new fixture with no search context providers.");
    }

    /**
     * Ensure that a connector is available.
     *
     * @see FittingFixture#getConnector()
     */
    @Test
    public void shouldGetConnector() {
        FittingConnector connector = fixture.getConnector();

        assertNotNull("No connector returned.", connector);
        assertSame("Different connector returned as expected.", this.connector, connector);
    }

    /**
     * Ensure that a By-clause is created for an existing tag.
     *
     * @see FittingFixture#getByClause(String, String)
     */
    @Test
    public void shouldCreateByClauseForExistingByTag() {
        when(byProvider.getBy(eq("tag"), eq("query"))).thenReturn(this.by);

        By by = fixture.getByClause("tag", "query");

        assertNotNull("No By-clause created.", by);
        assertSame("Different By returned as expected", this.by, by);
    }

    /**
     * Ensure that an exception is thrown when a By clause is created for a non-existing tag.
     *
     * @see FittingFixture#getByClause(String, String)
     */
    @Test(expected = FittingException.class)
    public void shouldThrowExceptionForCreatingByClauseWithNonExistingByTag() {
        when(byProvider.getBy(eq("non-existing"), anyString())).thenThrow(FittingException.class);

        fixture.getByClause("non-existing", "query");

        fail("Managed to create a By-clause for a non-existing tag.");
    }

    /**
     * Ensure that a FittingAction is available.
     *
     * @see FittingFixture#getFittingAction()
     */
    @Test
    public void shouldGetFittingAction() {
        FittingAction action = fixture.getFittingAction();

        assertNotNull("No FittingAction returned.", action);
        assertSame("Different FittingAction returned as expected.", this.action, action);
    }

    /**
     * Ensure the active container is retrieved.
     *
     * @see FittingFixture#getActiveContainer()
     */
    @Test
    public void shouldGetActiveContainer() {
        fixture.getActiveContainer();

        verify(containerProvider, times(1)).getActiveElementContainer();
        verifyNoMoreInteractions(containerProvider);
    }
}
