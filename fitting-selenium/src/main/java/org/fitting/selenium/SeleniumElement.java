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
import org.fitting.Point;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import static org.fitting.selenium.SeleniumDataTypeConverter.convert;

/**
 * {@link Element} Selenium implementation for HTML elements.
 */
public class SeleniumElement implements Element, SeleniumSearchContext {
    /**
     * The actual implementing web element.
     */
    private final WebElement element;

    /**
     * Create a new SeleniumElement.
     *
     * @param element The implementing Selenium WebElement.
     */
    public SeleniumElement(WebElement element) {
        this.element = element;
    }

    @Override
    public String getName() {
        return element.getTagName();
    }

    @Override
    public String getValue() {
        return element.getText();
    }

    @Override
    public void click() {
        element.click();
    }

    @Override
    public void sendKeys(final CharSequence... characters) {
        element.sendKeys(characters);
    }

    @Override
    public void clear() {
        element.clear();
    }

    @Override
    public String getAttributeValue(String attributeName) {
        return element.getAttribute(attributeName);
    }

    @Override
    public boolean isActive() {
        return element.isSelected();
    }

    @Override
    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return convert(element.getLocation());
    }

    @Override
    public Dimension getSize() {
        return convert(element.getSize());
    }

    @Override
    public List<Element> findElementsBy(final By byClause) {
        return convert(element.findElements(convert(byClause)));
    }

    @Override
    public Element findElementBy(final By byClause) {
        return convert(element.findElement(convert(byClause)));
    }

    @Override
    public SearchContext getImplementation() {
        return element;
    }
}
