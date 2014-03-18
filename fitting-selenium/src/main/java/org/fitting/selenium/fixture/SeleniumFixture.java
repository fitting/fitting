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

import org.fitting.FittingContainer;
import org.fitting.selenium.BrowserConnector;
import org.fitting.selenium.FittingSeleniumConnector;

/**
 * Fixture for initialising the Selenium coupling.
 */
public class SeleniumFixture {

    public void openBrowserOnHostWithPort(String browser, String host, int port) {
        FittingContainer.set(new FittingSeleniumConnector(BrowserConnector.builder().withBrowser(browser).onHost(host, port).build()));
    }

    public void openUrl(final String url) {
        String uri;
        if(url.startsWith("<") && url.endsWith("</a>")) {
            uri = url.split(">", 2)[1].split("<", 2)[0];
        } else {
            uri = url;
        }
        FittingContainer.get().getElementContainerProvider().navigateElementContainerTo(uri);
    }
}
