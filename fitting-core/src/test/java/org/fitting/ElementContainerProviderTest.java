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

import org.fitting.event.ContainerListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.fitting.test.ReflectionUtility.extract;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** Unit tests for {@link ElementContainerProvider}. */
@RunWith(MockitoJUnitRunner.class)
public class ElementContainerProviderTest {

    /** The ID of the element container. */
    private static final String ELEMENTCONTAINER_ID = "elementContainer";

    /** Mock Element container. */
    @Mock
    private ElementContainer elementContainer;

    /** Mock container listener. */
    @Mock
    private ContainerListener containerListener;

    /** Base implementation of the class under test. */
    private ElementContainerProvider provider;

    /** Set up the environment for the unit tests, called before every test. */
    @Before
    public void setUp() {
        when(elementContainer.getId()).thenReturn(ELEMENTCONTAINER_ID);

        provider = new ElementContainerProvider() {
            @Override
            protected ElementContainer createContainer(String uri) throws FittingException {
                return elementContainer;
            }

            @Override
            protected ElementContainer createContainer(String uri, ElementContainer parent) throws FittingException {
                return elementContainer;
            }
        };
    }

    /**
     * Given a new ElementContainerProvider.<br/>
     * When the number of registered listeners is checked.<br/>
     * Then there should be none registered.
     * @see ElementContainerProvider#ElementContainerProvider()
     */
    @Test
    public void shouldNotHaveListenersOnCreation() {
        List<ContainerListener> listeners = getListeners(provider);
        assertNotNull(listeners);
        assertEquals(0, listeners.size());
    }

    /**
     * Given a new ElementContainerProvider.<br/>
     * When the number of registered element containers is checked.<br/>
     * Then there should be none registered.
     * @see ElementContainerProvider#ElementContainerProvider()
     */
    @Test
    public void shouldNotHaveContainersOnCreation() {
        Map<String, ElementContainer> elementContainers = getElementContainers(provider);
        assertNotNull(elementContainers);
        assertEquals(0, elementContainers.size());
    }

    /**
     * Given an unregistered {@link ContainerListener}.<br/>
     * When the listener is registered.<br/>
     * Then it should be available in the provider.
     * @see ElementContainerProvider#register(ContainerListener)
     */
    @Test
    public void shouldRegisterListener() {
        assertEquals(0, getListeners(provider).size());

        provider.register(containerListener);

        assertEquals(1, getListeners(provider).size());
    }

    /**
     * When a null object is registered as container listener.<br/>
     * Then no listeners should be added to the provider.
     * @see ElementContainerProvider#register(ContainerListener)
     */
    @Test
    public void shouldNotRegisterNullListener() {
        provider.register(containerListener);
        assertEquals(1, getListeners(provider).size());

        provider.register(null);

        assertEquals(1, getListeners(provider).size());
    }

    /**
     * Given a registered container listener.<br/>
     * When the listener is unregistered from the provider via {@link ElementContainerProvider#remove(ContainerListener)}.<br/>
     * Then the listener should be removed.
     * @see ElementContainerProvider#remove(ContainerListener)
     */
    @Test
    public void shouldUnregisterExistingListener() {
        provider.register(containerListener);
        assertEquals(1, getListeners(provider).size());

        provider.remove(containerListener);

        assertEquals(0, getListeners(provider).size());
    }

    /**
     * Given a provider with a registered container listener.<br/>
     * When a null object is unregistered from the provider via {@link ElementContainerProvider#remove(ContainerListener)}.<br/>
     * Then nothing should be unregistered.
     * @see ElementContainerProvider#remove(ContainerListener)
     */
    @Test
    public void shouldNotUnregisterNullListener() {
        provider.register(containerListener);
        assertEquals(1, getListeners(provider).size());

        provider.remove(null);

        assertEquals(1, getListeners(provider).size());
    }

    /**
     * Given a provider with a registered container listener.<br/>
     * When a non registered listener is unregistered from the provider via {@link ElementContainerProvider#remove(ContainerListener)}.<br/>
     * Then nothing should be unregistered.
     * @see ElementContainerProvider#remove(ContainerListener)
     */
    @Test
    public void shouldNotUnregisterUnregisteredListener() {
        ContainerListener newListener = mock(ContainerListener.class);
        provider.register(containerListener);
        assertEquals(1, getListeners(provider).size());


        provider.remove(newListener);

        assertEquals(1, getListeners(provider).size());
    }

    /**
     * Given an ID of a container, which is not registered.<br/>
     * When a container is retrieved with the ID.<br/>
     * Then a {@link NoSuchContainerException} should be thrown.
     * @see ElementContainerProvider#getElementContainer(String)
     */
    @Test(expected = NoSuchContainerException.class)
    public void shouldThrowExceptionForGettingUnknownContainer() {
        provider.getElementContainer("non-existing-id");
    }

    /**
     * Given a registered container.<br/>
     * When the container is retrieved via its ID.<br/>
     * Then the same container should be returned.
     * @see ElementContainerProvider#getElementContainer(String)
     */
    @Test
    public void shouldGetContainerForKnownContainer() {
        provider.manageElementContainer(elementContainer, false);

        assertSame(elementContainer, provider.getElementContainer(ELEMENTCONTAINER_ID));
    }


    /**
     * Given a registered container and no active containers.<br/>
     * When the active container is requested.<br/>
     * Then the first available container should be activated.
     */
    @Test
    public void shouldActivateFirstAvailableContainerWhenNonIsActive() {
        when(elementContainer.isActive()).thenReturn(true);
        provider.manageElementContainer(elementContainer, false);
        assertFalse(provider.isElementContainerActive(ELEMENTCONTAINER_ID));

        assertSame(elementContainer, provider.getActiveElementContainer());
        assertTrue(provider.isElementContainerActive(ELEMENTCONTAINER_ID));
    }

    /**
     * Given a container provider without any registered containers.<br/>
     * When a new container is created but not activated.<br/>
     * It should be registered and be activated after all.
     * @see ElementContainerProvider#createNewElementContainer(String, boolean)
     */
    public void shouldRegisterNewContainer() {
        provider.createNewElementContainer("", false);
    }

    /**
     * Get the content of the private {@link org.fitting.ElementContainerProvider#containerListeners}.
     * @param provider The ElementContainerProvider to get the values from.
     * @return The content of the field.
     */
    private List<ContainerListener> getListeners(ElementContainerProvider provider) {
        return (List<ContainerListener>) extract(provider, "containerListeners");
    }

    /**
     * Get the content of the private {@link org.fitting.ElementContainerProvider#elementContainers}.
     * @param provider The ElementContainerProvider to get the values from.
     * @return The content of the field.
     */
    private Map<String, ElementContainer> getElementContainers(ElementContainerProvider provider) {
        return (Map<String, ElementContainer>) extract(provider, "elementContainers");
    }
}
