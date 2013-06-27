package org.fitting.selenium;

import java.util.ArrayList;
import java.util.List;

import org.fitting.By;
import org.fitting.Dimension;
import org.fitting.Element;
import org.fitting.Point;
import org.fitting.SearchContext;
import org.openqa.selenium.WebElement;

import static java.lang.String.format;

/**
 * Converter for converting Selenium data types to Fitting data types.
 */
public class SeleniumDataTypeConverter {
    /**
     * Private constructor to prevent instantiation.
     */
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
     * Convert a Selenium By to a Fitting {@link By}.
     *
     * <p>
     * TODO This might be removed due to {@link SeleniumByProvider}.
     * </p>
     *
     * @param byClause The By.
     *
     * @return The {@link By}.
     */
    public static By convert(org.openqa.selenium.By byClause) {
        // TODO Implement me!
        return null;
    }

    /**
     * Convert a Fitting {@link By} to a Selenium By.
     *
     * <p>
     * TODO This might be removed due to {@link SeleniumByProvider}.
     * </p>
     *
     * @param byClause The {@link By}.
     *
     * @return The By.
     */
    public static org.openqa.selenium.By convert(By byClause) {
        // TODO Implement me!
        return null;
    }
}