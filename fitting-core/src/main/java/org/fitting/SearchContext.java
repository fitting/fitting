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

import java.util.List;

/**
 * Search context for finding elements on.
 */
public interface SearchContext {
    /**
     * Find all elements on the context matching the given selector.
     *
     * @param selector The selector.
     *
     * @return The matching elements.
     *
     * @throws FittingException When the query failed to execute.
     */
    List<Element> findElementsBy(Selector selector) throws FittingException;

    /**
     * Find a single element on the context matching the given selector.
     *
     * @param selector The selector.
     *
     * @return The matching element.
     *
     * @throws NoSuchElementException When the element could not be found.
     * @throws FittingException       When the query failed to execute.
     */
    Element findElementBy(Selector selector) throws NoSuchElementException, FittingException;

    /**
     * Wait for a sub-element to become present within the element.
     *
     * @param selector The selector of the element.
     * @param timeout  The time to wait for the element before timing-out, in milliseconds.
     *
     * @throws NoSuchElementException When no element was found within the provided time-out.
     */
    void waitForElement(Selector selector, int timeout) throws NoSuchElementException;
}
