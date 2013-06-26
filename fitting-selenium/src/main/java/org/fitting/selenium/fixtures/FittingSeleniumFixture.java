package org.fitting.selenium.fixtures;

import org.fitting.FittingConnector;
import org.fitting.FittingContainer;
import org.fitting.FittingException;
import org.fitting.fixture.FittingFixture;
import org.fitting.selenium.FittingSeleniumConnector;
import org.openqa.selenium.WebDriver;

public abstract class FittingSeleniumFixture extends FittingFixture {
    protected final WebDriver getWebDriver() {
        return getSeleniumConnector().getWebDriver();
    }

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
