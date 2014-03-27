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

import org.fitting.*;
import org.openqa.selenium.WebDriver;

/** Selenium implementation for the {@link org.fitting.FittingConnector}, providing access for Fitting to test using the Selenium framework. */
public class FittingSeleniumConnector implements FittingConnector {
    /** The implementation name. */
    private static final String CONNECTOR_NAME = "selenium";
    /** The browser to use. */
    private BrowserConnector browser;

    /**
     * Create a new FittingSeleniumConnect.
     * @param browser The browser to use.
     */
    public FittingSeleniumConnector(BrowserConnector browser) {
        if (browser == null) {
            throw new FittingException("The given selenium connector is null.");
        }
        this.browser = browser;
    }

    @Override
    public String getName() {
        return CONNECTOR_NAME;
    }

    @Override
    public SelectorProvider getSelectorProvider() {
        return new SeleniumSelectorProvider();
    }

    @Override
    public ElementContainerProvider getElementContainerProvider() {
        return new SeleniumWindowProvider(browser);
    }

    @Override
    public SearchContext getDefaultSearchContext() {
        return new WebDriverSearchContext(browser.getWebDriver());
    }

    @Override
    public void destroy() {
        browser.destroy();
    }

    /**
     * Get the WebDriver instance used by the connector.
     * @return The WebDriver instance.
     */
    public WebDriver getWebDriver() {
        return browser.getWebDriver();
    }
}
