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
import org.openqa.selenium.By;

/**
 * {@link By} implementation for Selenium.
 *
 * This class acts as a wrapper for a Selenium By-clause.
 */
public class SeleniumBy implements org.fitting.By {
    public static final String NAME_CLASS_NAME = "className";
    public static final String NAME_CSS_SELECTOR = "cssSelector";
    public static final String NAME_ID = "id";
    public static final String NAME_LINK_TEXT = "linkText";
    public static final String NAME_NAME = "name";
    public static final String NAME_PARTIAL_LINK_TEXT = "partialLinkText";
    public static final String NAME_TAG_NAME = "tagName";
    public static final String NAME_XPATH = "xpath";

    /**
     * The wrapped Selenium By-clause.
     */
    private final By byClause;
    /**
     * The name of the By-clause.
     */
    private final String name;

    /**
     * Create a new SeleniumBy.
     *
     * @param byClause The Selenium By-clause to wrap.
     */
    private SeleniumBy(String name, By byClause) {
        this.name = name;
        this.byClause = byClause;
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
     * Get the wrapped Selenium By-clause.
     *
     * @return The Selenium By-clause.
     */
    public By getSeleniumBy() {
        return byClause;
    }

    /**
     * Get the name of the By-clause.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Create a By-clause for selecting elements based on their class name.
     *
     * @param className The class name.
     *
     * @return The {@link SeleniumBy} implementation.
     */
    public static SeleniumBy byClassName(String className) {
        return new SeleniumBy(NAME_CLASS_NAME, By.className(className));
    }

    /**
     * Create a By-clause for selecting elements based on a css selector.
     *
     * @param cssSelector The css selector.
     *
     * @return The {@link SeleniumBy} implementation.
     */
    public static SeleniumBy byCssSelector(String cssSelector) {
        return new SeleniumBy(NAME_CSS_SELECTOR, By.cssSelector(cssSelector));
    }

    /**
     * Create a By-clause for selecting elements based on their class id.
     *
     * @param id The id.
     *
     * @return The {@link SeleniumBy} implementation.
     */
    public static SeleniumBy byId(String id) {
        return new SeleniumBy(NAME_ID, By.id(id));
    }

    /**
     * Create a By-clause for selecting elements based on their link text.
     *
     * @param linkText The link text.
     *
     * @return The {@link SeleniumBy} implementation.
     */
    public static SeleniumBy byLinkText(String linkText) {
        return new SeleniumBy(NAME_LINK_TEXT, By.linkText(linkText));
    }

    /**
     * Create a By-clause for selecting elements based on their name.
     *
     * @param name The name.
     *
     * @return The {@link SeleniumBy} implementation.
     */
    public static SeleniumBy byName(String name) {
        return new SeleniumBy(NAME_NAME, By.name(name));
    }

    /**
     * Create a By-clause for selecting elements based on a partial link text.
     *
     * @param partialLinkText The partial link text.
     *
     * @return The {@link SeleniumBy} implementation.
     */
    public static SeleniumBy byPartialLinkText(String partialLinkText) {
        return new SeleniumBy(NAME_PARTIAL_LINK_TEXT, By.partialLinkText(partialLinkText));
    }

    /**
     * Create a By-clause for selecting elements based on their tag name.
     *
     * @param tagName The tag name.
     *
     * @return The {@link SeleniumBy} implementation.
     */
    public static SeleniumBy byTagName(String tagName) {
        return new SeleniumBy(NAME_TAG_NAME, By.tagName(tagName));
    }

    /**
     * Create a By-clause for selecting elements based on a xpath.
     *
     * @param xpath The XPath.
     *
     * @return The {@link SeleniumBy} implementation.
     */
    public static SeleniumBy byXPath(String xpath) {
        return new SeleniumBy(NAME_XPATH, By.xpath(xpath));
    }
}
