package org.fitting.selenium.fixtures;

import org.fitting.FittingConnector;
import org.fitting.FittingContainer;
import org.fitting.FittingException;
import org.fitting.fixture.FittingFixture;
import org.fitting.selenium.FittingSeleniumConnector;
import org.openqa.selenium.WebDriver;

/**
 * Base class for selenium fitting fixtures, providing all functionality of the {@link FittingFixture} as well.
 */
public abstract class FittingSeleniumFixture extends FittingFixture {
    /**
     * Get the active WebDriver instance.
     *
     * @return The WebDriver instance.
     */
    protected final WebDriver getWebDriver() {
        return getSeleniumConnector().getWebDriver();
    }

    /**
     * Get the active {@link FittingSeleniumFixture} instance.
     *
     * @return The instance.
     *
     * @throws FittingException When the current {@link FittingConnector} is not available or not a {@link FittingSeleniumConnector}.
     */
    protected final FittingSeleniumConnector getSeleniumConnector() {
        FittingSeleniumConnector seleniumConnector;
        FittingConnector connector = FittingContainer.get();
        if (FittingSeleniumConnector.class.isAssignableFrom(connector.getClass())) {
            seleniumConnector = (FittingSeleniumConnector) connector;
        } else {
            throw new FittingException(String.format("The connector %s (%s) is not a valid SeleniumConnector.", connector.getName(), connector.getClass().getName()));
        }
        return seleniumConnector;
    }
}
