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

package org.fitting.selenium;

import org.openqa.selenium.server.RemoteControlConfiguration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manager for starting and stopping Selenium server instances.
 *
 * @author Barre Dijkstra
 * @since 1.0
 */
public class SeleniumServerManager {
    /** The default Selenium server port. */
    public static final int DEFAULT_PORT = RemoteControlConfiguration.DEFAULT_PORT;
    /** The singleton instance. */
    private static SeleniumServerManager instance;
    /** Map containing all server managers, indexed by port. */
    private final Map<Integer, SeleniumServerInstance> servers;

    /**
     * Create a new instance.
     * <p>Private to prevent external instantiation.</p>
     *
     * @see org.fitting.selenium.SeleniumServerManager#getInstance()
     */
    private SeleniumServerManager() {
        this.servers = new HashMap<Integer, SeleniumServerInstance>();
    }

    /**
     * Get the singleton instance of the {@link org.fitting.selenium.SeleniumServerManager}.
     *
     * @return The {@link org.fitting.selenium.SeleniumServerManager} singleton instance.
     */
    public static SeleniumServerManager getInstance() {
        if (instance == null) {
            instance = new SeleniumServerManager();
        }
        return instance;
    }

    /**
     * Start a server on the default Selenium server port.
     *
     * @return <code>true</code> if the server was started.
     *
     * @throws Exception When there was a problem while starting the server.
     */
    public boolean startServer() throws Exception {
        return startServer(DEFAULT_PORT);
    }

    /**
     * Start a server on the provided port.
     *
     * @param port The port.
     *
     * @return <code>true</code> if the server was started.
     *
     * @throws Exception When there was a problem while starting the server.
     */
    public boolean startServer(int port) throws Exception {
        boolean started;

        SeleniumServerInstance manager = getSeleniumServerInstanceForPort(port);
        if (manager.isRunning()) {
            started = true;
        } else {
            started = manager.start();
        }

        return started;
    }

    /**
     * Check if a server is running on the default port.
     *
     * @return <code>true</code> if a server is running on the given port.
     */
    public boolean isServerRunning() {
        return isServerRunning(DEFAULT_PORT);
    }

    /**
     * Check if a server is running on the default port.
     *
     * @param port The port.
     *
     * @return <code>true</code> if a server is running on the given port.
     */
    public boolean isServerRunning(int port) {
        boolean running = false;
        // Ensure no managed server manager is added while checking.
        if (servers.containsKey(port)) {
            SeleniumServerInstance manager = getSeleniumServerInstanceForPort(port);
            running = manager.isRunning();
        }
        return running;
    }

    /**
     * Stop the server on the default selenium port if it's running and managed.
     *
     * @return <code>true</code> if the server was successfully stopped.
     */
    public boolean stopServer() {
        return stopServer(DEFAULT_PORT);
    }

    /**
     * Stop the server on the provided port, if it's running and managed.
     *
     * @param port The port.
     *
     * @return <code>true</code> if the server was successfully stopped.
     */
    public boolean stopServer(int port) {
        boolean stopped = false;

        // Ensure no managed server manager is added while checking.
        if (servers.containsKey(port)) {
            SeleniumServerInstance instance = getSeleniumServerInstanceForPort(port);
            stopped = instance.stop();
            servers.remove(port);
        }

        return stopped;
    }

    /**
     * Get a server manager for the given port, creating a new one if it doesn't exist.
     *
     * @param port The port.
     *
     * @return The server manager.
     */
    private SeleniumServerInstance getSeleniumServerInstanceForPort(int port) {
        SeleniumServerInstance manager;
        if (servers.containsKey(port)) {
            manager = servers.get(port);
        } else {
            manager = new SeleniumServerInstance(port);
            servers.put(manager.getPort(), manager);
        }
        return manager;
    }

    /**
     * Get the ports of all managed servers.
     *
     * @return The ports of all managed servers.
     */
    public Set<Integer> getPortsOfManagedServers() {
        return new HashSet<Integer>(servers.keySet());
    }
}
