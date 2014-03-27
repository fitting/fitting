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

import java.util.List;

import org.fitting.*;
import org.openqa.selenium.SearchContext;

/** {@link org.fitting.ElementContainer} implementation for Selenium based HTML (i)frames. */
public class SeleniumFrame implements ElementContainer, SeleniumSearchContext {
    @Override
    public String getId() {
        // TODO Implement me!
        return null;
    }

    @Override
    public String getParentId() {
        // TODO Implement me!
        return null;
    }

    @Override
    public boolean hasParent() {
        // TODO Implement me!
        return false;
    }

    @Override
    public Dimension getSize() {
        // TODO Implement me!
        return null;
    }

    @Override
    public void setSize(final Dimension size) throws FittingException {
        // TODO Implement me!
    }

    @Override
    public boolean isActive() {
        // TODO Implement me!
        return false;
    }

    @Override
    public void activate() {
        // TODO Implement me!
    }

    @Override
    public void refresh() {
        // TODO Implement me!
    }

    @Override
    public boolean isRootContainer() {
        // TODO Implement me!
        return false;
    }

    @Override
    public void close() {
        // TODO Implement me!
    }

    @Override
    public void navigateTo(String uri) {
        // TODO Implement me!
    }

    @Override
    public String currentLocation() {
        // TODO Implement me!
        return null;
    }

    @Override
    public String getTitle() {
        // TODO Implement me!
        return null;
    }

    @Override
    public void waitSeconds(final int seconds) {
        // TODO Implement me!
    }

    @Override
    public boolean isTextPresent(final String text) {
        // TODO Implement me!
        return false;
    }

    @Override
    public List<Element> findElementsBy(final Selector selector) {
        // TODO Implement me!
        return null;
    }

    @Override
    public Element findElementBy(final Selector selector) {
        // TODO Implement me!
        return null;
    }

    @Override
    public void waitForElement(final Selector selector, final int timeout) throws NoSuchElementException {
        // TODO Implement me!
    }

    @Override
    public void waitForElementWithContent(final Selector selector, final String content, final int timeout) {
        // TODO Implement me!
    }

    @Override
    public SearchContext getImplementation() {
        // TODO Implement me!
        return null;
    }
}
