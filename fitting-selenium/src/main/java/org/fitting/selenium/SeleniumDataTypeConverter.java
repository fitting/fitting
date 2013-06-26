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

public class SeleniumDataTypeConverter {
    private SeleniumDataTypeConverter() {
    }

    public static Point convert(org.openqa.selenium.Point point) {
        return new Point(point.getX(), point.getY());
    }

    public static Dimension convert(org.openqa.selenium.Dimension dimension) {
        return new Dimension(dimension.getWidth(), dimension.getHeight());
    }

    public static Element convert(WebElement element) {
        return new SeleniumElement(element);
    }

    public static WebElement convert(Element element) {
        if (!WebElement.class.isAssignableFrom(element.getClass())) {
            throw new IllegalArgumentException(format("Can't convert Element of type %s to a WebElement", element.getClass()));
        }
        return WebElement.class.cast(element);
    }

    public static org.openqa.selenium.SearchContext convert(SearchContext searchContext) {
        if (!SeleniumSearchContext.class.isAssignableFrom(searchContext.getClass())) {
            throw new IllegalArgumentException(format("Can't convert SearchContext of type %s to a Selenium SearchContext", searchContext.getClass()));
        }
        return SeleniumSearchContext.class.cast(searchContext).getImplementation();
    }

    public static List<Element> convert(List<WebElement> elements) {
        List<Element> fittingElements = new ArrayList<Element>(elements.size());
        for (WebElement e : elements) {
            fittingElements.add(convert(e));
        }
        return fittingElements;
    }

    public static By convert(org.openqa.selenium.By byClause) {
        // TODO Implement me!
        return null;
    }

    public static org.openqa.selenium.By convert(By byClause) {
        // TODO Implement me!
        return null;
    }
}
