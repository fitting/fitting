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

package org.fitting.selenium;

import org.fitting.Selector;
import org.fitting.SelectorProvider;
import org.fitting.FittingException;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/** {@link org.fitting.SelectorProvider} implementation for Selenium based {@link org.fitting.Selector}-clauses. */
public class SeleniumSelectorProvider implements SelectorProvider {
    /** The selectors provided by this provider. */
    private static final Map<String, SeleniumByFactory> BY_CLAUSES = new HashMap<String, SeleniumByFactory>() {{
        put(SeleniumSelector.NAME_CLASS_NAME, new SeleniumByFactory() {
            @Override
            public SeleniumSelector create(String query) {
                return SeleniumSelector.byClassName(query);
            }
        });
        put(SeleniumSelector.NAME_XPATH, new SeleniumByFactory() {
            @Override
            public SeleniumSelector create(String query) {
                return SeleniumSelector.byXPath(query);
            }
        });
        put(SeleniumSelector.NAME_CSS_SELECTOR, new SeleniumByFactory() {
            @Override
            public SeleniumSelector create(String query) {
                return SeleniumSelector.byCssSelector(query);
            }
        });
        put(SeleniumSelector.NAME_ID, new SeleniumByFactory() {
            @Override
            public SeleniumSelector create(String query) {
                return SeleniumSelector.byId(query);
            }
        });
        put(SeleniumSelector.NAME_LINK_TEXT, new SeleniumByFactory() {
            @Override
            public SeleniumSelector create(String query) {
                return SeleniumSelector.byLinkText(query);
            }
        });
        put(SeleniumSelector.NAME_NAME, new SeleniumByFactory() {
            @Override
            public SeleniumSelector create(String query) {
                return SeleniumSelector.byName(query);
            }
        });
        put(SeleniumSelector.NAME_PARTIAL_LINK_TEXT, new SeleniumByFactory() {
            @Override
            public SeleniumSelector create(String query) {
                return SeleniumSelector.byPartialLinkText(query);
            }
        });
        put(SeleniumSelector.NAME_TAG_NAME, new SeleniumByFactory() {
            @Override
            public SeleniumSelector create(String query) {
                return SeleniumSelector.selectorName(query);
            }
        });
    }};

    @Override
    public String[] getAvailableTags() {
        return BY_CLAUSES.keySet().toArray(new String[BY_CLAUSES.size()]);
    }

    @Override
    public Selector getSelector(String selector, String query) {
        if (isEmpty(selector)) {
            throw new FittingException(format("Can't instantiate Selenium selector with query [%s] with null tag.", query));
        }
        if (isEmpty(query)) {
            throw new FittingException(format("Can't instantiate Selenium selector [%s] with a null query.", selector));
        }
        if (!BY_CLAUSES.containsKey(selector)) {
            throw new FittingException(format("No Selenium selector found with tag [%s] for query [%s]", selector, query));
        }
        return BY_CLAUSES.get(selector).create(query);
    }

    /** Factory for instantiating {@link SeleniumSelector} instances. */
    private static interface SeleniumByFactory {
        /**
         * Create a new {@link SeleniumSelector} instance with the given query.
         * @param query The query.
         * @return The {@link SeleniumSelector} instance.
         */
        SeleniumSelector create(String query);
    }
}
