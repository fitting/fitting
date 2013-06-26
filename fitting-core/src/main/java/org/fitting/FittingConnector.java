package org.fitting;

/** Primary connector for connecting Fitting implementation modules to Fitting. */
public interface FittingConnector {

    String getName();

    ByProvider getByProvider();

    FittingAction getFittingAction();

    ElementContainerProvider getElementContainerProvider();

    void destroy();
}
