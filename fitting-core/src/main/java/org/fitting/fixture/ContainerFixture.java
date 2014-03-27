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

import org.fitting.*;

/**
 * Fixture for basic interaction with UI containers/windows.
 */
public class ContainerFixture extends FittingFixture {
    /**
     * Get the title of the current window.
     * <p>
     * FitNesse usage:
     * <pre>| window title |</pre>
     * </p>
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
     * <p>
     * FitNesse usage:
     * <pre>| navigate to | [uri] |</pre>
     * </p>
     *
     * @param uri The URI to navigate to.
     *
     * @throws FittingException When no container was active or there was a problem navigating to the URI.
     */
    public void navigateTo(String uri) throws FittingException {
        getElementContainerProvider().navigateElementContainerTo(uri);
    }

    /**
     * Resize the container.
     * <p>
     * FitNesse usage:
     * <pre>| resize to | [width] | by | [height] |</pre>
     * </p>
     *
     * @param width  The new width for the container.
     * @param height The new height for the container.
     *
     * @throws FittingException When the container couldn't be resized.
     */
    public void resizeToBy(String width, String height) throws FittingException {
        Dimension size;
        try {
            int w = Integer.parseInt(width);
            int h = Integer.parseInt(height);
            size = new Dimension(w, h);
        } catch (NumberFormatException e) {
            throw new FormattedFittingException(String.format("Cannot resize the container to the width [%s] and height [%s]", width, height));
        }

        getElementContainerProvider().getActiveElementContainer().setSize(size);
    }

    /**
     * Check if a given text is present in one of the elements of the container.
     * <p>
     * FitNesse usage:
     * <pre>| container contains text | [text] |</pre>
     * </p>
     *
     * <p>
     * <em>Warning</em>
     * Depending on the implementation this operation can be quite expensive.
     * </p>
     *
     * @param text The text to search for.
     *
     * @return <code>true</code> if the container contains the given text.
     *
     * @throws FittingException When searching failed.
     */
    public boolean containerContainsText(String text) throws FittingException {
        return getElementContainerProvider().getActiveElementContainer().isTextPresent(text);
    }

    /**
     * Wait for a given time.
     * <p>
     * FitNesse usage:
     * <pre>| wait | [seconds] | seconds |</pre>
     * </p>
     *
     * @param seconds The time in seconds.
     */
    public void waitSeconds(int seconds) {
        getElementContainerProvider().getActiveElementContainer().waitSeconds(seconds);
    }

    /**
     * Wait for an element to be loaded into the container within a provided timeout.
     * <p>
     * FitNesse usage:
     * <pre>| wait for element with | [selector] | being | [identifier] | within | [seconds] | seconds |</pre>
     * </p>
     *
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @param seconds    The time in seconds.
     *
     * @return <code>true</code> if the element was found within the timeout time, <code>false</code> if not.
     */
    public boolean waitSecondsForElementWithBeing(int seconds, String selector, String identifier) {
        boolean present;
        try {
            getSearchContext().waitForElement(getSelector(selector, identifier), seconds);
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }
        return present;
    }

    /**
     * Wait for an element to be loaded into the container within a provided timeout.
     * <p>
     * FitNesse usage:
     * <pre>| wait | [seconds] | seconds for element with | [selector] | being | [identifier] | and containing | [contents] |</pre>
     * </p>
     *
     * @param selector   The name of the selector.
     * @param identifier The identifier/query for the selector.
     * @param contents   The contents to search for.
     * @param seconds    The time in seconds.
     *
     * @return <code>true</code> if the element was found within the timeout time, <code>false</code> if not.
     */
    public boolean waitSecondsForElementWithBeingAndContaining(int seconds, String selector, String identifier, String contents) {
        boolean present;
        try {
            getSearchContext().waitForElementWithContent(getSelector(selector, identifier), contents, seconds);
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }
        return present;
    }
}
