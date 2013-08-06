package org.fitting.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Supported browsers.
 */
public enum Browser {
    /**
     * Microsoft Internet Explorer.
     */
    INTERNET_EXPLORER(new SeleniumActionCreator() {
        @Override
        public SeleniumAction create(WebDriver webDriver) {
            return new SeleniumAction(webDriver);
        }
    }, new DesiredCapabilitiesCreator() {
        @Override
        public DesiredCapabilities create() {
            return DesiredCapabilities.internetExplorer();
        }
    }, "ie", "explorer", "internet explorer"
    ),
    /**
     * Opera.
     */
    OPERA(new SeleniumActionCreator() {
        @Override
        public SeleniumAction create(WebDriver webDriver) {
            return new SeleniumAction(webDriver);
        }
    }, new DesiredCapabilitiesCreator() {
        @Override
        public DesiredCapabilities create() {
            return DesiredCapabilities.opera();
        }
    }, "opera"
    ),
    /**
     * Google Chrome.
     */
    CHROME(new SeleniumActionCreator() {
        @Override
        public SeleniumAction create(WebDriver webDriver) {
            return new SeleniumAction(webDriver);
        }
    }, new DesiredCapabilitiesCreator() {
        @Override
        public DesiredCapabilities create() {
            return DesiredCapabilities.chrome();
        }
    }, "chrome", "google chrome"
    ),
    /**
     * Mozilla Firefox.
     */
    FIREFOX(new SeleniumActionCreator() {
        @Override
        public SeleniumAction create(WebDriver webDriver) {
            return new SeleniumAction(webDriver);
        }
    }, new DesiredCapabilitiesCreator() {
        @Override
        public DesiredCapabilities create() {
            return DesiredCapabilities.firefox();
        }
    }, "firefox", "mozilla firefox", "mozzila"
    ),
    /**
     * PhantomJS headless webkit implementation.
     */
    PHANTOM_JS(new SeleniumActionCreator() {
        @Override
        public SeleniumAction create(WebDriver webDriver) {
            return new SeleniumAction(webDriver);
        }
    }, new DesiredCapabilitiesCreator() {
        @Override
        public DesiredCapabilities create() {
            return DesiredCapabilities.phantomjs();
            // TODO Add PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY capability
        }
    }, "phantomjs", "phantom js", "headless"
    ),
    /**
     * The default browser ({@link Browser#FIREFOX})
     */
    DEFAULT(Browser.FIREFOX);

    /**
     * The text aliases for the browser.
     */
    private final String[] aliases;
    /**
     * The creator for creating the browser specific {@link org.fitting.selenium.SeleniumAction}.
     */
    private final SeleniumActionCreator seleniumActionCreator;
    /**
     * The creator for creating the browser specific Selenium DesiredCapabilities.
     */
    private final DesiredCapabilitiesCreator desiredCapabilitiesCreator;

    /**
     * Create a new Browser instance.
     *
     * @param seleniumActionCreator      The creator for creating the browser specific {@link SeleniumAction}.
     * @param desiredCapabilitiesCreator The creator for creating the browser specific Selenium DesiredCapabilities.
     * @param aliases                    The text aliases for the browser.
     */
    private Browser(SeleniumActionCreator seleniumActionCreator, DesiredCapabilitiesCreator desiredCapabilitiesCreator, String... aliases) {
        this.seleniumActionCreator = seleniumActionCreator;
        this.aliases = aliases;
        this.desiredCapabilitiesCreator = desiredCapabilitiesCreator;
    }

    /**
     * Create a new Browser instance from an existing instance.
     *
     * @param original The instance to create the Browser from.
     */
    private Browser(Browser original) {
        this.aliases = original.aliases;
        this.seleniumActionCreator = original.seleniumActionCreator;
        this.desiredCapabilitiesCreator = original.desiredCapabilitiesCreator;
    }

    /**
     * Get the text aliases for the browser.
     *
     * @return The aliases.
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Create a browser specific {@link SeleniumAction}.
     *
     * @return The {@link SeleniumAction} instance.
     */
    public SeleniumAction createSeleniumAction(WebDriver webDriver) {
        return seleniumActionCreator.create(webDriver);
    }

    /**
     * Create the desired capabilities for the browser.
     *
     * @return The desired capabilities.
     */
    public DesiredCapabilities createDesiredCapabilities() {
        return desiredCapabilitiesCreator.create();
    }

    /**
     * Check if the given alias is a valid alias for the browser (case insensitive).
     *
     * @param alias The alias to check.
     *
     * @return <code>true</code> if the alias is a valid alias for the browser.
     */
    public boolean hasAlias(String alias) {
        boolean aliasForBrowser = false;
        if (!isEmpty(alias)) {
            for (String a : aliases) {
                if (alias.equalsIgnoreCase(a)) {
                    aliasForBrowser = true;
                    break;
                }
            }
        }
        return aliasForBrowser;
    }

    /**
     * Get the browser for a given alias. If no browser was found {@link Browser#DEFAULT} is returned.
     *
     * @param alias The alias.
     *
     * @return The browser.
     */
    public static Browser getBrowserForAlias(String alias) {
        Browser browser = DEFAULT;
        if (!isEmpty(alias)) {
            for (Browser b : Browser.values()) {
                if (b.hasAlias(alias)) {
                    browser = b;
                    break;
                }
            }
        }
        return browser;
    }

    /**
     * Creator for creating a browser specific {@link SeleniumAction}.
     */
    private static interface SeleniumActionCreator {
        /**
         * Create the browser specific {@link SeleniumAction}.
         *
         * @param webDriver The WebDriver instance to create the action for.
         *
         * @return The {@link SeleniumAction}.
         */
        SeleniumAction create(WebDriver webDriver);
    }

    /**
     * Creator for creating a browser specific DesiredCapabilities instance.
     */
    private static interface DesiredCapabilitiesCreator {
        /**
         * Create the browser specific DesiredCapabilities instance.
         *
         * @return The DesiredCapabilities.
         */
        DesiredCapabilities create();
    }
}
