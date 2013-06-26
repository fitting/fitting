package org.fitting.event;

public interface ContainerListener {
    void onContainerCreated(ContainerCreatedEvent event);

    void onContainerClosed(ContainerClosedEvent event);

    void onLocationChanged(LocationChangedEvent event);
}
