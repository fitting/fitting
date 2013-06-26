package org.fitting.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fitting.ElementContainer;
import org.fitting.ElementContainerProvider;
import org.fitting.FittingException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SeleniumWindowProvider extends ElementContainerProvider {

    private WebDriver driver;

    public SeleniumWindowProvider(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    protected ElementContainer createContainer(final String uri) throws FittingException {
        if (!JavascriptExecutor.class.isAssignableFrom(driver.getClass())) {
            throw new FittingException("The provided webdriver does not support javascript execution, a new window can not be created.");
        }
        final Set<String> currentHandles = driver.getWindowHandles();
        final String currentWindowHandle = driver.getWindowHandle();
        final JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.open('" + uri + "')");

        final List<String> newHandles = new ArrayList<String>(driver.getWindowHandles());
        newHandles.removeAll(currentHandles);
        if (newHandles.size() != 1) {
            throw new FittingException("There were " + newHandles.size() + " windows created, while 1 was expected.");
        }
        final String newWindowHandle = newHandles.get(0);
        return new SeleniumWindow(newWindowHandle, currentWindowHandle, driver);
    }

    @Override
    protected ElementContainer createContainer(final String uri, final ElementContainer parent) throws FittingException {
        // TODO Implement me!
        return null;
    }
}
