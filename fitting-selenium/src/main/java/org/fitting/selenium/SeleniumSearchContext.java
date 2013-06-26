package org.fitting.selenium;

import org.openqa.selenium.SearchContext;

/**
 * Selenium search context proxy marker interface.
 */
public interface SeleniumSearchContext {
    /**
     * Get the actual search context implementation.
     *
     * @return The search context.
     */
    SearchContext getImplementation();
}
