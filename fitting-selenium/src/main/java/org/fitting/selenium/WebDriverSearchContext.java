package org.fitting.selenium;

import java.util.List;

import org.fitting.*;
import org.openqa.selenium.WebDriver;

import static org.fitting.selenium.SeleniumDataTypeConverter.convert;

/** {@link org.fitting.SearchContext} implementation for providing a Selenium WebDriver as SearchContext. */
public class WebDriverSearchContext implements SearchContext, SeleniumSearchContext {
    /** The wrapped Selenium WebDriver. */
    private WebDriver webDriver;

    /**
     * Create a new {@link org.fitting.selenium.WebDriverSearchContext} instance.
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
    public List<Element> findElementsBy(Selector selector) throws FittingException {
        return convert(webDriver.findElements(convert(selector)));
    }

    @Override
    public Element findElementBy(Selector selector) throws NoSuchElementException, FittingException {
        return convert(webDriver.findElement(convert(selector)));
    }

    @Override
    public void waitForElement(final Selector selector, final int timeout) throws NoSuchElementException {
        SeleniumUtil.waitForElement(this.webDriver, this, selector, timeout);
    }

    @Override
    public org.openqa.selenium.SearchContext getImplementation() {
        return webDriver;
    }
}
