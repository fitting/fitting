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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Unit tests for {@link org.fitting.CachedSearchContextProvider}. */
@RunWith(MockitoJUnitRunner.class)
public class CachedSearchContextProviderTest {
    /** The id for the search context. */
    private static final String CONTEXT_ID = "contextId";
    /** The id for the selector. */
    private static final String SELECTOR_ID = "selectorId";
    /** The query for the selector. */
    private static final String SELECTOR_QUERY = "selectorQuery";
    /** Mock search context. */
    @Mock
    private SearchContext searchContext;
    /** Mock element. */
    @Mock
    private Element element;
    /** Mock selector provider. */
    @Mock
    private SelectorProvider selectorProvider;
    /** Mock selector. */
    @Mock
    private Selector selector;

    /** The instance of the class under test. */
    private CachedSearchContextProvider instance;

    /**
     * Set up the mock behaviour, executed before each test.
     * @throws Exception When initialization fails.
     */
    @Before
    public void setUp() throws Exception {
        when(selectorProvider.getSelector(eq(SELECTOR_ID), eq(SELECTOR_QUERY))).thenReturn(selector);
        when(searchContext.findElementBy(eq(selector))).thenReturn(element);

        instance = new CachedSearchContextProvider(CONTEXT_ID, SELECTOR_ID, SELECTOR_QUERY);
    }

    /**
     * Ensure the ID of the context is set at creation.
     * @see org.fitting.CachedSearchContextProvider#getId()
     */
    @Test
    public void shouldSetId() {
        assertEquals("Context ID not properly set on creation.", CONTEXT_ID, instance.getId());
    }

    /**
     * Ensure the selector id and query are set at creation.
     * @see org.fitting.CachedSearchContextProvider#getSearchContext(SearchContext, SelectorProvider)
     */
    @Test
    public void shouldSetSelectorCriteria() {
        instance.getSearchContext(searchContext, selectorProvider);

        verify(selectorProvider).getSelector(eq(SELECTOR_ID), eq(SELECTOR_QUERY));
    }

    /**
     * Ensure the right element is looked up when retrieving the search context.
     * @see org.fitting.CachedSearchContextProvider#getSearchContext(SearchContext, SelectorProvider)
     */
    @Test
    public void shouldLookUpElement() {
        assertSame("Different element returned.", element, instance.getSearchContext(searchContext, selectorProvider));

        verify(searchContext).findElementBy(eq(selector));
    }

    /**
     * Ensure an exception is thrown, originating from the root search context, when the element was not found.
     * @see org.fitting.CachedSearchContextProvider#getSearchContext(SearchContext, SelectorProvider)
     */
    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionForUnknownElement() {
        String INVALID = "invalid";
        Selector invalidElement = mock(Selector.class);

        when(selectorProvider.getSelector(eq(INVALID), eq(INVALID))).thenReturn(invalidElement);
        when(searchContext.findElementBy(eq(invalidElement))).thenThrow(NoSuchElementException.class);

        instance = new CachedSearchContextProvider(CONTEXT_ID, INVALID, INVALID);
        instance.getSearchContext(searchContext, selectorProvider);
    }

    /**
     * Ensure an exception is thrown if an invalid root search context was provided.
     * @see org.fitting.CachedSearchContextProvider#getSearchContext(SearchContext, SelectorProvider)
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidSearchContext() {
        instance.getSearchContext(null, selectorProvider);
    }

    /**
     * Ensure an exception is thrown if an invalid selector provider was provided.
     * @see org.fitting.CachedSearchContextProvider#getSearchContext(SearchContext, SelectorProvider)
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidSelectorProvider() {
        instance.getSearchContext(searchContext, null);
    }

    /**
     * Ensure a search context is only looked up once once it has been found.
     * @see org.fitting.CachedSearchContextProvider#getSearchContext(SearchContext, SelectorProvider)
     */
    @Test
    public void shouldOnlyLookupContextOnce() {
        instance.getSearchContext(searchContext, selectorProvider);
        instance.getSearchContext(searchContext, selectorProvider);
        verify(searchContext, times(1)).findElementBy(any(Selector.class));
    }
}
