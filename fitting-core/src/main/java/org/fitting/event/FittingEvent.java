package org.fitting.event;

import java.lang.ref.WeakReference;

/**
 * Base class for Fitting events.
 */
public abstract class FittingEvent {
    /**
     * The source.
     *
     * <p>
     * TODO:
     * Decide whether to keep a hard-reference (allowing for the source to not be garbage collected) or risk the source being garbage collected and not having the
     * reference in the event.
     * </p>
     */
    private WeakReference<Object> source;
    /**
     * The timestamp of when the event was created.
     */
    private long timeStamp;

    /**
     * Create a new event without a source.
     */
    public FittingEvent() {
        timeStamp = System.currentTimeMillis();
    }

    /**
     * Create a new event.
     *
     * @param source The object creating/firing the event.
     */
    public FittingEvent(Object source) {
        this.source = new WeakReference<Object>(source);
        timeStamp = System.currentTimeMillis();
    }

    /**
     * Get the timestamp of when the event was created.
     *
     * @return The timestamp.
     */
    public final long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Get the object that created/fired the event.
     *
     * @return The source object or <code>null</code> when that information is unavailable.
     */
    public final Object getSource() {
        Object obj = null;
        if (source != null) {
            obj = source.get();
        }
        return obj;
    }
}
