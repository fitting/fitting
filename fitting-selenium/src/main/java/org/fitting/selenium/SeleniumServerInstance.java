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
import org.openqa.selenium.server.SeleniumServer;

import java.io.IOException;
import java.net.ServerSocket;

import static java.lang.String.format;

/**
 * Selenium server instance abstraction with basic start and stop functionality.
 *
 * @author Barre Dijkstra
 * @since 1.0
 */
public class SeleniumServerInstance {
    /** The port the server is assigned to. */
    private final int port;

    /** The server implementation. */
    private SeleniumServer server;

    /** Flag indicating if the server is running. */
    private boolean running;

    /**
     * Create a manager instance for a custom port.
     *
     * @param port The port.
     *
     * @throws IllegalArgumentException When an invalid port was specified.
     */
    public SeleniumServerInstance(int port) {
        if (port < 1) {
            throw new IllegalArgumentException(format("Cannot run a Selenium server instance on port [%d].", port));
        }
        this.port = port;
    }

    /**
     * Start the server if it's not already running (and no other application is running on the given port).
     *
     * @return <code>true</code> if the server was started successfully.
     *
     * @throw Exception When the server could not be started.
     */
    public synchronized boolean start() throws Exception {
        if (!isRunning()) {
            RemoteControlConfiguration rcc = new RemoteControlConfiguration();
            rcc.setPort(port);
            server = new SeleniumServer(false, rcc);
            server.start();
            running = true;
        }
        return running;
    }

    /**
     * Stop the selenium server.
     *
     * @return <code>true</code> if the server was stopped successfully.
     */
    public synchronized boolean stop() {
        boolean stopped = false;
        if (server != null && isRunning()) {
            server.stop();
            running = false;
            stopped = true;
        }

        return stopped;
    }

    /**
     * Check if the server is running.
     *
     * @return <code>true</code> if the server is running.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Check if the port for the server is available or used (either by the selenium server or another process).
     *
     * @return <code>true</code> if the port is available.
     */
    public synchronized boolean isPortAvailable() {
        boolean available;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
            available = true;
        } catch (IOException e) {
            available = false;
        }
        return available;
    }

    /**
     * Get the port the server is bound to.
     *
     * @return The port.
     */
    public int getPort() {
        return port;
    }
}
