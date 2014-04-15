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

import org.fitting.event.ContainerClosedEvent;
import org.fitting.event.ContainerCreatedEvent;
import org.fitting.event.ContainerListener;
import org.fitting.event.LocationChangedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Provider that provides {@link org.fitting.ElementContainer} management functionality as well as management for multiple containers.
 * Event hooks are provided for various container events (like location changes, creation and destruction).
 *
 * @see org.fitting.event.ContainerListener
 * @see org.fitting.event.ContainerCreatedEvent
 * @see org.fitting.event.ContainerClosedEvent
 * @see org.fitting.event.LocationChangedEvent
 */
public abstract class ElementContainerProvider {
    /** The element containers, indexed by their id. */
    private final Map<String, ElementContainer> elementContainers;
    /** The registered {@link org.fitting.event.ContainerListener} implementations for events. */
    private final List<ContainerListener> containerListeners;
    /** The id of the main element container, like for example the main window. */
    private String mainElementContainerId;
    /** The id of the currently active element container. */
    private String activeElementContainerId;

    /**
     * Create a new {@link org.fitting.ElementContainerProvider} instance.
     */
    public ElementContainerProvider() {
        elementContainers = new HashMap<String, ElementContainer>();
        containerListeners = new ArrayList<ContainerListener>();
    }

    /**
     * Register a {@link org.fitting.event.ContainerListener}.
     *
     * @param listener The listener to register.
     */
    public final void register(ContainerListener listener) {
        if (listener != null) {
            containerListeners.add(listener);
        }
    }

    /**
     * Remove a registered {@link org.fitting.event.ContainerListener}.
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
     * @throws NoSuchContainerException When no container was found with the given id.
     */
    public final ElementContainer activateElementContainer(String id) throws NoSuchContainerException {
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
     * @throws NoSuchContainerException When no container was found with the given id.
     */

    public final ElementContainer getElementContainer(String id) throws NoSuchContainerException {
        ElementContainer elementContainer = null;
        if (!isEmpty(id) && elementContainers.containsKey(id)) {
            elementContainer = elementContainers.get(id);
        } else {
            throw new NoSuchContainerException(id);
        }
        return elementContainer;
    }

    /**
     * Get the currently active element container.
     * <p>
     * If no container is active, the first registered container will be activated.
     * </p>
     *
     * @return The active element container.
     *
     * @throws NoSuchContainerException When no container is active and no containers are registered.
     */
    public final ElementContainer getActiveElementContainer() {
        if (activeElementContainerId == null && !elementContainers.isEmpty()) {
            activateElementContainer(elementContainers.keySet().iterator().next());
        }
        return getElementContainer(activeElementContainerId);
    }

    /**
     * Close the currently active element container.
     *
     * @throws NoSuchContainerException When no container was active.
     * @see org.fitting.ElementContainerProvider#closeElementContainer(String)
     */
    public final void closeActiveElementContainer() {
        closeElementContainer(activeElementContainerId);
    }

    /**
     * Close an element container with the given id.
     * <p>
     * Notes:
     * <ul>
     * <li>If the closed container was the active container, the main container is activated.</li>
     * <li>A {@link org.fitting.event.ContainerClosedEvent} is fired to all registered listeners.</li>
     * <li>@TODO: Currently child containers are not automatically closed/removed.</li>
     * </ul>
     * </p>
     *
     * @param id The id of the container to close.
     *
     * @throws NoSuchContainerException When no container was found with the given id.
     */
    public final void closeElementContainer(String id) throws NoSuchContainerException {
        ElementContainer container = getElementContainer(id);
        elementContainers.remove(id);
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
     * Get the ids of all root containers.
     *
     * @return The ids.
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
     * @throws NoSuchContainerException When there is no container registered with the given id.
     */
    public final void setMainElementContainer(String id) throws NoSuchContainerException {
        ElementContainer container = getElementContainer(id);
        mainElementContainerId = id;
    }

    /**
     * Activate the main element container.
     *
     * @return The main element container.
     *
     * @throws NoSuchContainerException When no main element container id was set.
     */
    public final ElementContainer activateMainElementContainer() throws NoSuchContainerException {
        return activateElementContainer(mainElementContainerId);
    }

    /**
     * Check if an element container is active.
     *
     * @param id The id.
     *
     * @return <code>true</code> if the container is active.
     *
     * @throws NoSuchContainerException When no container was found with the given id.
     */
    public boolean isElementContainerActive(String id) throws NoSuchContainerException {
        return activeElementContainerId != null && activeElementContainerId.equals(id) && getElementContainer(id).isActive();
    }

    /**
     * Navigate an element container to the provided URI.
     *
     * @param id  The id of the container to navigate.
     * @param uri The URI to navigate to.
     *
     * @throws NoSuchContainerException When no container was found with the given id.
     * @throws FittingException         When When the URI was invalid.
     */
    public final void navigateElementContainerTo(String id, String uri) throws NoSuchContainerException, FittingException {
        navigateElementContainerTo(uri, getElementContainer(id));
    }

    /**
     * Navigate the currently active element container to the provided URI.
     *
     * @param uri The URI to navigate to.
     *
     * @throws NoSuchContainerException When no active container was found.
     * @throws FittingException         When When the URI was invalid.
     */
    public final void navigateElementContainerTo(String uri) throws NoSuchContainerException, FittingException {
        navigateElementContainerTo(uri, getActiveElementContainer());
    }

    /**
     * Navigate the given container to the provided URI and signal all registered listeners of the change.
     *
     * @param uri              The URI to navigate to.
     * @param elementContainer The element container.
     *
     * @throws org.fitting.FittingException When navigation failed.
     */
    protected void navigateElementContainerTo(String uri, ElementContainer elementContainer) throws FittingException {
        String oldLocation = elementContainer.currentLocation();

        elementContainer.navigateTo(uri);

        final LocationChangedEvent event = new LocationChangedEvent(elementContainer, oldLocation, uri, this);
        for (ContainerListener listener : containerListeners) {
            listener.onLocationChanged(event);
        }
    }

    /**
     * Create a new element container.
     *
     * @param uri      The URI/location of where the container should load from (e.g. URL for browsers, panel name, etc.)
     * @param activate Indicator if the new container should be activated directly.
     *
     * @return The created element container.
     *
     * @throws org.fitting.FittingException When the container couldn't be created.
     */
    public final ElementContainer createNewElementContainer(String uri, boolean activate) throws FittingException {
        ElementContainer elementContainer = createContainer(uri);
        manageElementContainer(elementContainer, activate);
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
     * @throws org.fitting.FittingException When the container couldn't be created or no parent could be found with the given id.
     */
    public final ElementContainer createNewElementContainer(String uri, String parentId, boolean activate) throws FittingException {
        ElementContainer parent = getElementContainer(parentId);
        ElementContainer elementContainer = createContainer(uri, parent);
        manageElementContainer(elementContainer, activate);
        return elementContainer;
    }

    /**
     * Start management on a new element container, overriding an old container if there is already one with the same id.
     *
     * @param elementContainer The container to add.
     * @param activate         Flag indicating if the container should be activated.
     */
    protected void manageElementContainer(ElementContainer elementContainer, boolean activate) {
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
     * @throws org.fitting.FittingException When creation failed.
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
     * @throws org.fitting.FittingException When creation failed.
     */
    protected abstract ElementContainer createContainer(String uri, ElementContainer parent) throws FittingException;
}
