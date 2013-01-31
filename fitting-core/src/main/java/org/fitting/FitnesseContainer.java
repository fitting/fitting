package org.fitting;

/**
 * Fitnesse container. The container is a singleton that holds the FitnesseContext
 * in a threadlocal so each running test or suite contains its own context.
 */
public final class FitnesseContainer {
    // ThreadLocal containing the FitnesseContext.
    private static final ThreadLocal<FitnesseContext> LOCAL = new ThreadLocal<FitnesseContext>();

    /** Constructor. */
    private FitnesseContainer() {
    }

    /**
     * Sets the fitnesse context on the thread local.
     * @param context The FitnesseContext.
     */
    public static void set(final FitnesseContext context) {
        LOCAL.set(context);
    }

    /** Remove the fitnesse context from the thread local. */
    public static void unset() {
        LOCAL.remove();
    }

    /**
     * Get the fitnesse context from the thread local.
     * @return context The FitnesseContext.
     */
    public static FitnesseContext get() {
        return LOCAL.get();
    }
}
