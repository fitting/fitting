package org.fitting.event;

import java.lang.ref.WeakReference;

import org.fitting.ElementContainer;

public class ContainerCreatedEvent extends FittingEvent {
    private WeakReference<ElementContainer> container;

    public ContainerCreatedEvent(ElementContainer container) {
        this.container = new WeakReference<ElementContainer>(container);
    }

    public ContainerCreatedEvent(ElementContainer container, Object source) {
        super(source);
        this.container = new WeakReference<ElementContainer>(container);
    }

    public ElementContainer getElementContainer() {
        return container.get();
    }
}