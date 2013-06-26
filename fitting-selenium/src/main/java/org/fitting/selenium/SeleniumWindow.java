package org.fitting.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fitting.By;
import org.fitting.Dimension;
import org.fitting.Element;
import org.fitting.ElementContainer;
import org.fitting.FittingException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.fitting.selenium.SeleniumDataTypeConverter.convert;

public class SeleniumWindow implements ElementContainer, SeleniumSearchContext {
    private final String id;
    private final String parentId;
    private org.openqa.selenium.By currentFrameSelector;
    private WebDriver driver;

    /**
     * Default constructor.
     *
     * @param id The window id.
     */
    public SeleniumWindow(String id, String parentId, WebDriver driver) {
        this.driver = driver;
        this.id = id;
        this.parentId = parentId;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public boolean hasParent() {
        return !isEmpty(parentId);
    }

    @Override
    public boolean isRootContainer() {
        return true;
    }

    @Override
    public void refresh() {
        driver.navigate().refresh();
    }

    /**
     * Select the frame with id.
     *
     * @param id     The frame id.
     * @param driver The WebDriver.
     */
    public void selectFrameWithId(final String id, final WebDriver driver) {
        final org.openqa.selenium.By frameSelector = org.openqa.selenium.By.id(id);
        final WebElement element = driver.findElement(frameSelector);
        driver.switchTo().frame(element);
        this.currentFrameSelector = frameSelector;
    }

    /**
     * Select the frame with name.
     *
     * @param name   The frame name.
     * @param driver The WebDriver.
     */
    public void selectFrameWithName(final String name, final WebDriver driver) {
        final org.openqa.selenium.By frameSelector = org.openqa.selenium.By.name(name);
        final WebElement element = driver.findElement(frameSelector);
        driver.switchTo().frame(element);
        this.currentFrameSelector = frameSelector;
    }

    /**
     * Select the main frame.
     *
     * @param driver The WebDriver.
     */
    public void selectMainFrame(final WebDriver driver) {
        driver.switchTo().defaultContent();
        currentFrameSelector = org.openqa.selenium.By.name("_top");
    }

    /**
     * Get the selected frame id.
     *
     * @return The Selenium By clause with which the frame was selected.
     */
    public org.openqa.selenium.By getSelectedFrameSelectClause() {
        return this.currentFrameSelector;
    }

    /**
     * Create a new window.
     *
     * @param location  The location.
     * @param webDriver The WebDriver.
     *
     * @return window The newly created window.
     */
    public static SeleniumWindow createNewWindow(final String location, final WebDriver webDriver) {
        if (!JavascriptExecutor.class.isAssignableFrom(webDriver.getClass())) {
            throw new FittingException("The provided webdriver does not support javascript execution, a new window can not be created.");
        }
        final Set<String> currentHandles = webDriver.getWindowHandles();
        final String currentWindowHandle = webDriver.getWindowHandle();
        final JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("window.open('" + location + "')");

        final List<String> newHandles = new ArrayList<String>(webDriver.getWindowHandles());
        newHandles.removeAll(currentHandles);
        if (newHandles.size() != 1) {
            throw new FittingException("There were " + newHandles.size() + " windows created, while 1 was expected.");
        }
        final String newWindowHandle = newHandles.get(0);
        return new SeleniumWindow(newWindowHandle, currentWindowHandle, webDriver);
    }

    public static SeleniumWindow switchToWindow(final String handle, final WebDriver webDriver) {
        // TODO Revisit this code and probably rewrite. Let window creation/destruction be handled by the ElementContainerProvider.
        final String currentWindowHandle = webDriver.getWindowHandle();
        try {
            webDriver.switchTo().window(handle);
        } catch (NoSuchWindowException e) {
            throw new FittingException(String.format("There is no window with the current id[%s] present.", handle));
        }
        return new SeleniumWindow(webDriver.getWindowHandle(), currentWindowHandle, webDriver);
    }

    @Override
    public Dimension getSize() {
        return convert(driver.manage().window().getSize());
    }

    @Override
    public boolean isActive() {
        // TODO Implement me!
        return false;
    }

    @Override
    public void activate() {
        // TODO Implement me!
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public List<Element> findElementsBy(final By byClause) {
        return convert(driver.findElements(convert(byClause)));
    }

    @Override
    public Element findElementBy(final By byClause) {
        return convert(driver.findElement(convert(byClause)));
    }

    @Override
    public SearchContext getImplementation() {
        return driver;
    }
}
