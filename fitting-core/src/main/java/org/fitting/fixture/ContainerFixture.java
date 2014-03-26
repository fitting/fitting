/*
 * Licensed to the Fitting Project under one
 * or more contributor license agreements.  See the NOTICE.txt file
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

package org.fitting.fixture;

import org.fitting.ElementContainer;
import org.fitting.FittingException;
import org.fitting.FormattedFittingException;

/**
 * Fixture for basic interaction with UI containers/windows.
 */
public class ContainerFixture extends FittingFixture {
    /**
     * Get the title of the current window.
     *
     * @return The title text.
     *
     * @throws FittingException When no window was opened or the title couldn't be read.
     */
    public String windowTitle() throws FittingException {
        ElementContainer activeContainer = getElementContainerProvider().getActiveElementContainer();
        if (activeContainer == null) {
            throw new FormattedFittingException("Tried to retrieve the title of the active window, with no window open.");
        }
        return activeContainer.getTitle();
    }

    /**
     * Navigate the currently active container to the provided URI.
     *
     * @param uri The URI to navigate to.
     *
     * @throws FittingException When no container was active or there was a problem navigating to the URI.
     */
    public void navigateTo(String uri) throws FittingException {
        getElementContainerProvider().navigateElementContainerTo(uri);
    }
}
