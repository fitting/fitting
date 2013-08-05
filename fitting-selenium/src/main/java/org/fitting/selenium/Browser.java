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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

/** Selenium browser object. */
public class Browser {
    /** The underlying WebDriver implementation. */
    private WebDriver webDriver;
    /** Flag indicating if javascript is enabled. */
    private boolean javascriptEnabled;

    /**
     * Create a new Browser instance.
     *
     * @param capabilities The desired browser capabilities.
     * @param url          The URL to connect to.
     */
    public Browser(DesiredCapabilities capabilities, URL url) {
        webDriver = new RemoteWebDriver(url, capabilities);
        javascriptEnabled = capabilities.isJavascriptEnabled();
    }

    /**
     * Get the underlying WebDriver implementation.
     *
     * @return The WebDriver.
     */
    public WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * Check if JavaScript is enabled.
     *
     * @return <code>true</code> if javascript is enabled.
     */
    public boolean isJavascriptEnabled() {
        return javascriptEnabled;
    }

    /** Destroy the Browser object. */
    public void destroy() {
        this.webDriver.close();
        this.webDriver = null;
        this.javascriptEnabled = false;
    }
}
