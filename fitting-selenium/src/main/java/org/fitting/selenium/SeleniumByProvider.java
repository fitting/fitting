package org.fitting.selenium;

import java.util.HashMap;
import java.util.Map;

import org.fitting.By;
import org.fitting.ByProvider;
import org.fitting.FittingException;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * {@link ByProvider} implementation for Selenium based {@link By}-clauses.
 */
public class SeleniumByProvider implements ByProvider {
    /**
     * The By-clauses provided by this provider.
     */
    private static final Map<String, SeleniumByFactory> BY_CLAUSES = new HashMap<String, SeleniumByFactory>() {{
        put(SeleniumBy.NAME_CLASS_NAME, new SeleniumByFactory() {
            @Override
            public SeleniumBy create(String query) {
                return SeleniumBy.byClassName(query);
            }
        });
        put(SeleniumBy.NAME_XPATH, new SeleniumByFactory() {
            @Override
            public SeleniumBy create(String query) {
                return SeleniumBy.byXPath(query);
            }
        });
        put(SeleniumBy.NAME_CSS_SELECTOR, new SeleniumByFactory() {
            @Override
            public SeleniumBy create(String query) {
                return SeleniumBy.byCssSelector(query);
            }
        });
        put(SeleniumBy.NAME_ID, new SeleniumByFactory() {
            @Override
            public SeleniumBy create(String query) {
                return SeleniumBy.byId(query);
            }
        });
        put(SeleniumBy.NAME_LINK_TEXT, new SeleniumByFactory() {
            @Override
            public SeleniumBy create(String query) {
                return SeleniumBy.byLinkText(query);
            }
        });
        put(SeleniumBy.NAME_NAME, new SeleniumByFactory() {
            @Override
            public SeleniumBy create(String query) {
                return SeleniumBy.byName(query);
            }
        });
        put(SeleniumBy.NAME_PARTIAL_LINK_TEXT, new SeleniumByFactory() {
            @Override
            public SeleniumBy create(String query) {
                return SeleniumBy.byPartialLinkText(query);
            }
        });
        put(SeleniumBy.NAME_TAG_NAME, new SeleniumByFactory() {
            @Override
            public SeleniumBy create(String query) {
                return SeleniumBy.byTagName(query);
            }
        });
    }};

    @Override
    public String[] getAvailableTags() {
        return BY_CLAUSES.keySet().toArray(new String[BY_CLAUSES.size()]);
    }

    @Override
    public By getBy(String byTag, String query) {
        if (isEmpty(byTag)) {
            throw new FittingException(format("Can't instantiate Selenium By-clause with query [%s] with null tag.", query));
        }
        if (isEmpty(query)) {
            throw new FittingException(format("Can't instantiate Selenium By-clause [%s] with a null query.", byTag));
        }
        if (!BY_CLAUSES.containsKey(byTag)) {
            throw new FittingException(format("No Selenium By-clause found with tag [%s] for query [%s]", byTag, query));
        }
        return BY_CLAUSES.get(byTag).create(query);
    }

    /**
     * Factory for instantiating {@link SeleniumBy} instances.
     */
    private static interface SeleniumByFactory {
        /**
         * Create a new {@link SeleniumBy} instance with the given query.
         *
         * @param query The query.
         *
         * @return The {@link SeleniumBy} instance.
         */
        SeleniumBy create(String query);
    }
}
