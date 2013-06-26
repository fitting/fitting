package org.fitting.event;

import java.lang.ref.WeakReference;

public abstract class FittingEvent {
    private WeakReference<Object> source;
    private long timeStamp;

    public FittingEvent() {
        timeStamp = System.currentTimeMillis();
    }

    public FittingEvent(Object source) {
        this.source = new WeakReference<Object>(source);
        timeStamp = System.currentTimeMillis();
    }

    public final long getTimeStamp() {
        return timeStamp;
    }

    public final Object getSource() {
        Object obj = null;
        if (source != null) {
            obj = source.get();
        }
        return obj;
    }
}
