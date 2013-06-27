package org.fitting.selenium;

import java.util.List;

import org.fitting.By;
import org.fitting.Dimension;
import org.fitting.Element;
import org.fitting.ElementContainer;
import org.openqa.selenium.SearchContext;

/**
 * {@link ElementContainer} implementation for Selenium based HTML (i)frames.
 */
public class SeleniumFrame implements ElementContainer, SeleniumSearchContext {
    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getParentId() {
        return null;
    }

    @Override
    public boolean hasParent() {
        return false;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void activate() {
    }

    @Override
    public void refresh() {
    }

    @Override
    public boolean isRootContainer() {
        return false;
    }

    @Override
    public void close() {
    }

    @Override
    public List<Element> findElementsBy(final By byClause) {
        return null;
    }

    @Override
    public Element findElementBy(final By byClause) {
        return null;
    }

    @Override
    public SearchContext getImplementation() {
        return null;
    }
}
