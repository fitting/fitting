package org.fitting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fitting.event.ContainerClosedEvent;
import org.fitting.event.ContainerCreatedEvent;
import org.fitting.event.ContainerListener;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isEmpty;

public abstract class ElementContainerProvider {
    private Map<String, ElementContainer> elementContainers;
    private String mainElementContainerId;
    private String activeElementContainerId;
    private List<ContainerListener> containerListeners;

    public ElementContainerProvider() {
        elementContainers = new HashMap<String, ElementContainer>();
        containerListeners = new ArrayList<ContainerListener>();
    }

    public final void registerContainerListener(ContainerListener listener) {
        if (listener != null) {
            containerListeners.add(listener);
        }
    }

    public final void remove(ContainerListener listener) {
        if ((listener != null) && (containerListeners.contains(listener))) {
            containerListeners.remove(listener);
        }
    }

    public final ElementContainer activateElementContainer(String id) throws FittingException {
        ElementContainer elementContainer = getElementContainer(id);
        elementContainer.activate();
        activeElementContainerId = id;
        return elementContainer;
    }

    public final ElementContainer getElementContainer(String id) throws FittingException {
        ElementContainer elementContainer = null;
        if (!isEmpty(id) && elementContainers.containsKey(id)) {
            elementContainer = elementContainers.get(id);
        } else {
            throw new FormattedFittingException(format("Can't activate container with id %s, no container registered with that id.", id));
        }
        return elementContainer;
    }

    public final ElementContainer getActiveElementContainer() {
        return getElementContainer(activeElementContainerId);
    }

    public final void closeActiveElementContainer() {
        closeElementContainer(activeElementContainerId);
    }

    public final void closeElementContainer(String id) {
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

    public final List<String> getRootContainerIds() {
        List<String> rootContainers = new ArrayList<String>();
        for (ElementContainer container : elementContainers.values()) {
            if (container.isRootContainer()) {
                rootContainers.add(container.getId());
            }
        }
        return rootContainers;
    }

    public final List<String> getElementContainerIds() {
        return new ArrayList<String>(elementContainers.keySet());
    }

    public final void setMainElementContainer(String id) {
        ElementContainer container = getElementContainer(id);
        mainElementContainerId = id;
    }

    public final ElementContainer activateMainElementContainer() {
        return activateElementContainer(mainElementContainerId);
    }

    public boolean isElementContainerActive(String id) {
        return activeElementContainerId.equals(id) && getElementContainer(id).isActive();
    }

    public final ElementContainer createNewElementContainer(String uri, boolean activate) throws FittingException {
        ElementContainer elementContainer = createContainer(uri);
        addNewElementContainer(elementContainer, activate);
        return elementContainer;
    }

    public final ElementContainer createNewElementContainer(String uri, String parentId, boolean activate) throws FittingException {
        ElementContainer parent = getElementContainer(parentId);
        ElementContainer elementContainer = createContainer(uri, parent);
        addNewElementContainer(elementContainer, activate);
        return elementContainer;
    }

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

    protected abstract ElementContainer createContainer(String uri) throws FittingException;

    protected abstract ElementContainer createContainer(String uri, ElementContainer parent) throws FittingException;
}
