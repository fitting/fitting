package org.fitting;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.name;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** SeleniumWindow. */
public class SeleniumWindow {
    private final String id;
    private final String parentId;
    private By currentFrameSelector;
    private WebElement currentlySelectedElement;

    /**
     * Default constructor.
     * @param id The window id.
     */
    public SeleniumWindow(final String id, final String parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    /**
     * Gets the id.
     * @return id The id.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the parent id.
     * @return parentId The parent id.
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Select the frame with id.
     * @param id     The frame id.
     * @param driver The WebDriver.
     */
    public void selectFrameWithId(final String id, final WebDriver driver) {
        final By frameSelector = id(id);
        WebDriverUtil.switchToFrame(driver, frameSelector);
        this.currentFrameSelector = frameSelector;
    }

    /**
     * Select the frame with name.
     * @param name   The frame name.
     * @param driver The WebDriver.
     */
    public void selectFrameWithName(final String name, final WebDriver driver) {
        final By frameSelector = name(name);
        WebDriverUtil.switchToFrame(driver, frameSelector);
        this.currentFrameSelector = frameSelector;
    }

    /**
     * Select the main frame.
     * @param driver The WebDriver.
     */
    public void selectMainFrame(final WebDriver driver) {
        WebDriverUtil.switchToMainFrame(driver);
        this.currentFrameSelector = name("_top");
    }

    /**
     * Get the selected frame id.
     * @return The Selenium By clause with which the frame was selected.
     */
    public By getSelectedFrameSelectClause() {
        return this.currentFrameSelector;
    }

    /**
     * Set the selected web element.
     * @param element The selected WebElement.
     */
    public void setSelectedWebElement(final WebElement element) {
        this.currentlySelectedElement = element;
    }

    /**
     * Get the selected web element.
     * @return element The selected web element.
     */
    public WebElement getSelectedWebElement() {
        return this.currentlySelectedElement;
    }

    /**
     * Create a new window.
     * @param location  The location.
     * @param webDriver The WebDriver.
     * @param switchTo  The boolean indicating if the new window should be active.
     * @return window The newly created window.
     */
    public static SeleniumWindow createNewWindow(final String location, final WebDriver webDriver) {
        if (!JavascriptExecutor.class.isAssignableFrom(webDriver.getClass())) {
            throw new WebDriverException(
                    "The provided webdriver does not support javascript execution, a new window can not be created.");
        }
        final Set<String> currentHandles = webDriver.getWindowHandles();
        final String currentWindowHandle = webDriver.getWindowHandle();
        final JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("window.open('" + location + "')");

        final List<String> newHandles = new ArrayList<String>(webDriver.getWindowHandles());
        newHandles.removeAll(currentHandles);
        if (newHandles.size() != 1) {
            throw new WebDriverException("There were " + newHandles.size() + " windows created, while 1 was expected.");
        }
        final String newWindowHandle = newHandles.get(0);
        return new SeleniumWindow(newWindowHandle, currentWindowHandle);
    }
    
    public static SeleniumWindow switchToWindow(final String handle,
            final WebDriver webDriver) {
        final String currentWindowHandle = webDriver.getWindowHandle();
        try {
            webDriver.switchTo().window(handle);
        } catch (NoSuchWindowException e) {
            throw new WebDriverException(String.format(
                    "There is no window with the current id[%s] present.",
                    handle));
        }
        return new SeleniumWindow(webDriver.getWindowHandle(),
                currentWindowHandle);
    }
}
