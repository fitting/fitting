package org.fitting.event;

/**
 * Listener for container related events.
 */
public interface ContainerListener {
    /**
     * Event hook for when a container is created.
     *
     * @param event The event.
     */
    void onContainerCreated(ContainerCreatedEvent event);

    /**
     * Event hook for when a container is closed.
     *
     * @param event The event.
     */
    void onContainerClosed(ContainerClosedEvent event);

    /**
     * Event hook for when the location of a container has changed.
     *
     * @param event The event.
     */
    void onLocationChanged(LocationChangedEvent event);
}
