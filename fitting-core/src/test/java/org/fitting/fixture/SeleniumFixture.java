package org.fitting.fixture;

import org.fitting.FitnesseContainer;
import org.fitting.SearchContextProvider;
import org.fitting.SearchContextProviders;
import org.fitting.WebDriverSearchContextProvider;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** SeleniumFixture. */
public class SeleniumFixture {
    /** The search context providers to use. */
    private final ThreadLocal<SearchContextProviders> providers = new ThreadLocal<SearchContextProviders>();

    /** Create a new selenium fixture, using WebDriver (and thus the window root) as search context. */
    public SeleniumFixture() {
        this(new WebDriverSearchContextProvider());

    }

    /**
     * Create a new selenium fixture, using a custom search context provider for providing the search context.
     * @param searchContextProviders The SearchContextProviders.
     */
    public SeleniumFixture(final SearchContextProvider... searchContextProviders) {
        providers.set(new SearchContextProviders(searchContextProviders));
    }

    /**
     * Get the web driver for the fixture.
     * <p/>
     * For searches (e.g. getElement() and getElements()) use
     * {@link org.fitting.fixture.SeleniumFixture#getSearchContext(String)}.
     * @return The web driver.
     * @see org.fitting.fixture.SeleniumFixture#getSearchContext(String)
     */
    protected static WebDriver getDriver() {
        return FitnesseContainer.get().getDriver();
    }

    /**
     * Get the search context by its id.
     * <p/>
     * The search context is either a WebElement or the WebDriver.
     * @return The search context or null if there is no search context for the given id.
     */
    protected final SearchContext getSearchContext() {
        return getSearchContext(WebDriverSearchContextProvider.ID);
    }

    /**
     * Get the search context provider by its id.
     * <p/>
     * @param id The id of the search context.
     * @return The search context provider  or null if there is no search context provider for the given id.
     */
    protected final SearchContextProvider getSearchContextProvider(final String id) {
        return providers.get().getSearchContextProvider(id);
    }

    /**
     * Get the search context by its id.
     * <p/>
     * The search context is either a WebElement or the WebDriver.
     * @param id The id of the search context.
     * @return The search context or null if there is no search context for the given id.
     */
    protected final SearchContext getSearchContext(final String id) {
        SearchContext context = null;
        if (providers.get().isSearchContextProviderKnown(id)) {
            final SearchContextProvider provider = providers.get().getSearchContextProvider(id);
            context = provider.getSearchContext();
        }
        return context;
    }

    /**
     * Get the selected web element for the currently active window.
     * @return The selected web element.
     */
    protected WebElement getSelectedElement() {
        return FitnesseContainer.get().getActiveWindow().getSelectedWebElement();
    }

    /**
     * Set the selected web element for the currently active window.
     * @param element The selected web element.
     */
    protected void setSelectedElement(final WebElement element) {
        FitnesseContainer.get().getActiveWindow().setSelectedWebElement(element);
    }
}
