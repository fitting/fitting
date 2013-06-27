package org.fitting.event;

import java.lang.ref.WeakReference;

import org.fitting.ElementContainer;

/**
 * Event to signal a {@link ElementContainer} was created.
 */
public class ContainerCreatedEvent extends FittingEvent {
    /**
     * The created container.
     *
     * <p>
     * TODO:
     * Decide whether to keep a hard-reference (allowing for the container to not be garbage collected) or risk the container being garbage collected and not having the
     * reference in the event.
     * </p>
     */
    private WeakReference<ElementContainer> container;

    /**
     * Create a new ContainerCreatedEvent.
     *
     * @param container The created container.
     */
    public ContainerCreatedEvent(ElementContainer container) {
        this.container = new WeakReference<ElementContainer>(container);
    }

    /**
     * Create a new ContainerCreatedEvent.
     *
     * @param container The created container.
     * @param source    The object firing the event.
     */
    public ContainerCreatedEvent(ElementContainer container, Object source) {
        super(source);
        this.container = new WeakReference<ElementContainer>(container);
    }

    /**
     * Get the created element container.
     *
     * @return The created element container.
     */
    public ElementContainer getElementContainer() {
        return container.get();
    }
}