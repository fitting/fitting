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

package org.fitting.selenium.fixture;

import org.fitting.FittingConnector;
import org.fitting.FittingContainer;
import org.fitting.FittingException;
import org.fitting.fixture.FittingFixture;
import org.fitting.selenium.FittingSeleniumConnector;
import org.openqa.selenium.WebDriver;

/**
 * Base class for selenium fitting fixtures, providing all functionality of the {@link org.fitting.fixture.FittingFixture} as well.
 */
public abstract class SeleniumFixture extends FittingFixture {

    /**
     * Get the active WebDriver instance.
     * @return The WebDriver instance.
     */
    protected final WebDriver getWebDriver() {
        return getSeleniumConnector().getWebDriver();
    }

    /**
     * Get the active {@link SeleniumFixture} instance.
     * @return The instance.
     * @throws org.fitting.FittingException When the current {@link org.fitting.FittingConnector} is not available or not a {@link org.fitting.selenium.FittingSeleniumConnector}.
     */
    protected final FittingSeleniumConnector getSeleniumConnector() {
        FittingSeleniumConnector seleniumConnector;
        FittingConnector connector = FittingContainer.get();


        if (FittingSeleniumConnector.class.isAssignableFrom(connector.getClass())) {
            seleniumConnector = (FittingSeleniumConnector) connector;
        } else {
            throw new FittingException(String.format("The connector %s (%s) is not a valid SeleniumConnector.", connector.getName(), connector.getClass().getName()));
        }
        return seleniumConnector;
    }
}
