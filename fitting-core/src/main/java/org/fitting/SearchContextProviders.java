package org.fitting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Container for holding multiple search contexts, indexed by their id. */
public final class SearchContextProviders {
    /** The providers. */
    private final Map<String, SearchContextProvider> providers;

    /**
     * Create a new SearchContextProviders instance.
     * @param providers The providers.
     */
    public SearchContextProviders(final SearchContextProvider... providers) {
        this.providers = new HashMap<String, SearchContextProvider>();
        if ((providers == null) || (providers.length == 0)) {
            throw new IllegalArgumentException("No SearchContextProviders provided.");
        }
        for (final SearchContextProvider provider : providers) {
            this.providers.put(provider.getId(), provider);
        }
    }

    /**
     * Get the SearchContextProvider instance for the given id.
     * @param id The id of the provider.
     * @return The provider or null if no provider was known under the given id.
     */
    public SearchContextProvider getSearchContextProvider(final String id) {
        SearchContextProvider provider = null;
        if ((id != null) && (this.providers.containsKey(id))) {
            provider = this.providers.get(id);
        }
        return provider;
    }

    /**
     * Get the available provider ids.
     * @return The provider ids.
     */
    public Set<String> getProviderIds() {
        return new HashSet<String>(this.providers.keySet());
    }

    /**
     * Check if there is a search provider known for the given id.
     * @param id The id to check.
     * @return True if there as a SearchContextProvider known under the given id.
     */
    public boolean isSearchContextProviderKnown(final String id) {
        return (id != null) && (this.providers.containsKey(id));
    }
}