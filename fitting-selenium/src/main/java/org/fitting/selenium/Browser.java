package org.fitting.selenium;

import org.openqa.selenium.remote.DesiredCapabilities;

import static org.apache.commons.lang.StringUtils.isEmpty;

/** Supported browsers. */
public enum Browser {
    /** Microsoft Internet Explorer. */
    INTERNET_EXPLORER(new DesiredCapabilitiesCreator() {
        @Override
        public DesiredCapabilities create() {
            return DesiredCapabilities.internetExplorer();
        }
    }, "ie", "explorer", "internet explorer"
    ),
    /** Opera. */
    OPERA(new DesiredCapabilitiesCreator() {
        @Override
        public DesiredCapabilities create() {
            return DesiredCapabilities.opera();
        }
    }, "opera"
    ),
    /** Google Chrome. */
    CHROME(new DesiredCapabilitiesCreator() {
        @Override
        public DesiredCapabilities create() {
            return DesiredCapabilities.chrome();
        }
    }, "chrome", "google chrome"
    ),
    /** Mozilla Firefox. */
    FIREFOX(new DesiredCapabilitiesCreator() {
        @Override
        public DesiredCapabilities create() {
            return DesiredCapabilities.firefox();
        }
    }, "firefox", "mozilla firefox", "mozzila"
    ),
    /** PhantomJS headless webkit implementation. */
    PHANTOM_JS(new DesiredCapabilitiesCreator() {
        @Override
        public DesiredCapabilities create() {
            return DesiredCapabilities.phantomjs();
            // TODO Add PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY capability
        }
    }, "phantomjs", "phantom js", "headless"
    ),
    /** The default browser ({@link org.fitting.selenium.Browser#FIREFOX}) */
    DEFAULT(Browser.FIREFOX);

    /** The text aliases for the browser. */
    private final String[] aliases;
    /** The creator for creating the browser specific Selenium DesiredCapabilities. */
    private final DesiredCapabilitiesCreator desiredCapabilitiesCreator;

    /**
     * Create a new Browser instance.
     *
     * @param desiredCapabilitiesCreator The creator for creating the browser specific Selenium DesiredCapabilities.
     * @param aliases                    The text aliases for the browser.
     */
    private Browser(DesiredCapabilitiesCreator desiredCapabilitiesCreator, String... aliases) {
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
     * Get the browser for a given alias. If no browser was found {@link org.fitting.selenium.Browser#DEFAULT} is returned.
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

    /** Creator for creating a browser specific DesiredCapabilities instance. */
    private static interface DesiredCapabilitiesCreator {
        /**
         * Create the browser specific DesiredCapabilities instance.
         *
         * @return The DesiredCapabilities.
         */
        DesiredCapabilities create();
    }
}
