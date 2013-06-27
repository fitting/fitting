package org.fitting.event;

import java.lang.ref.WeakReference;

import org.fitting.ElementContainer;

/**
 * Event to signal a {@link ElementContainer} was closed.
 */
public class ContainerClosedEvent extends FittingEvent {
    /**
     * The closed container.
     *
     * <p>
     * TODO:
     * Decide whether to keep a hard-reference (allowing for the container to not be garbage collected) or risk the container being garbage collected and not having the
     * reference in the event.
     * </p>
     */
    private WeakReference<ElementContainer> container;

    /**
     * Create a new ContainerClosedEvent.
     *
     * @param container The closed container.
     */
    public ContainerClosedEvent(ElementContainer container) {
        this.container = new WeakReference<ElementContainer>(container);
    }

    /**
     * Create a new ContainerClosedEvent.
     *
     * @param container The closed container.
     * @param source    The object firing the event.
     */
    public ContainerClosedEvent(ElementContainer container, Object source) {
        super(source);
        this.container = new WeakReference<ElementContainer>(container);
    }

    /**
     * Get the closed element container.
     *
     * @return The element container.
     */
    public ElementContainer getElementContainer() {
        return container.get();
    }
}