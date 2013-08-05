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

import org.fitting.By;
import org.fitting.Dimension;
import org.fitting.Element;
import org.fitting.Point;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.fitting.selenium.SeleniumDataTypeConverter.convert;

/**
 * {@link Element} Selenium implementation for Selenium HTML elements.
 *
 * @author Barre Dijkstra
 * @since 1.0
 */
public class SeleniumElement implements Element, SeleniumSearchContext {
    /** The underlying implementing Selenium WebElement. */
    private final WebElement element;

    /**
     * Create a new SeleniumElement.
     *
     * @param element The implementing Selenium WebElement.
     */
    public SeleniumElement(WebElement element) {
        this.element = element;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return element.getTagName();
    }

    /** {@inheritDoc} */
    @Override
    public String getValue() {
        return element.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void click() {
        element.click();
    }

    /** {@inheritDoc} */
    @Override
    public void sendKeys(final CharSequence... characters) {
        element.sendKeys(characters);
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        element.clear();
    }

    /** {@inheritDoc} */
    @Override
    public String getAttributeValue(String attributeName) {
        return element.getAttribute(attributeName);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isActive() {
        return element.isSelected();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    /** {@inheritDoc} */
    @Override
    public Point getLocation() {
        return convert(element.getLocation());
    }

    /** {@inheritDoc} */
    @Override
    public Dimension getSize() {
        return convert(element.getSize());
    }

    /** {@inheritDoc} */
    @Override
    public List<Element> findElementsBy(final By byClause) {
        return convert(element.findElements(convert(byClause)));
    }

    /** {@inheritDoc} */
    @Override
    public Element findElementBy(final By byClause) {
        return convert(element.findElement(convert(byClause)));
    }

    /** {@inheritDoc} */
    @Override
    public SearchContext getImplementation() {
        return element;
    }
}
