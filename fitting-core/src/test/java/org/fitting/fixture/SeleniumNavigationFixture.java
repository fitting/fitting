package org.fitting.fixture;

import static org.fitting.WebDriverUtil.acceptAlertWindow;
import static org.fitting.WebDriverUtil.dismissAlertWindow;
import static org.fitting.WebDriverUtil.getAlertWindowText;
import static org.fitting.WebDriverUtil.isAlertWindowPresent;
import org.fitting.FitnesseContainer;
import org.fitting.WebDriverUtil;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * SeleniumNavigationFixture is a fixture that exposes all the WebDriverUtil navigation methods. These methods allow for
 * navigation interaction with the browser.
 */
public final class SeleniumNavigationFixture extends SeleniumFixture {

    /**
     * Opens the given url.
     * @param url The url.
     */
    public void open(final String url) {
        WebDriverUtil.open(getDriver(), url);
    }

    /**
     * Opens an url and adds a param and value to the query string.
     * @param url   The url.
     * @param param The param.
     * @param value The value.
     */
    public void openUrlWithParameterAndValue(final String url, final String param, final String value) {
        WebDriverUtil.openUrlWithParameterAndValue(getDriver(), url, param, value);
    }

    /** Refresh the page. */
    public void refresh() {
        WebDriverUtil.refresh(getDriver());
    }

    /**
     * Switches the focus to iframe with id.
     * @param id The id of the iframe.
     * @deprecated Please use {@link SeleniumNavigationFixture#switchToFrameWithId(String)} since it handles both frames
     *             and iFrames.
     */
    @Deprecated
    public void switchToIframeWithId(final String id) {
        FitnesseContainer.get().getActiveWindow().selectFrameWithId(id, getDriver());
    }

    /**
     * Switches the focus to the frame or iFrame with the given id.
     * @param id The id of the frame.
     */
    public void switchToFrameWithId(final String id) {
        FitnesseContainer.get().getActiveWindow().selectFrameWithId(id, getDriver());
    }

    /**
     * Switches the focus to iframe with name.
     * @param name The name of the iframe.
     * @deprecated Please use {@link SeleniumNavigationFixture#switchToFrameWithName(String)} since it handles both
     *             frames and iFrames.
     */
    @Deprecated
    public void switchToIframeWithName(final String name) {
        FitnesseContainer.get().getActiveWindow().selectFrameWithName(name, getDriver());
    }

    /**
     * Switches the focus to the frame or iFrame with the given name.
     * @param name The id of the frame.
     */
    public void switchToFrameWithName(final String name) {
        FitnesseContainer.get().getActiveWindow().selectFrameWithName(name, getDriver());
    }

    /** Switch to the main frame (thus stepping out of all iframes/frames). */
    public void switchToMainFrame() {
        FitnesseContainer.get().getActiveWindow().selectMainFrame(getDriver());
    }

    /**
     * Switch focus to a window.
     * @param id The id of the window.
     */
    public void switchToWindowWithId(final String id) {
        FitnesseContainer.get().activateWindow(id);
    }

    /** Switch to the main window. */
    public void switchToMainWindow() {
        FitnesseContainer.get().switchToMainWindow();
    }
   
    /** Accept the alert window (by click OK). */
    public void clickOkForAlertWindow() {
        if (isAlertWindowPresent(getDriver())) {
            acceptAlertWindow(getDriver());
        }
    }

    /** Dismiss the alert window, pressing cancel if possible. */
    public void clickCancelForAlertWindow() {
        if (isAlertWindowPresent(getDriver())) {
            dismissAlertWindow(getDriver());
        }
    }

    /**
     * Get the text from the alert window.
     * @return The text.
     */
    public String textFromAlertWindow() {
        return getAlertWindowText(getDriver());
    }

    /**
     * Set the browser in a state so it suppresses the print()
     * dialog from popping up.
     */
    public void expectPrintDialogToAppear() {
        WebDriver driver = getDriver();
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript("window.WebDriverPrintFunctionCalled = false; window.print = function () { window.WebDriverPrintFunctionCalled = true; }");
        }
    }
    
    /**
     * @return true if the window.print() function was called from
     *           the browser.
     *           Requires {@link #expectPrintDialogToAppear()}
     *            to be called beforehand.
     */
    public boolean printDialogWasCalled() {
        WebDriver driver = getDriver();
        if (driver instanceof JavascriptExecutor) {
            Object val = ((JavascriptExecutor) driver).executeScript("return window.WebDriverPrintFunctionCalled;");
            return (val instanceof Boolean) ? ((Boolean) val).booleanValue() : false;
        }
        return false;
    }
    
    /**
     * Gets the current url.
     * @return url The url.
     */
    public String currentUrlIs() {
        return getDriver().getCurrentUrl();
    }

    /**
     * Change the browser window size
     * @param dim dimensions (e.g. "800x600")
     */
    public void changeBrowserWindowSize(String dim) {
        String[] v = dim.split("x");
        int width = Integer.valueOf(v[0].trim()); int height = Integer.valueOf(v[1].trim());
        getDriver().manage().window().setSize(new Dimension(width, height));
    }

}
