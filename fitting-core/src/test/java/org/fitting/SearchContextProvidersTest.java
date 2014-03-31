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

import org.junit.Assert;
import org.junit.Test;

public class SearchContextProvidersTest {

    @Test(expected = IllegalArgumentException.class)
    public void testProvidersNull() {
        new SearchContextProviders((SearchContextProvider[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProvidersEmpty() {
        new SearchContextProviders(new SearchContextProvider[0]);
    }

    @Test
    public void testSearchContextProviders() {
        SearchContextProvider testProvider1 = new TestSearchContextProvider("1", null);
        SearchContextProvider testProvider2 = new TestSearchContextProvider("2", null);
        SearchContextProviders searchContextProviders = new SearchContextProviders(testProvider1, testProvider2);

        Assert.assertEquals(2, searchContextProviders.getProviderIds().size());
        Assert.assertTrue(searchContextProviders.isSearchContextProviderKnown("1"));
        Assert.assertTrue(searchContextProviders.isSearchContextProviderKnown("2"));
        Assert.assertFalse(searchContextProviders.isSearchContextProviderKnown("3"));
        Assert.assertFalse(searchContextProviders.isSearchContextProviderKnown(null));
        Assert.assertEquals(testProvider1, searchContextProviders.getSearchContextProvider("1"));
        Assert.assertEquals(testProvider2, searchContextProviders.getSearchContextProvider("2"));
        Assert.assertEquals(null, searchContextProviders.getSearchContextProvider("3"));
    }

    private class TestSearchContextProvider implements SearchContextProvider {
        private final String id;
        private final SearchContext searchContext;

        public TestSearchContextProvider(final String id, final SearchContext searchContext) {
            this.id = id;
            this.searchContext = searchContext;
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public SearchContext getSearchContext(final SearchContext root, final SelectorProvider provider) {
            return this.searchContext;
        }
    }
}
