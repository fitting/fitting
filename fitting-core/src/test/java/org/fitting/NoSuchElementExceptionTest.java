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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class NoSuchElementExceptionTest {

    private static final String SEARCH_CONTEXT_TO_STRING = "SEARCH_CONTEXT_TO_STRING";
    private static final String BY_TO_STRING = "BY_TO_STRING";

    @Test
    public void testException() {
        SearchContext searchContext = new SearchContext() {
            @Override
            public List<Element> findElementsBy(final Selector selector) throws FittingException {
                return null;
            }
            @Override
            public Element findElementBy(final Selector selector) throws FittingException {
                return null;
            }

            @Override
            public void waitForElement(final Selector selector, final int timeout) throws NoSuchElementException {
            }

            @Override
            public void waitForElementWithContent(final Selector selector, final String content, final int timeout) {

            }

            @Override
            public String toString() {
                return SEARCH_CONTEXT_TO_STRING;
            }
        };
        Selector selector = new Selector() {
            @Override
            public Element findElement(final SearchContext context) {
                return null;
            }
            @Override
            public List<Element> findElements(final SearchContext context) {
                return null;
            }
            @Override
            public String toString() {
                return BY_TO_STRING;
            }
        };
        NoSuchElementException fittingException = new NoSuchElementException(searchContext, selector);
        Assert.assertEquals("message:<<FITTING_ERROR No element found on search context SEARCH_CONTEXT_TO_STRING matching selector BY_TO_STRING>>", fittingException.getMessage());
    }

}
