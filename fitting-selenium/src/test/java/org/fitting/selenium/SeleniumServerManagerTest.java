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

import org.fitting.test.ReflectionUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Unit tests for {@link SeleniumServerManager}.
 *
 * @author Barre Dijkstra
 * @since 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SeleniumServerInstance.class, SeleniumServerManager.class})
public class SeleniumServerManagerTest {
    /** The default port. */
    private static final int PORT_DEFAULT = RemoteControlConfiguration.DEFAULT_PORT;
    /** The custom port. */
    private static final int PORT_CUSTOM = 1357;

    /** Server instance mock, returned when a new instance is created (via PowerMockito's whenNew-construct). */
    @Mock
    private SeleniumServerInstance serverInstance;

    /** The {@link SeleniumServerManager} instance under test, recreated between tests. */
    private SeleniumServerManager instance;

    @Before
    public void setUp() throws Exception {
        whenNew(SeleniumServerInstance.class).withAnyArguments().thenReturn(serverInstance);

        instance = ReflectionUtility.createNewInstanceAndUpdateSingleton(SeleniumServerManager.class, "instance");
    }

    /**
     * Ensure that the instance returned via the singleton method is always the same instance.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#getInstance()
     */
    @Test
    public void shouldAlwaysReturnSingletonInstance() throws Exception {
        SeleniumServerManager instance = SeleniumServerManager.getInstance();

        assertSame("Got a different instance via singleton accessor.", instance, SeleniumServerManager.getInstance());
    }

    /**
     * Given a custom port number and that no server is running on that port.<br/>
     * When {@link SeleniumServerManager#startServer(int)} is called with the port number.<br/>
     * Then ensure that the server is started successfully.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#startServer(int)
     */
    @Test
    public void shouldStartServerOnCustomPort() throws Exception {
        when(serverInstance.start()).thenReturn(true);

        assertTrue("Server not successfully started for custom port.", instance.startServer(PORT_CUSTOM));

        verifyNew(SeleniumServerInstance.class, times(1)).withArguments(PORT_CUSTOM);
        verify(serverInstance, times(1)).start();
    }

    /**
     * Given that a server is already running on a custom port number.<br/>
     * When {@link SeleniumServerManager#startServer(int)} is called with the port number.<br/>
     * Then ensure that the server is not started again if it's already running, but is reported as running.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#startServer(int)
     */
    @Test
    public void shouldNotStartNewServerWithOneExisting() throws Exception {
        when(serverInstance.start()).thenReturn(true);
        when(serverInstance.getPort()).thenReturn(PORT_CUSTOM);
        when(serverInstance.isRunning()).thenReturn(false).thenReturn(true);
        instance.startServer(PORT_CUSTOM);

        assertTrue("Existing server not successfully reported started for custom port.", instance.startServer(PORT_CUSTOM));

        verifyNew(SeleniumServerInstance.class, times(1)).withArguments(PORT_CUSTOM);
        verify(serverInstance, times(1)).start();
    }

    /**
     * Given that no server is running on the default port.<br/>
     * When {@link SeleniumServerManager#startServer()} is called.<br/>
     * Then ensure that the server is started successfully.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#startServer()
     */
    @Test
    public void shouldStartServerOnDefaultPort() throws Exception {
        when(serverInstance.start()).thenReturn(true);

        assertTrue("Server not successfully started for default port.", instance.startServer());

        verifyNew(SeleniumServerInstance.class, times(1)).withArguments(PORT_DEFAULT);
        verify(serverInstance, times(1)).start();
    }

    /**
     * Given that a server is running on the default and the 1st port after is not available. <br/>
     * When calling {@link org.fitting.selenium.SeleniumServerManager#startServerOnFirstAvailablePort(int)}.<br/>
     * The server should be started on the default port + 2.
     *
     * @throws Exception When execution failed.
     */
    @Test
    public void shouldStartServerOnFirstAvailablePort() throws Exception {
        when(serverInstance.start()).thenReturn(true);
        when(serverInstance.isPortAvailable()).thenReturn(false).thenReturn(false).thenReturn(true);
        instance.startServer();

        assertEquals(SeleniumServerManager.DEFAULT_PORT + 2, instance.startServerOnFirstAvailablePort(20));
    }


    /**
     * Given a custom port number and that a server is running on that port.<br/>
     * When {@link SeleniumServerManager#isServerRunning(int)} is called with the port.<br/>
     * Then ensure that the server is reported to be running.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#isServerRunning(int)
     */
    @Test
    public void shouldReportServerRunningOnCustomPort() throws Exception {
        when(serverInstance.start()).thenReturn(true);
        when(serverInstance.getPort()).thenReturn(PORT_CUSTOM);
        when(serverInstance.isRunning()).thenReturn(false).thenReturn(true);
        instance.startServer(PORT_CUSTOM);

        assertTrue("Running server not reported as running.", instance.isServerRunning(PORT_CUSTOM));
    }

    /**
     * Given a custom port number and that a server is registered on that port but not running.<br/>
     * When {@link SeleniumServerManager#isServerRunning(int)} is called with the port.<br/>
     * Then ensure that the server is reported not to be running.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#isServerRunning(int)
     */
    @Test
    public void shouldReportServerNotRunningOnCustomPort() throws Exception {
        when(serverInstance.isRunning()).thenReturn(false).thenReturn(false);
        instance.startServer(PORT_CUSTOM);

        assertFalse("Non-running server reported as running", instance.isServerRunning(PORT_CUSTOM));
    }

    /**
     * Given a custom port number and that no server was registered on that port.<br/>
     * When {@link SeleniumServerManager#isServerRunning(int)} is called with the port.<br/>
     * Then ensure that the server is reported not to be running.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#isServerRunning(int)
     */
    @Test
    public void shouldReportServerNotRunningWhenNoServerExistsOnPort() throws Exception {
        assertFalse("Non-existing server reported as running", instance.isServerRunning(PORT_CUSTOM));
    }

    /**
     * Given that a server is running on the default port.<br/>
     * When {@link SeleniumServerManager#isServerRunning()} is called.<br/>
     * Then ensure that the server is reported to be running.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#isServerRunning()
     */
    @Test
    public void shouldReportServerRunningOnDefaultPort() throws Exception {
        when(serverInstance.start()).thenReturn(true);
        when(serverInstance.getPort()).thenReturn(PORT_DEFAULT);
        when(serverInstance.isRunning()).thenReturn(false).thenReturn(true);
        instance.startServer();

        assertTrue("Running server on default port not reported as running.", instance.isServerRunning());
    }

    /**
     * Given that a server is registered on the default port but not running.<br/>
     * When {@link SeleniumServerManager#isServerRunning()} is called.<br/>
     * Then ensure that the server is reported to be running.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#isServerRunning()
     */
    @Test
    public void shouldReportServerNotRunningOnDefaultPort() throws Exception {
        when(serverInstance.isRunning()).thenReturn(false).thenReturn(false);
        instance.startServer();

        assertFalse("Non-running server on default port reported as running", instance.isServerRunning());
    }

    /**
     * Given a custom port number and that a server is running on that port. <br/>
     * When {@link SeleniumServerManager#stopServer(int)} is called.<br/>
     * Then ensure that the server is stopped.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#stopServer(int)
     */
    @Test
    public void shouldStopServerOnCustomPort() throws Exception {
        when(serverInstance.getPort()).thenReturn(PORT_CUSTOM);
        when(serverInstance.isRunning()).thenReturn(false).thenReturn(true);
        when(serverInstance.stop()).thenReturn(true);
        instance.startServer(PORT_CUSTOM);

        assertTrue("Running server on custom port not stopped successfully.", instance.stopServer(PORT_CUSTOM));

        verify(serverInstance, times(1)).stop();
    }

    /**
     * Given a custom port number and with no server running on that port. <br/>
     * When {@link SeleniumServerManager#stopServer(int)} is called.<br/>
     * Then ensure no servers are stopped.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#stopServer(int)
     */
    @Test
    public void shouldNotStopServerWithNonExisting() throws Exception {
        assertFalse("Non-running server on custom port stopped.", instance.stopServer(PORT_CUSTOM));

        verify(serverInstance, never()).stop();
    }

    /**
     * Given a that a server is running on the default port. <br/>
     * When {@link SeleniumServerManager#stopServer()} is called.<br/>
     * Then ensure that the server is stopped.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#stopServer()
     */
    @Test
    public void shouldStopServerOnDefaultPort() throws Exception {
        when(serverInstance.getPort()).thenReturn(PORT_DEFAULT);
        when(serverInstance.isRunning()).thenReturn(false).thenReturn(true);
        when(serverInstance.stop()).thenReturn(true);

        instance.startServer();

        assertTrue("Running server on default port not stopped successfully.", instance.stopServer());

        verify(serverInstance, times(1)).stop();
    }

    /**
     * Given a that a number of servers are running on different ports and some non-running servers are registered<br/>
     * When {@link org.fitting.selenium.SeleniumServerManager#getPortsOfManagedServers()} is called.<br/>
     * Then ensure that all ports of the managed servers are returned.
     *
     * @throws Exception When execution failed.
     * @see SeleniumServerManager#getPortsOfManagedServers()
     */
    @Test
    public void shouldReturnPortsOfManagedServers() throws Exception {
        Set<Integer> ports = new HashSet<Integer>(Arrays.asList(1001, 1002, 1003, 1004, 1006, 1008, 2001));

        when(serverInstance.start()).thenReturn(true);
        when(serverInstance.isRunning()).thenReturn(false).thenReturn(true);

        for (int p : ports) {
            when(serverInstance.getPort()).thenReturn(p);
            instance.startServer(p);
        }

        assertEquals(ports, instance.getPortsOfManagedServers());
    }
}
