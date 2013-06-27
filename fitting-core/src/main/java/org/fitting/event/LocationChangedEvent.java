package org.fitting.event;

import java.lang.ref.WeakReference;

import org.fitting.ElementContainer;

/**
 * Event to signal a {@link ElementContainer} location was changed.
 */
public class LocationChangedEvent extends FittingEvent {
    /**
     * The container.
     *
     * <p>
     * TODO:
     * Decide whether to keep a hard-reference (allowing for the container to not be garbage collected) or risk the container being garbage collected and not having the
     * reference in the event.
     * </p>
     */
    private WeakReference<ElementContainer> container;
    /**
     * The old location.
     */
    private String oldLocation;
    /**
     * The new location.
     */
    private String newLocation;

    /**
     * Create a new LocationChangedEvent.
     *
     * @param container   The container of which the location was changed.
     * @param oldLocation The old location.
     * @param newLocation The new location.
     */
    public LocationChangedEvent(ElementContainer container, String oldLocation, String newLocation) {
        this.container = new WeakReference<ElementContainer>(container);
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    /**
     * Create a new LocationChangedEvent.
     *
     * @param container   The container of which the location was changed.
     * @param oldLocation The old location.
     * @param newLocation The new location.
     * @param source      The object that created/fired the event.
     */
    public LocationChangedEvent(ElementContainer container, String oldLocation, String newLocation, Object source) {
        super(source);
        this.container = new WeakReference<ElementContainer>(container);
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    /**
     * Get the container for which the location was changed.
     *
     * @return The container.
     */
    public ElementContainer getElementContainer() {
        return container.get();
    }

    /**
     * Get the old location.
     *
     * @return The old location.
     */
    public String getOldLocation() {
        return oldLocation;
    }

    /**
     * Get the new location.
     *
     * @return The new location.
     */
    public String getNewLocation() {
        return newLocation;
    }
}
