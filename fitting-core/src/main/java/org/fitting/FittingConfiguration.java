/*
 * Licensed to the Fitting Project under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Fitting Project licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.fitting;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * General fitting configuration.
 *
 * @author Barre Dijkstra
 * @since 1.0
 */
public class FittingConfiguration {
    /** The logging instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FittingConfiguration.class);
    /** The name of the properties file with the default values. */
    private static final String DEFAULT_CONFIGURATION_PROPERTIES = "fitting.default.properties";
    /** The name of the properties file that gets search for on the classpath. */
    private static final String CONFIGURATION_PROPERTIES = "fitting.properties";
    /** The key for the default test system. */
    private static final String KEY_SYSTEM_DEFAULT = "fitting.system.default";
    /** The key for the test system. */
    private static final String KEY_SYSTEM = "fitting.system";
    /** The singleton instance. */
    private static FittingConfiguration instance;
    /** The configuration. */
    private CompositeConfiguration configuration;


    /**
     * Create a new instance.
     * <p>
     * Private to prevent external instantiation.
     * </p>
     */
    private FittingConfiguration() {
        configuration = new CompositeConfiguration();
        loadDefaults();
    }

    /**
     * Get the configuration instance.
     *
     * @return The instance.
     */
    public static FittingConfiguration getInstance() {
        // Don't worry about race conditions, worst case scenario the configuration gets read twice.
        if (instance == null) {
            instance = new FittingConfiguration();
        }
        return instance;
    }

    /**
     * Load the default configuration.
     *
     * @throws FittingException When the properties could not be loaded.
     */
    private void loadDefaults() throws FittingException {
        try {
            configuration.addConfiguration(new PropertiesConfiguration(DEFAULT_CONFIGURATION_PROPERTIES));
        } catch (ConfigurationException e) {
            throw new FittingException("Unable to load default configuration properties file [" + DEFAULT_CONFIGURATION_PROPERTIES + "]", e);
        }
    }

    /** Load the configuration from the default properties. */
    private void loadProperties() {
        try {
            configuration.addConfiguration(new PropertiesConfiguration(CONFIGURATION_PROPERTIES));
        } catch (ConfigurationException e) {
            LOGGER.warn("No properties found with the name " + CONFIGURATION_PROPERTIES + " or properties file could not be loaded", e);
        }
    }

    /**
     * Get the configured system connector class.
     *
     * @return The system connector.
     *
     * @throws FittingException When the connector could not be loaded.
     */
    public Class<? extends FittingConnector> getSystemConnector() throws FittingException {
        // TODO Determine if it's worth loading to class every time to allow injection of more configuration or that we need to lazy load it.
        String className = configuration.getString(KEY_SYSTEM, configuration.getString(KEY_SYSTEM_DEFAULT, null));
        if (isEmpty(className)) {
            throw new FittingException("No system connector configured with the " + KEY_SYSTEM + " property.");
        }
        Class<?> cls;
        try {
            cls = FittingConfiguration.class.getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new FittingException("Could not load configured system connector" + className, e);
        }
        if (!FittingConnector.class.isAssignableFrom(cls)) {
            throw new FittingException("Configured system connector " + className + " does not implement FittingConnector.");
        }
        LOGGER.debug("Loaded system connector {}", className);
        return (Class<? extends FittingConnector>) cls;
    }
}
