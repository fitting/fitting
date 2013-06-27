package org.fitting;

/**
 * Connector for connecting Fitting implementation modules to Fitting.
 */
public interface FittingConnector {

    /**
     * The name of the implementation.
     *
     * @return The name.
     */
    String getName();

    /**
     * Get the {@link ByProvider} implementation for the implementation.
     *
     * @return The {@link ByProvider}.
     */
    ByProvider getByProvider();

    /**
     * Get the {@link FittingAction} implementation for the implementation.
     *
     * @return The {@link FittingAction}.
     */
    FittingAction getFittingAction();

    /**
     * Get the {@link ElementContainerProvider} implementation for the implementation.
     *
     * @return The {@link ElementContainerProvider}.
     */
    ElementContainerProvider getElementContainerProvider();

    /**
     * Destroy/tear-down the current connector implementation.
     */
    void destroy();
}
