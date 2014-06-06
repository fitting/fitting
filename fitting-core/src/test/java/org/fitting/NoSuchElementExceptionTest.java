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

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/** Unit tests for {@link NoSuchElementException}. */
@RunWith(MockitoJUnitRunner.class)
public class NoSuchElementExceptionTest {
    /** The value to return as the toString representation of the SearchContext. */
    private static final String SEARCHCONTEXT_TOSTRING = "searchContextToString";
    /** The value to return as the toString representation of the selector. */
    private static final String SELECTOR_TOSTRING = "selectorToString";
    /** Mock search context. */
    @Mock
    private SearchContext searchContext;
    /** Mock selector. */
    @Mock
    private Selector selector;

    /** Set up the environment, called before every test execution. */
    @Before
    public void setUp() {
        when(searchContext.toString()).thenReturn(SEARCHCONTEXT_TOSTRING);
        when(selector.toString()).thenReturn(SELECTOR_TOSTRING);
    }

    /**
     * Given a NoSuchElementException with a search context and selector. <br/>
     * When {@link NoSuchElementException#getMessage()} is called. <br/>
     * Then a formatted exception message should be returned, referencing the search context and selector.
     * @see NoSuchElementException#getMessage()
     */
    @Test
    public void shouldCreateFormattedExceptionMessage() {
        String errorMessage = format("message:<<FITTING_ERROR No element found on search context %s matching selector %s>>", SEARCHCONTEXT_TOSTRING, SELECTOR_TOSTRING);
        NoSuchElementException exception = new NoSuchElementException(searchContext, selector);

        assertEquals(errorMessage, exception.getMessage());
    }
}
