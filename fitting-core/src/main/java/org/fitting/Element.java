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
     *
     * @return The name.
     */
    String getName();

    /**
     * Get the type of element.
     *
     * @return The type of element.
     */
    String getType();

    /**
     * Get the displayed text of the element.
     *
     * @return The text.
     */
    String getText();

    /**
     * Get the value of an attribute on the element.
     * The implementation can either decide to return <code>null</code> or throw one of the {@link FittingException}-exceptions when there is no attribute with the given name.
     *
     * @param attributeName The name of the attribute.
     *
     * @return The value.
     */
    String getAttributeValue(String attributeName);

    /**
     * Check if the element is currently active (e.g. selected, etc.).
     *
     * @return <code>true</code> if the element is active or <code>false</code> if not or the element can't be active.
     */
    boolean isActive();

    /**
     * Check if the element is displayed (this also includes being visible, etc.).
     *
     * @return <code>true</code> if the element is displayed.
     */
    boolean isDisplayed();

    /**
     * Get the absolute location of the element within the container.
     *
     * @return The location or <code>null</code> if no location information was available.
     */
    Point getLocation();

    /**
     * Get the size of the element.
     *
     * @return The size or <code>null</code> if no size information was available.
     */
    Dimension getSize();

    /**
     * Check if the element allows input and/or setting of the value.
     *
     * @return <code>true</code> if the element is an input element.
     */
    boolean isInput();

    /**
     * Get the value of the element.
     *
     * <p>
     * Returns the same result as {@link Element#getText()} for non-input elements.
     * </p>
     *
     * @return The value.
     *
     * @see Element#getText()
     */
    String getValue();

    /**
     * Click the element.
     */
    void click();

    /**
     * Send a sequence of keys to the element.
     *
     * @param characters The keys to send.
     */
    void sendKeys(CharSequence... characters);

    /**
     * Select the given value for an input element.
     *
     * @param value The value.
     *
     * @throws FittingException When the element does not allow the setting of the value.
     * @see Element#isInput()
     */
    void setValue(String value) throws FittingException;

    /**
     * Set the value of the element to that of the one with the provided displayed text for an input element.
     *
     * @param text The displayed text of the value.
     *
     * @throws FittingException When the element does not allow the setting of the value by text.
     * @see Element#isInput()
     */
    void setValueWithText(String text) throws FittingException;

    /**
     * Clear the value for the element if applicable.
     */
    void clear();
}
