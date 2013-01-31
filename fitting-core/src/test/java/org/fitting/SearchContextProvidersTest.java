package org.fitting;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Test class for SearchContextProviders. */
public class SearchContextProvidersTest {
    private SearchContextProviders providers; // class under test

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private WebDriverSearchContextProvider provider;

    @Before
    public void setUp() throws Exception {
        provider = new WebDriverSearchContextProvider();
    }

    @Test
    public void shouldThrowExceptionWhenNotProvidingAnySearchContextProviders() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("No SearchContextProviders provided");
        providers = new SearchContextProviders();
    }

    @Test
    public void shouldInitializeWhenProvidingSearchContextProviders() throws Exception {
        providers = new SearchContextProviders(provider);
    }

    @Test
    public void shouldGetProviderByName() throws Exception {
        providers = new SearchContextProviders(provider);
        assertEquals(provider, providers.getSearchContextProvider("driver"));
    }

    @Test
    public void shouldGetProviderIds() throws Exception {
        providers = new SearchContextProviders(provider);
        assertEquals(1, providers.getProviderIds().size());
    }

    @Test
    public void shouldIndicateIfSearchContextProviderIsKnown() {
        providers = new SearchContextProviders(provider);
        assertTrue(providers.isSearchContextProviderKnown("driver"));
        assertFalse(providers.isSearchContextProviderKnown("unknown"));
    }
}
