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

import org.fitting.Element;
import org.fitting.SearchContext;
import org.fitting.Selector;
import org.openqa.selenium.By;

/**
 * {@link org.openqa.selenium.By} implementation for Selenium.
 * This class acts as a wrapper for a Selenium selector.
 */
public class SeleniumSelector implements Selector {
    public static final String CLASS_NAME = "className";
    public static final String CSS_SELECTOR = "cssSelector";
    public static final String ID = "id";
    public static final String LINK_TEXT = "linkText";
    public static final String NAME = "name";
    public static final String PARTIAL_LINK_TEXT = "partialLinkText";
    public static final String TAG_NAME = "tagName";
    public static final String XPATH = "xpath";

    /** The wrapped Selenium selector. */
    private final By selector;
    /** The name of the selector. */
    private final String name;

    /**
     * Create a new SeleniumBy.
     *
     * @param selector The Selenium selector to wrap.
     */
    private SeleniumSelector(String name, By selector) {
        this.name = name;
        this.selector = selector;
    }

    @Override
    public List<Element> findElements(final SearchContext context) {
        return context.findElementsBy(this);
    }

    @Override
    public Element findElement(final SearchContext context) {
        return context.findElementBy(this);
    }

    /**
     * Get the wrapped Selenium selector.
     *
     * @return The Selenium selector.
     */
    public By getSeleniumBy() {
        return selector;
    }

    /**
     * Get the name of the selector.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return selector.toString();
    }

    /**
     * Create a selector for selecting elements based on their class name.
     *
     * @param className The class name.
     *
     * @return The {@link org.fitting.selenium.SeleniumSelector} implementation.
     */
    public static SeleniumSelector byClassName(String className) {
        return new SeleniumSelector(CLASS_NAME, By.className(className));
    }

    /**
     * Create a selector for selecting elements based on a css selector.
     *
     * @param cssSelector The css selector.
     *
     * @return The {@link org.fitting.selenium.SeleniumSelector} implementation.
     */
    public static SeleniumSelector byCssSelector(String cssSelector) {
        return new SeleniumSelector(CSS_SELECTOR, By.cssSelector(cssSelector));
    }

    /**
     * Create a selector for selecting elements based on their class id.
     *
     * @param id The id.
     *
     * @return The {@link org.fitting.selenium.SeleniumSelector} implementation.
     */
    public static SeleniumSelector byId(String id) {
        return new SeleniumSelector(ID, By.id(id));
    }

    /**
     * Create a selector for selecting elements based on their link text.
     *
     * @param linkText The link text.
     *
     * @return The {@link org.fitting.selenium.SeleniumSelector} implementation.
     */
    public static SeleniumSelector byLinkText(String linkText) {
        return new SeleniumSelector(LINK_TEXT, By.linkText(linkText));
    }

    /**
     * Create a selector for selecting elements based on their name.
     *
     * @param name The name.
     *
     * @return The {@link org.fitting.selenium.SeleniumSelector} implementation.
     */
    public static SeleniumSelector byName(String name) {
        return new SeleniumSelector(NAME, By.name(name));
    }

    /**
     * Create a selector for selecting elements based on a partial link text.
     *
     * @param partialLinkText The partial link text.
     *
     * @return The {@link org.fitting.selenium.SeleniumSelector} implementation.
     */
    public static SeleniumSelector byPartialLinkText(String partialLinkText) {
        return new SeleniumSelector(PARTIAL_LINK_TEXT, By.partialLinkText(partialLinkText));
    }

    /**
     * Create a selector for selecting elements based on their tag name.
     *
     * @param tagName The tag name.
     *
     * @return The {@link org.fitting.selenium.SeleniumSelector} implementation.
     */
    public static SeleniumSelector selectorName(String tagName) {
        return new SeleniumSelector(TAG_NAME, By.tagName(tagName));
    }

    /**
     * Create a selector for selecting elements based on a xpath.
     *
     * @param xpath The XPath.
     *
     * @return The {@link org.fitting.selenium.SeleniumSelector} implementation.
     */
    public static SeleniumSelector byXPath(String xpath) {
        return new SeleniumSelector(XPATH, By.xpath(xpath));
    }
}
