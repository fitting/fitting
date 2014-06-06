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

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link org.fitting.FittingContainer}.
 * <p>
 * Note for developers, most of these tests involve ensuring
 * </p>
 * @see FittingContainer
 */
public class FittingContainerTest {
    /**
     * Set up method that gets executed before each unit test.
     * @throws Exception When initialization fails.
     */
    @Before
    public void setUp() throws Exception {
        FittingContainer.unset();
    }


    /**
     * Given the FittingContainer class.
     * When trying to instantiate it.
     * Then ensure that it can't be instantiated.
     * @throws IllegalAccessException Expected outcome due to trying to instantiate the class.
     * @throws Exception When the execution fails.
     */
    @Test(expected = IllegalAccessException.class)
    public void shouldHavePrivateConstructor() throws IllegalAccessException, Exception {
        assertEquals("No accessible constructor expected.", 0, FittingContainer.class.getConstructors().length);

        FittingContainer.class.newInstance();
    }

    /**
     * Given a container without a connector set.<br/>
     * When {@link org.fitting.FittingContainer#get()} is called.<br/>
     * Then ensure it returns <code>null</code>.
     * @throws Exception When execution fails.
     */
    @Test
    public void shouldReturnNullForUninitializedContainer() throws Exception {
        assertNull("Got a connector for an empty container", FittingContainer.get());
    }

    /**
     * Given a container with a connector set.<br/>
     * When {@link org.fitting.FittingContainer#get()} is called.<br/>
     * Then ensure it returns the same connector.
     * @throws Exception When execution fails.
     */
    @Test
    public void shouldReturnConnector() throws Exception {
        FittingConnector connector = mock(FittingConnector.class);
        FittingContainer.set(connector);

        assertSame(connector, FittingContainer.get());
    }

    /**
     * Given a container without a connector set.<br/>
     * When {@link org.fitting.FittingContainer#isInitialised()} is called.<br/>
     * Then ensure it reports false.
     * @throws Exception When execution fails.
     */
    @Test
    public void shouldReportedUninitialized() throws Exception {
        assertFalse(FittingContainer.isInitialised());
    }

    /**
     * Given a container with a connector set.
     * When {@link org.fitting.FittingContainer#isInitialised()} is called.<br/>
     * Then ensure it reports true.
     * @throws Exception When execution fails.
     */
    @Test
    public void shouldReportInitializedWithConnector() throws Exception {
        FittingContainer.set(mock(FittingConnector.class));

        assertTrue(FittingContainer.isInitialised());
    }

    /**
     * Given a container with a connector set.
     * When {@link org.fitting.FittingContainer#unset()} is called.<br/>
     * Then ensure the connector is unset.
     * @throws Exception When execution fails.
     */
    @Test
    public void shouldUnsetConnector() throws Exception {
        FittingContainer.set(mock(FittingConnector.class));
        assertNotNull(FittingContainer.get());

        FittingContainer.unset();
        assertNull(FittingContainer.get());
    }

    /**
     * Given multiple connectors with different names.<br/>
     * When the connectors are set by the same thread.<br/>
     * Then ensure that the connector gets overwritten in the container.
     * @throws Exception When execution fails.
     */
    @Test
    public void shouldOverrideInstancesInSameThread() throws Exception {
        int numberOfConnectors = 4;
        for (int i = 1; i <= numberOfConnectors; i++) {
            FittingConnector connector = mock(FittingConnector.class);
            when(connector.getName()).thenReturn("connector-#" + i);
            FittingContainer.set(connector);
        }

        assertEquals("connector-#" + numberOfConnectors, FittingContainer.get().getName());
    }

    /**
     * Given multiple connectors with different names.<br/>
     * When the connectors are set simultaneously by different threads.<br/>
     * Then ensure that all the different names are set and that no threads overwrite the connector of another thread.
     * @throws Exception When execution fails.
     */
    @Test(timeout = 1000)
    public void shouldSetDifferentInstancesPerThread() throws Exception {
        int numberOfConnectors = 4;
        final List<String> connectorNames = new ArrayList<String>();
        final List<String> names = new ArrayList<String>();

        CountDownLatch nameAddedLatch = new CountDownLatch(4);

        for (int i = 1; i <= numberOfConnectors; i++) {
            final String name = "connector-#" + i;
            names.add(name);
            new Thread(new ConnectorNameExecutor(name, connectorNames, nameAddedLatch)).start();
        }

        nameAddedLatch.await();

        assertTrue(connectorNames.containsAll(names));
        assertTrue(names.containsAll(connectorNames));
    }

    /** Support class for {@link org.fitting.FittingContainerTest#shouldSetDifferentInstancesPerThread()} that adds a given name to a list after a random time (< 1 sec). */
    private static class ConnectorNameExecutor implements Runnable {
        /** Pseudo Random (yay for System.currentTimeMillis() seeds) for generating random wait times. */
        private static final Random rng = new Random(System.currentTimeMillis());
        /** The name of the connector to use. */
        private final String name;
        /** Reference to the list of names to add the name to after the random wait period. */
        private final List<String> names;
        /** Reference to the countdown latch to use to signal the thread was done. */
        private final CountDownLatch nameAddedLatch;

        /**
         * Create a new instance.
         * @param name The name of the connector.
         * @param names The list of names to add the name to.
         * @param nameAddedLatch The countdown latch to use to signal a name has been added.
         */
        public ConnectorNameExecutor(String name, List<String> names, CountDownLatch nameAddedLatch) {
            this.name = name;
            this.names = names;
            this.nameAddedLatch = nameAddedLatch;

        }

        @Override
        public void run() {
            FittingConnector connector = mock(FittingConnector.class);
            when(connector.getName()).thenReturn(name);
            FittingContainer.set(connector);

            try {
                long sleepTime = rng.nextInt(100);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
            names.add(FittingContainer.get().getName());
            nameAddedLatch.countDown();
        }
    }
}
