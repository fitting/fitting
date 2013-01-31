package org.fitting;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import static org.fitting.WebDriverUtil.getElement;

/**
 * SearchContextProvider that uses the By mechanism to locate elements.
 * The By mechanism can be updated so that the search context of the implementing
 * provider can be changed at runtime.
 */
public abstract class UpdatableSearchContextProvider implements SearchContextProvider {
    private By by;

    /**
     * Constructor.
     * @param by The by mechanism used to locate elements.
     */
    public UpdatableSearchContextProvider(final By by) {
        this.by = by;
    }

    /**
     * Update the by mechanism.
     * @param by The by mechanism used to locate elements.
     */
    public void updateBy(final By by) {
        this.by = by;
    }

    @Override
    public SearchContext getSearchContext() {
            return WebDriverUtil.getElement(FitnesseContainer.get().getDriver(), by);
    }
}
