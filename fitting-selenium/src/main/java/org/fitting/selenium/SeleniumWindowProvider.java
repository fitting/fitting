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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fitting.ElementContainer;
import org.fitting.ElementContainerProvider;
import org.fitting.FittingException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * {@link ElementContainerProvider} Selenium implementation for browser windows.
 */
public class SeleniumWindowProvider extends ElementContainerProvider {
    /**
     * The web driver.
     */
    private WebDriver driver;

    /**
     * Create a new provider.
     *
     * @param driver The web driver to use.
     */
    public SeleniumWindowProvider(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    protected ElementContainer createContainer(final String uri) throws FittingException {
        if (!JavascriptExecutor.class.isAssignableFrom(driver.getClass())) {
            throw new FittingException("The provided webdriver does not support javascript execution, a new window can not be created.");
        }
        final Set<String> currentHandles = driver.getWindowHandles();
        final String currentWindowHandle = driver.getWindowHandle();
        final JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.open('" + uri + "')");

        final List<String> newHandles = new ArrayList<String>(driver.getWindowHandles());
        newHandles.removeAll(currentHandles);
        if (newHandles.size() != 1) {
            throw new FittingException("There were " + newHandles.size() + " windows created, while 1 was expected.");
        }
        final String newWindowHandle = newHandles.get(0);
        return new SeleniumWindow(newWindowHandle, currentWindowHandle, driver);
    }

    @Override
    protected ElementContainer createContainer(final String uri, final ElementContainer parent) throws FittingException {
        // TODO Implement me!
        return null;
    }
}
