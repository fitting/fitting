package org.fitting;

/**
 * Singleton container that holds the FittingConnector in a ThreadLocal so each running test or suite contains its own context.
 */
public final class FittingContainer {
    /** ThreadLocal containing the FittingConnector. */
    private static final ThreadLocal<FittingConnector> LOCAL = new ThreadLocal<FittingConnector>();

    /** Private constructor to prevent instantiation. */
    private FittingContainer() {
    }

    /**
     * Set the FittingConnector to use for the current thread.
     * @param context The FittingConnector to use..
     */
    public static void set(final FittingConnector context) {
        LOCAL.set(context);
    }

    /** Remove the FittingConnector. */
    public static void unset() {
        LOCAL.remove();
    }

    /**
     * Get the FittingConnector for the current thread.
     * @return context The FitnesseContext.
     */
    public static FittingConnector get() {
        return LOCAL.get();
    }
}
