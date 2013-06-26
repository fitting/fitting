package org.fitting.event;

import java.lang.ref.WeakReference;

import org.fitting.ElementContainer;

public class LocationChangedEvent extends FittingEvent {
    private WeakReference<ElementContainer> container;
    private String oldLocation;
    private String newLocation;

    public LocationChangedEvent(ElementContainer container, String oldLocation, String newLocation) {
        this.container = new WeakReference<ElementContainer>(container);
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    public LocationChangedEvent(ElementContainer container, String oldLocation, String newLocation, Object source) {
        super(source);
        this.container = new WeakReference<ElementContainer>(container);
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    public ElementContainer getElementContainer() {
        return container.get();
    }

    public String getOldLocation() {
        return oldLocation;
    }

    public String getNewLocation() {
        return newLocation;
    }
}
