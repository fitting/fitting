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

import org.fitting.*;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.fitting.selenium.SeleniumDataTypeConverter.convert;

/**
 * {@link org.fitting.Element} Selenium implementation for Selenium HTML elements.
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

    @Override
    public String getType() {
        return element.getTagName();
    }

    @Override
    public String getText() {
        return element.getText();
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

    @Override
    public void setValue(final String value) throws FittingException {
        sendKeys(value);
    }

    @Override
    public void setValueWithText(final String value) throws FittingException {
        sendKeys(value);
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

    @Override
    public boolean isInput() {
        // TODO Implement me!
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public List<Element> findElementsBy(final Selector selector) {
        return convert(element.findElements(convert(selector)));
    }

    /** {@inheritDoc} */
    @Override
    public Element findElementBy(final Selector selector) {
        return convert(element.findElement(convert(selector)));
    }

    /** {@inheritDoc} */
    @Override
    public void waitForElement(final Selector selector, final int timeout) throws NoSuchElementException {
        SeleniumUtil.waitForElement(getDriver(), this, selector, timeout);
    }

    /** {@inheritDoc} */
    @Override
    public void waitForElementWithContent(final Selector selector, final String content, final int timeout) throws NoSuchElementException {
        SeleniumUtil.waitForElementWithContent(getDriver(), this, selector, content, timeout);
    }

    /** {@inheritDoc} */
    @Override
    public SearchContext getImplementation() {
        return element;
    }

    /**
     * Get the WebDriver instance for the current thread.
     *
     * @return The WebDriver.
     *
     * @throw IllegalArgumentException When no WebDriver was available for the current thread.
     */
    private WebDriver getDriver() throws IllegalArgumentException {
        WebDriver driver;
        FittingConnector connector = FittingContainer.get();
        if (FittingSeleniumConnector.class.isAssignableFrom(connector.getClass())) {
            driver = ((FittingSeleniumConnector) connector).getWebDriver();
        } else {
            throw new IllegalArgumentException("No WebDriver instance available for current thread.");
        }
        return driver;
    }
}
