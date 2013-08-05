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
import org.fitting.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/** Converter for converting Selenium data types to Fitting data types. */
public class SeleniumDataTypeConverter {
    /** Private constructor to prevent instantiation. */
    private SeleniumDataTypeConverter() {
    }

    /**
     * Convert a Selenium point to a Fitting {@link Point}.
     *
     * @param point The Selenium point.
     *
     * @return The {@link Point}.
     */
    public static Point convert(org.openqa.selenium.Point point) {
        return new Point(point.getX(), point.getY());
    }

    /**
     * Convert a Selenium Dimension to a Fitting {@link Dimension}.
     *
     * @param dimension The Selenium Dimension.
     *
     * @return The {@link Dimension}.
     */
    public static Dimension convert(org.openqa.selenium.Dimension dimension) {
        return new Dimension(dimension.getWidth(), dimension.getHeight());
    }

    /**
     * Convert a Selenium WebElement to a Fitting {@link Element}.
     *
     * @param element The web element.
     *
     * @return The {@link Element}.
     */
    public static Element convert(WebElement element) {
        return new SeleniumElement(element);
    }

    /**
     * Convert a Fitting {@link Element} to a WebElement.
     *
     * @param element The {@link Element}.
     *
     * @return The WebElement.
     *
     * @throws IllegalArgumentException When the element could not be converted.
     */
    public static WebElement convert(Element element) {
        if (!WebElement.class.isAssignableFrom(element.getClass())) {
            throw new IllegalArgumentException(format("Can't convert Element of type %s to a WebElement", element.getClass()));
        }
        return WebElement.class.cast(element);
    }

    /**
     * Convert a Fitting {@link SearchContext} to a Selenium SearchContext.
     *
     * @param searchContext The {@link SearchContext}.
     *
     * @return The Selenium SearchContext.
     */
    public static org.openqa.selenium.SearchContext convert(SearchContext searchContext) {
        if (!SeleniumSearchContext.class.isAssignableFrom(searchContext.getClass())) {
            throw new IllegalArgumentException(format("Can't convert SearchContext of type %s to a Selenium SearchContext", searchContext.getClass()));
        }
        return SeleniumSearchContext.class.cast(searchContext).getImplementation();
    }

    /**
     * Convert a List of Selenium WebElements to a list of Fitting {@link Element}.
     *
     * @param elements The WebElements.
     *
     * @return The {@link Element} list.
     */
    public static List<Element> convert(List<WebElement> elements) {
        List<Element> fittingElements = new ArrayList<Element>(elements.size());
        for (WebElement e : elements) {
            fittingElements.add(convert(e));
        }
        return fittingElements;
    }

    /**
     * Convert a Fitting {@link By} to a Selenium by-clause.
     *
     * @param byClause The Fitting {@link By} to convert.
     *
     * @return The Selenium by-clause.
     */
    public static org.openqa.selenium.By convert(By byClause) throws IllegalArgumentException {
        if (!SeleniumBy.class.isAssignableFrom(byClause.getClass())) {
            throw new IllegalArgumentException(format("By-clause %s is not a valid Selenium by-clause.", byClause.getClass().getName()));
        }
        return SeleniumBy.class.cast(byClause).getSeleniumBy();
    }
}
