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

/** Generic screen element. */
public interface Element extends SearchContext {
    /**
     * Get the name of the element.
     * @return The name.
     */
    String getName();

    /**
     * Get the value of the element.
     * @return The value.
     */
    String getValue();

    /** Click the element. */
    void click();

    /**
     * Send a sequence of keys to the element.
     * @param characters The keys to send.
     */
    void sendKeys(CharSequence... characters);

    /** Clear the element. */
    void clear();

    /**
     * Get the value of an attribute on the element.
     * The implementation can either decide to return <code>null</code> or throw one of the {@link FittingException}-exceptions when there is no attribute with the given name.
     * @param attributeName The name of the attribute.
     * @return The value.
     */
    String getAttributeValue(String attributeName);

    /**
     * Check if the element is currently active (e.g. selected, etc.).
     * @return <code>true</code> if the element is active or <code>false</code> if not or the element can't be active.
     */
    boolean isActive();

    /**
     * Check if the element is displayed (this also includes being visible, etc.).
     * @return <code>true</code> if the element is displayed.
     */
    boolean isDisplayed();

    /**
     * Get the location of the element within the container.
     * @return The location or <code>null</code> if no location information was available.
     */
    Point getLocation();

    /**
     * Get the size of the element.
     * @return The size or <code>null</code> if no size information was available.
     */
    Dimension getSize();
}
