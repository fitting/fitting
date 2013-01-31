package org.fitting;

import static org.fitting.FitnesseContainer.unset;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Fitnesse Context contains thead specific context. All relevant information should be stored here. */
public class FitnesseContext {

    private SeleniumConnector browserTarget;
    private Map<String, SeleniumWindow> seleniumWindows;
    private final SeleniumWindow defaultWindow;
    private SeleniumWindow activeWindow;

    /**
     * Constructor.
     * @param target The SeleniumConnector.
     */
    public FitnesseContext(final SeleniumConnector target) {
        if (target == null) {
            throw new WebDriverException("The given selenium connector is null.");
        }
        browserTarget = target;
        seleniumWindows = new HashMap<String, SeleniumWindow>();
        defaultWindow = new SeleniumWindow(target.getDriver().getWindowHandle(), null);
        activeWindow = defaultWindow;
        seleniumWindows.put(defaultWindow.getId(), defaultWindow);
    }

    /** Start the proxy. */
    public void startProxy() {
        browserTarget.startProxy();
    }

    /** Destroys the fitnesse context. */
    public void destroy() {
        browserTarget.destroy();
        unset();
    }

    /**
     * Gets the driver from the context.
     * @return driver The WebDriver.
     */
    public WebDriver getDriver() {
        return browserTarget.getDriver();
    }

    /**
     * Adds an ip domain mapping.
     * @param ip     The ip address.
     * @param domain The domain.
     */
    public void addIpDomainMapping(final String ip, final String domain) {
        browserTarget.addIpDomainMapping(domain, ip);
    }

    /**
     * Get the active Selenium window.
     * @return The active Selenium window.
     */
    public SeleniumWindow getActiveWindow() {
        return activeWindow;
    }

    /**
     * Close the active window, making the main window active. If the active
     * window is the last window, the context is properly teared down.
     */
    public void closeActiveWindow() {
        if (this.seleniumWindows.size() <= 1) {
            this.destroy();
        } else {
            final String activeWindowHandle = getActiveWindow().getId();
            final String parentId = getActiveWindow().getParentId();
            getDriver().close();
            seleniumWindows.remove(activeWindowHandle);
            activateWindow(parentId);
        }
    }


    /**
     * Create a new window.
     * @param url        The URL the window should go to.
     * @param makeActive Flag indicating if the new window should be the new active window.
     * @return The handle of the window.
     */
    public String createNewWindow(final String url, final boolean makeActive) {
        final SeleniumWindow window = SeleniumWindow.createNewWindow(url, getDriver());

        seleniumWindows.put(window.getId(), window);
        if (makeActive) {
            activateWindow(window.getId());
        }
        return window.getId();
    }

    /**
     * Get the handles of the windows.
     * @return handles The window handles.
     */
    public Set<String> getWindowHandles() {
        return seleniumWindows.keySet();
    }

    /**
     * Gets the default window.
     * @return
     */
    public void switchToMainWindow() {
        activateWindow(defaultWindow.getId());
    }

    /**
     * Indicates if the active window has the given id.
     * @param id The id.
     * @return <code>true</code> if window has the given id, else <code>false</code>.
     */
    public boolean isActiveWindow(final String id) {
        return activeWindow.getId().equals(id);
    }

    /**
     * Activates the window with the given id. If the window was not created
     * via FitNesse, a lookup is done in the list of open windows.
     * @param id The id of the window to activate.
     */
    public void activateWindow(final String id) {
        if (getWindowHandles().contains(id)) {
            activeWindow = seleniumWindows.get(id);
            getDriver().switchTo().window(activeWindow.getId());
        } else {
            activeWindow = SeleniumWindow.switchToWindow(id, getDriver());
            seleniumWindows.put(id, activeWindow);
        }

        try {
            new WebDriverWait(getDriver(), 2, 300)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(final WebDriver driver) {
                            return driver.getWindowHandles().contains(activeWindow.getId());
                        }
                    });
        } catch (TimeoutException e) {
            throw new WebDriverException("The window is still not active.");
        }
    }

}
