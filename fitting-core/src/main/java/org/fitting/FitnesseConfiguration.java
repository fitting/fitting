package org.fitting;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Properties;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.openqa.selenium.Platform.extractFromSysProperty;
import static org.openqa.selenium.remote.CapabilityType.ACCEPT_SSL_CERTS;
import static org.openqa.selenium.remote.CapabilityType.VERSION;

/**
 * Fitnesse Configuration. The configuration is a singleton that provides access to Fitnesse
 * properties that are not runtime defined but are read from property files and are consistent until restart of
 * the fitnesse wiki.
 */
public final class FitnesseConfiguration {
    private String defaultServerHost;
    private String defaultServerPort;
    private String defaultPlatform;
    private Boolean defaultModeJs;

    /** Constructor. */
    private FitnesseConfiguration() {
        try {
            Configuration configuration = new PropertiesConfiguration("fitnesse.properties");
            defaultServerHost = configuration.getString("selenium.server.ip");
            defaultServerPort = configuration.getString("selenium.server.port");
            defaultPlatform = configuration.getString("selenium.platform");
            defaultModeJs = configuration.getBoolean("mode.js");

            if (configuration.containsKey("http.proxyHost") && configuration.containsKey("http.proxyPort")) {
                final Properties systemProperties = System.getProperties();
                systemProperties.put("http.proxyHost", configuration.getString("http.proxyHost"));
                systemProperties.put("http.proxyPort", configuration.getString("http.proxyPort"));
                if (configuration.containsKey("http.proxyUser") && configuration.containsKey("http.proxyPassword")) {
                    systemProperties.put("http.proxyUser", configuration.getString("http.proxyUser"));
                    systemProperties.put("http.proxyPassword", configuration.getString("http.proxyPassword"));
                }
            }
        } catch (ConfigurationException e) {
            throw new IllegalArgumentException("Could not load fitnesse.properties", e);
        }
    }

    /** Holder for the FitnesseConfiguration. */
    private static final class FitnesseConfigurationHolder {
        public static final FitnesseConfiguration INSTANCE = new FitnesseConfiguration();

        /** Default contstructor. */
        private FitnesseConfigurationHolder() {
        }
    }

    /**
     * Get the instance of the fitnesse configuration.
     * @return configuration The FitnesseConfiguration.
     */
    public static FitnesseConfiguration instance() {
        return FitnesseConfigurationHolder.INSTANCE;
    }

    /**
     * Gets the default server host.
     * @return defaultHost The default host.
     */
    public String getDefaultServerHost() {
        return defaultServerHost;
    }

    /**
     * Get the default server port.
     * @return defaultPort The default port.
     */
    public String getDefaultServerPort() {
        return defaultServerPort;
    }

    /**
     * Gets the desired capabilities with the default setting for the enablement of JavaScript.
     * @param platform The platform to run on.
     * @param browser  The browser to open.
     * @param version  The browser version.
     * @return capabilities The DesiredCapabilities.
     */
    public DesiredCapabilities getCapabilities(final String platform, final String browser, final String version) {
        return getCapabilities(platform, browser, version, defaultModeJs);
    }

    /**
     * Gets the desired capabilities.
     * @param platform          The platform to run on.
     * @param browser           The browser to open.
     * @param version           The browser version.
     * @param javascriptEnabled Flag indicating if JavaScript should be enabled.
     * @return capabilities The DesiredCapabilities.
     */
    public DesiredCapabilities getCapabilities(final String platform, final String browser, final String version, final boolean javascriptEnabled) {
        final String osName = isEmpty(platform) ? defaultPlatform : platform;

        final DesiredCapabilities capabilities;
        if ("ie".equals(browser)) {
            capabilities = DesiredCapabilities.internetExplorer();
        } else if ("chrome".equals(browser)) {
            capabilities = DesiredCapabilities.chrome();
        } else if ("opera".equals(browser)) {
            capabilities = DesiredCapabilities.opera();
        } else {
            capabilities = DesiredCapabilities.firefox();
        }

        capabilities.setJavascriptEnabled(javascriptEnabled);
        capabilities.setCapability(ACCEPT_SSL_CERTS, true);
        capabilities.setPlatform(extractFromSysProperty(osName));

        if (version != null) {
            capabilities.setCapability(VERSION, version);
        }
        return capabilities;
    }
}
