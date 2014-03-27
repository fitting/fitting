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

package org.fitting;

/**
 * Generic element container which can be anything that acts as a container for elements like panels, windows, frames and so forth.
 */
public interface ElementContainer extends SearchContext {
    /**
     * Get the id of the container.
     *
     * @return The id of the container.
     */
    String getId();

    /**
     * Get the id of the parent container.
     *
     * @return The id of the parent container or <code>null</code> if there is no parent container.
     *
     * @see org.fitting.ElementContainer#hasParent()
     */
    String getParentId();

    /**
     * Check if the container has a parent container.
     *
     * @return <code>true</code> if the container has a parent container.
     *
     * @see org.fitting.ElementContainer#getParentId()
     */
    boolean hasParent();

    /**
     * Get the size of the container in pixels.
     *
     * @return The size.
     */
    Dimension getSize();

    /**
     * Set the size of the container.
     *
     * @param size The new size.
     *
     * @throws FittingException When the size could not be set.
     */
    void setSize(Dimension size) throws FittingException;

    /**
     * Check if the container is the current active container.
     *
     * @return <code>true</code> if the container is the active container.
     */
    boolean isActive();

    /**
     * Make the container the active container.
     * <p>
     * If the container does not support being activated (e.g. for panels), it is up to the implementing class to decide whether to delegate the call to a parent container,
     * ignore the call or  throw an exception.
     * </p>
     */
    void activate();

    /**
     * Refresh the container.
     * <p>
     * If the container does not support closing, it is up to the implementing class to decide whether to delegate to a parent container,
     * ignore the call or throw an exception.
     * </p>
     */
    void refresh();

    /**
     * Check if the container is a root container, like e.g. a window.
     *
     * @return <code>true</code> if the container is a root container.
     */
    boolean isRootContainer();

    /**
     * Close the container.
     * <p>
     * If the container does not support closing, it is up to the implementing class to decide whether to delegate to a parent container,
     * ignore the call or throw an exception.
     * </p>
     */
    void close();

    /**
     * Navigate the element container to the provided URI.
     *
     * @param uri The URI.
     */
    void navigateTo(String uri);

    /**
     * Get the current URI of element container.
     *
     * @return The current URI of the container.
     */
    String currentLocation();

    /**
     * Get the title of the element container.
     *
     * @return The title of the container or <code>null</code> if the container has none.
     */
    String getTitle();

    /**
     * Tell the container to block operations for a given amount of time.
     *
     * @param seconds The time to wait in seconds.
     */
    void waitSeconds(int seconds);

    /**
     * Check if the provided text is present in the container.
     *
     * <p>
     * <em>Warning</em>
     * Depending on the implementation this operation can be quite expensive.
     * </p>
     *
     * @param text The text to search for.
     *
     * @return <code>true</code> if the given text is present.
     */
    boolean isTextPresent(String text);
}
