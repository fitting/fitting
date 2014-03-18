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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Unit tests for {@link SeleniumServerInstance}.
 *
 * @author Barre Dijkstra
 * @since 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ServerSocket.class, RemoteControlConfiguration.class, SeleniumServer.class, SeleniumServerInstance.class})
public class SeleniumServerInstanceTest {
    /** Invalid port. */
    private static final int PORT_INVALID = -1;
    /** Port used. */
    private static final int PORT = 1234;

    @Mock
    private ServerSocket serverSocket;

    @Mock
    private SeleniumServer seleniumServer;

    /** The instance under test, initialized with {@link org.fitting.selenium.SeleniumServerInstanceTest#PORT}. */
    private SeleniumServerInstance instance;


    @Before
    public void setUp() throws Exception {
        whenNew(ServerSocket.class).withAnyArguments().thenReturn(serverSocket);
        whenNew(SeleniumServer.class).withAnyArguments().thenReturn(seleniumServer);

        instance = new SeleniumServerInstance(PORT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCreatingWithInvalidPort() throws Exception {
        new SeleniumServerInstance(PORT_INVALID);
    }

    @Test
    public void shouldCreateInstanceWithValidPort() throws Exception {
        assertNotNull("No instance created while using valid port.", new SeleniumServerInstance(PORT));
    }

    @Test
    public void shouldSetPortOnCreation() throws Exception {
        assertEquals("Reported port is not the port set.", PORT, instance.getPort());
    }

    @Test
    public void shouldNotStartOnCreation() throws Exception {
        assertFalse("Instance reported running without being started.", instance.isRunning());
    }

    @Test
    public void shouldReportPortAvailable() throws Exception {
        assertTrue("Port reported as unavailable while it should be available.", instance.isPortAvailable());
    }

    @Test
    public void shouldReportPortUnavailableWhenSocketCouldNotBeCreated() throws Exception {
        doThrow(new IOException("test")).when(serverSocket).close();
        whenNew(ServerSocket.class).withAnyArguments().thenReturn(serverSocket);

        assertFalse("Port reported as available while it should not be available.", instance.isPortAvailable());
    }
}