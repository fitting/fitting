package org.fitting.selenium;

import java.util.List;

import org.fitting.By;
import org.fitting.Element;
import org.fitting.FittingException;
import org.fitting.NoSuchElementException;
import org.fitting.SearchContext;
import org.openqa.selenium.WebDriver;

import static org.fitting.selenium.SeleniumDataTypeConverter.convert;

/**
 * {@link SearchContext} implementation for providing a Selenium WebDriver as SearchContext.
 */
public class WebDriverSearchContext implements SearchContext, SeleniumSearchContext {
    /**
     * The wrapped Selenium WebDriver.
     */
    private WebDriver webDriver;

    /**
     * Create a new {@link WebDriverSearchContext} instance.
     *
     * @param webDriver The WebDriver to wrap.
     */
    public WebDriverSearchContext(WebDriver webDriver) {
        if (webDriver == null) {
            throw new IllegalArgumentException("Tried to create a new WebDriverSearchContext for a null WebDriver.");
        }
        this.webDriver = webDriver;
    }

    @Override
    public List<Element> findElementsBy(By byClause) throws FittingException {
        return convert(webDriver.findElements(convert(byClause)));
    }

    @Override
    public Element findElementBy(By byClause) throws NoSuchElementException, FittingException {
        return convert(webDriver.findElement(convert(byClause)));
    }

    @Override
    public org.openqa.selenium.SearchContext getImplementation() {
        return webDriver;
    }
}
