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
import org.fitting.test.ReflectionUtility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ElementContainerProviderTest {

    private static final String ELEMENT_CONTAINER_ID = "ID1";

    private ElementContainerProvider elementContainerProvider;

    private ElementContainer elementContainer;

    @Mock
    private ContainerListener containerListener;

    @Before
    public void init() {
        this.elementContainer = Mockito.mock(ElementContainer.class);
        Mockito.when(elementContainer.getId()).thenReturn(ELEMENT_CONTAINER_ID, "ID2", "ID3", "ID4", "ID5");
        this.elementContainerProvider = new ElementContainerProvider() {
            @Override
            protected ElementContainer createContainer(final String uri, final ElementContainer parent) throws FittingException {
                return elementContainer;
            }

            @Override
            protected ElementContainer createContainer(final String uri) throws FittingException {
                return elementContainer;
            }
        };
    }

    @Test
    public void constructorShouldInitializeCollections() {
        Assert.assertNotNull(getElementContainers());
        Assert.assertEquals(0, getElementContainers().size());
        Assert.assertNotNull(getListeners());
        Assert.assertEquals(0, getListeners().size());
    }

    @Test
    public void testListenerAdministration() {
        Assert.assertEquals(0, getListeners().size());
        this.elementContainerProvider.register(containerListener);
        Assert.assertEquals(1, getListeners().size());
        this.elementContainerProvider.register(null);
        Assert.assertEquals(1, getListeners().size());
        this.elementContainerProvider.remove(null);
        Assert.assertEquals(1, getListeners().size());
        this.elementContainerProvider.remove(Mockito.mock(ContainerListener.class));
        Assert.assertEquals(1, getListeners().size());
        this.elementContainerProvider.remove(containerListener);
        Assert.assertEquals(0, getListeners().size());
    }

    @Test
    public void testCreateNewElementContainer() {
        Assert.assertEquals(0, getElementContainers().size());
        this.elementContainerProvider.createNewElementContainer("", false);
        Assert.assertEquals(1, getElementContainers().size());
        Assert.assertNotNull(this.elementContainerProvider.getElementContainer(ELEMENT_CONTAINER_ID));
        Assert.assertFalse(this.elementContainerProvider.isElementContainerActive(ELEMENT_CONTAINER_ID));
        this.elementContainerProvider.createNewElementContainer("", ELEMENT_CONTAINER_ID, false);
        Assert.assertEquals(2, getElementContainers().size());
    }

    @Test
    public void testActiveElementContainer() {
        Mockito.when(elementContainer.isActive()).thenReturn(true);

        this.elementContainerProvider.createNewElementContainer("", true);
        Assert.assertNotNull(this.elementContainerProvider.getActiveElementContainer());
        Assert.assertTrue(this.elementContainerProvider.isElementContainerActive(ELEMENT_CONTAINER_ID));
    }

    @SuppressWarnings("unchecked")
    private List<ContainerListener> getListeners() {
        return (List<ContainerListener>) ReflectionUtility.extract(this.elementContainerProvider, "containerListeners");
    }

    @SuppressWarnings("unchecked")
    private Map<String, ElementContainer> getElementContainers() {
        return (Map<String, ElementContainer>) ReflectionUtility.extract(this.elementContainerProvider, "elementContainers");
    }

}
