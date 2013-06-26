package org.fitting.event;

import java.lang.ref.WeakReference;

import org.fitting.ElementContainer;

public class ContainerClosedEvent extends FittingEvent {
    private WeakReference<ElementContainer> container;

    public ContainerClosedEvent(ElementContainer container) {
        this.container = new WeakReference<ElementContainer>(container);
    }

    public ContainerClosedEvent(ElementContainer container, Object source) {
        super(source);
        this.container = new WeakReference<ElementContainer>(container);
    }

    public ElementContainer getElementContainer() {
        return container.get();
    }
}