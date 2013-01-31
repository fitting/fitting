package org.fitting;

/** Callback for handling stuff when an element is not found. */
public interface NotSuchElementCallback {
    /**
     * Can be used to do stuff when an element is not found
     * @param objects The objects that can be passed to the callback implementation.
     */
    void onNoSuchElementFound(final Object... objects);
}
