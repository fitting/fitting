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
import org.fitting.selenium.BrowserConnector;
import org.fitting.selenium.FittingSeleniumConnector;
import org.fitting.selenium.SeleniumServerManager;

/**
 * Fixture for initialising the Selenium coupling and starting/navigating browsers.
 *
 * @author Barre Dijkstra
 * @since 1.0
 */
public class BrowserFixture {
    /** The number of ports to try when creating a new in-process Selenium server. */
    private final static int TRIES_FOR_SELENIUM_PORTS = 20;
    /** The port used for the in-process selenium server, if any. */
    private ThreadLocal<Integer> seleniumPort = new ThreadLocal<Integer>();

    /**
     * Open a browser, starting a local selenium server in process, and go to a specific URL.
     *
     * @param browser The browser to open. See {@link org.fitting.selenium.Browser}.
     * @param url     The URL to navigate to.
     */
    public void openBrowserFor(String browser, String url) throws Exception {
        if (seleniumPort.get() == null) {
            int port = SeleniumServerManager.getInstance().startServerOnFirstAvailablePort(TRIES_FOR_SELENIUM_PORTS);
            seleniumPort.set(port);
        }
        openBrowserOnHostWithPortFor(browser, "localhost", seleniumPort.get(), url);
    }

    /**
     * Open a browser, connecting to an external selenium running on the provided host and port, and go to a specific URL.
     *
     * @param browser The browser to open. See {@link org.fitting.selenium.Browser}.
     * @param host    The host the selenium server is running on.
     * @param port    The port the selenium server is running on.
     * @param url     The URL to navigate to.
     */
    public void openBrowserOnHostWithPortFor(String browser, String host, int port, String url) {
        FittingContainer.set(new FittingSeleniumConnector(BrowserConnector.builder().withBrowser(browser).onHost(host, port).build()));
        openUrl(url);
    }

    public void closeBrowser() {
        FittingConnector connector = FittingContainer.get();
        connector.destroy();
    }

    /**
     * Navigate the active browser window to the provided URL.
     *
     * @param url The URL to navigate to.
     */
    public void openUrl(final String url) {
        FittingContainer.get().getElementContainerProvider().navigateElementContainerTo(stripURL(url));
    }

    /**
     * Since Selenium passes URLs as full &lt;a&gt;-tags, strip the tags from it.
     *
     * @param url The URL to strip.
     *
     * @return The stripped URL.
     */
    private static final String stripURL(String url) {
        String uri;
        if (url.startsWith("<") && url.endsWith("</a>")) {
            uri = url.split(">", 2)[1].split("<", 2)[0];
        } else {
            uri = url;
        }
        return uri;
    }
}
