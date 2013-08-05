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

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link FittingConfiguration}.
 *
 * @author barre
 * @since 1.0
 */
public class FittingConfigurationTest {
    /** The class under test. */
    private FittingConfiguration configuration;

    @Before
    public void setUp() {
        configuration = FittingConfiguration.getInstance();
    }

    /** Ensure the configuration is a singleton. */
    @Test
    public void shouldBeSingleton() {
        // Make sure the constructors are either private or protected.
        for (Constructor<?> c : configuration.getClass().getConstructors()) {
            assertTrue(Modifier.isPrivate(c.getModifiers()) || Modifier.isProtected(c.getModifiers()));
        }
        // Make sure the getInstance() returns the same instance.
        assertSame(configuration, FittingConfiguration.getInstance());
    }

    @Test
    public void shouldLoadConfigurationDefaults() {
        assertEquals(DefaultFittingConnector.class, configuration.getDefaultSystemConnector());
    }

    @Test
    public void shouldOverrideDefaultConfiguration() {
        assertEquals(TestFittingConnector.class, configuration.getSystemConnector());
    }

    static class TestFittingConnector implements FittingConnector {

        @Override
        public String getName() {
            return "TestFittingConnector";
        }

        @Override
        public ByProvider getByProvider() {
            return null;
        }

        @Override
        public FittingAction getFittingAction() {
            return null;
        }

        @Override
        public ElementContainerProvider getElementContainerProvider() {
            return null;
        }

        @Override
        public SearchContext getDefaultSearchContext() {
            return null;
        }

        @Override
        public void destroy() {
        }
    }

    static class DefaultFittingConnector implements FittingConnector {

        @Override
        public String getName() {
            return "DefaultFittingConnector";
        }

        @Override
        public ByProvider getByProvider() {
            return null;
        }

        @Override
        public FittingAction getFittingAction() {
            return null;
        }

        @Override
        public ElementContainerProvider getElementContainerProvider() {
            return null;
        }

        @Override
        public SearchContext getDefaultSearchContext() {
            return null;
        }

        @Override
        public void destroy() {
        }
    }
}
