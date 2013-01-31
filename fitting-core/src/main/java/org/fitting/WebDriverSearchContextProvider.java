package org.fitting;

import org.openqa.selenium.SearchContext;

/** Base implementation of the SearchContextProvider, returning the WebDriver as search context. */
public final class WebDriverSearchContextProvider implements SearchContextProvider {
    /** The id of the search context provider. */
    public static final String ID = "driver";

    /** {@inheritDoc} */
    @Override
    public String getId() {
        return ID;
    }

    /** {@inheritDoc} */
    @Override
    public SearchContext getSearchContext() {
        return FitnesseContainer.get().getDriver();
    }
}