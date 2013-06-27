package org.fitting.selenium;

import org.fitting.ByProvider;
import org.fitting.ElementContainerProvider;
import org.fitting.FittingAction;
import org.fitting.FittingConnector;
import org.fitting.FittingException;
import org.openqa.selenium.WebDriver;

/**
 * Selenium implementation for the {@link FittingConnector}, providing access for Fitting to test using the Selenium framework.
 */
public class FittingSeleniumConnector implements FittingConnector {
    /**
     * The implementation name.
     */
    private static final String CONNECTOR_NAME = "Fitting-Selenium";
    /**
     * The browser to use.
     */
    private Browser browser;

    /**
     * Create a new FittingSeleniumConnect.
     *
     * @param browser The browser to use.
     */
    public FittingSeleniumConnector(Browser browser) {
        if (browser == null) {
            throw new FittingException("The given selenium connector is null.");
        }
        this.browser = browser;
    }

    @Override
    public String getName() {
        return CONNECTOR_NAME;
    }

    @Override
    public ByProvider getByProvider() {
        return new SeleniumByProvider();
    }

    @Override
    public FittingAction getFittingAction() {
        return new FittingSeleniumAction(browser.getWebDriver());
    }

    @Override
    public ElementContainerProvider getElementContainerProvider() {
        return new SeleniumWindowProvider(browser.getWebDriver());
    }

    @Override
    public void destroy() {
        browser.destroy();
    }

    public WebDriver getWebDriver() {
        return browser.getWebDriver();
    }
}
