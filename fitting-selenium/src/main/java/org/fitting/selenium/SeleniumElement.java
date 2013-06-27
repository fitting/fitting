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
