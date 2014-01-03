package org.fitting;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

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
        private String id;
        private SearchContext searchContext;
        public TestSearchContextProvider(final String id, final SearchContext searchContext) {
            this.id = id;
            this.searchContext = searchContext;
        }
        @Override
        public String getId() {
            return this.id;
        }
        @Override
        public SearchContext getSearchContext() {
            return this.searchContext;
        }
    }

}
