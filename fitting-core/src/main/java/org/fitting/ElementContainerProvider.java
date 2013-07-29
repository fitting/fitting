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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fitting.event.ContainerClosedEvent;
import org.fitting.event.ContainerCreatedEvent;
import org.fitting.event.ContainerListener;
import org.fitting.event.LocationChangedEvent;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * This class allows for implementation specific primary manipulation of multiple containers as well as management for multiple containers
 *
 * <p>
 * Event hooks are provided for primary container events (like location changes, creation and destruction).
 * </p>
 *
 * @see ContainerListener
 * @see ContainerCreatedEvent
 * @see ContainerClosedEvent
 * @see LocationChangedEvent
 */
public abstract class ElementContainerProvider {
    /**
     * The element containers, indexed by their id.
     */
    private Map<String, ElementContainer> elementContainers;
    /**
     * The id of the main element container, like for example the main window.
     */
    private String mainElementContainerId;
    /**
     * The id of the currently active element container.
     */
    private String activeElementContainerId;
    /**
     * The registered {@link ContainerListener} implementations for events.
     */
    private List<ContainerListener> containerListeners;

    /**
     * Create a new {@link ElementContainerProvider}.
     */
    public ElementContainerProvider() {
        elementContainers = new HashMap<String, ElementContainer>();
        containerListeners = new ArrayList<ContainerListener>();
    }

    /**
     * Register a {@link ContainerListener}.
     *
     * @param listener The listener to register.
     */
    public final void register(ContainerListener listener) {
        if (listener != null) {
            containerListeners.add(listener);
        }
    }

    /**
     * Remove a registered {@link ContainerListener}.
     *
     * @param listener The listener to remove.
     */
    public final void remove(ContainerListener listener) {
        if ((listener != null) && (containerListeners.contains(listener))) {
            containerListeners.remove(listener);
        }
    }

    /**
     * Activate the container with a given id.
     *
     * @param id The id of the container.
     *
     * @return The activated container.
     *
     * @throws FittingException When no container was found with the given id.
     */
    public final ElementContainer activateElementContainer(String id) throws FittingException {
        ElementContainer elementContainer = getElementContainer(id);
        elementContainer.activate();
        activeElementContainerId = id;
        return elementContainer;
    }

    /**
     * Get an element container with the given id.
     *
     * @param id The id of the container.
     *
     * @return The container.
     *
     * @throws FittingException When no container was found with the given id.
     */

    public final ElementContainer getElementContainer(String id) throws FittingException {
        ElementContainer elementContainer = null;
        if (!isEmpty(id) && elementContainers.containsKey(id)) {
            elementContainer = elementContainers.get(id);
        } else {
            throw new FormattedFittingException(format("Can't activate container with id %s, no container registered with that id.", id));
        }
        return elementContainer;
    }

    /**
     * Get the currently active element container.
     *
     * @return The element container.
     */
    public final ElementContainer getActiveElementContainer() {
        return getElementContainer(activeElementContainerId);
    }

    /**
     * Close the currently active element container.
     *
     * @see ElementContainerProvider#closeElementContainer(String)
     */
    public final void closeActiveElementContainer() {
        closeElementContainer(activeElementContainerId);
    }

    /**
     * Close an element container with the given id.
     *
     * <p>
     * Notes:
     * <ul>
     * <li>If the closed container was the active container, the main container is activated.</li>
     * <li>A {@link ContainerClosedEvent} is fired to all registered listeners.</li>
     * <li>TODO: Currently child containers are not automatically closed/removed.</li>
     * </ul>
     *
     * </p>
     *
     * @param id The id of the container to close.
     *
     * @throws FittingException When no container was found with the given id.
     */
    public final void closeElementContainer(String id) throws FittingException {
        ElementContainer container = elementContainers.remove(id);
        container.close();
        if (isElementContainerActive(id)) {
            if (!id.equals(mainElementContainerId)) {
                activateMainElementContainer();
            }
        }
        ContainerClosedEvent event = new ContainerClosedEvent(container, this);
        for (ContainerListener l : containerListeners) {
            l.onContainerClosed(event);
        }
    }

    /**
     * Get the id's of all root containers.
     *
     * @return The id's.
     */
    public final List<String> getRootContainerIds() {
        List<String> rootContainers = new ArrayList<String>();
        for (ElementContainer container : elementContainers.values()) {
            if (container.isRootContainer()) {
                rootContainers.add(container.getId());
            }
        }
        return rootContainers;
    }

    /**
     * Get id's of all registered element containers.
     *
     * @return The id's.
     */
    public final List<String> getElementContainerIds() {
        return new ArrayList<String>(elementContainers.keySet());
    }

    /**
     * Set the id of the main element container.
     *
     * @param id The id.
     *
     * @throws FittingException When there is no container registered with the given id.
     */
    public final void setMainElementContainer(String id) throws FittingException {
        ElementContainer container = getElementContainer(id);
        mainElementContainerId = id;
    }

    /**
     * Activate the main element container.
     *
     * @return The main element container.
     */
    public final ElementContainer activateMainElementContainer() {
        return activateElementContainer(mainElementContainerId);
    }

    /**
     * Check if an element container is active.
     *
     * @param id The id.
     *
     * @return <code>true</code> if the container is active.
     *
     * @throws FittingException When no container was found with the given id.
     */
    public boolean isElementContainerActive(String id) throws FittingException {
        return activeElementContainerId.equals(id) && getElementContainer(id).isActive();
    }

    /**
     * Create a new element container.
     *
     * @param uri      The URI/location of where the container should load from (e.g. URL for browsers, panel name, etc.)
     * @param activate Indicator if the new container should be activated directly.
     *
     * @return The created element container.
     *
     * @throws FittingException When the container couldn't be created.
     */
    public final ElementContainer createNewElementContainer(String uri, boolean activate) throws FittingException {
        ElementContainer elementContainer = createContainer(uri);
        addNewElementContainer(elementContainer, activate);
        return elementContainer;
    }

    /**
     * Create a new element container as a child of another container.
     *
     * @param uri      The URI/location of where the container should load from (e.g. URL for browsers, panel name, etc.)
     * @param parentId The id of the parent container.
     * @param activate Indicator if the new container should be activated directly.
     *
     * @return The created element container.
     *
     * @throws FittingException When the container couldn't be created or no parent could be found with the given id.
     */
    public final ElementContainer createNewElementContainer(String uri, String parentId, boolean activate) throws FittingException {
        ElementContainer parent = getElementContainer(parentId);
        ElementContainer elementContainer = createContainer(uri, parent);
        addNewElementContainer(elementContainer, activate);
        return elementContainer;
    }

    /**
     * Start management on a new element container, overriding an old container if there is already one with the same id.
     *
     * @param elementContainer The container to add.
     * @param activate         Flag indicating if the container should be activated.
     */
    private void addNewElementContainer(ElementContainer elementContainer, boolean activate) {
        String containerId = elementContainer.getId();
        elementContainers.put(containerId, elementContainer);
        if (activate) {
            activateElementContainer(containerId);
        }
        if (mainElementContainerId == null) {
            setMainElementContainer(containerId);
        }
        ContainerCreatedEvent event = new ContainerCreatedEvent(elementContainer, this);
        for (ContainerListener l : containerListeners) {
            l.onContainerCreated(event);
        }
    }

    /**
     * Create a new container for the given location.
     *
     * @param uri The URI/location of where the container should load from (e.g. URL for browsers, panel name, etc.)
     *
     * @return The created container.
     *
     * @throws FittingException When creation failed.
     */
    protected abstract ElementContainer createContainer(String uri) throws FittingException;

    /**
     * Create a new container for the given location and as child of an existing parent.
     *
     * @param uri    The URI/location of where the container should load from (e.g. URL for browsers, panel name, etc.)
     * @param parent The parent container.
     *
     * @return The created container.
     *
     * @throws FittingException When creation failed.
     */
    protected abstract ElementContainer createContainer(String uri, ElementContainer parent) throws FittingException;
}
