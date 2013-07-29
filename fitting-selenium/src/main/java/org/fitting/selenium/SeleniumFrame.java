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

import org.fitting.By;
import org.fitting.Dimension;
import org.fitting.Element;
import org.fitting.ElementContainer;
import org.openqa.selenium.SearchContext;

/**
 * {@link ElementContainer} implementation for Selenium based HTML (i)frames.
 */
public class SeleniumFrame implements ElementContainer, SeleniumSearchContext {
    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getParentId() {
        return null;
    }

    @Override
    public boolean hasParent() {
        return false;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void activate() {
    }

    @Override
    public void refresh() {
    }

    @Override
    public boolean isRootContainer() {
        return false;
    }

    @Override
    public void close() {
    }

    @Override
    public List<Element> findElementsBy(final By byClause) {
        return null;
    }

    @Override
    public Element findElementBy(final By byClause) {
        return null;
    }

    @Override
    public SearchContext getImplementation() {
        return null;
    }
}
