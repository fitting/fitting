package org.fitting;

/**
 * Generic element container which can be anything that acts as a container for elements like panels, windows, frames and so forth.
 */
public interface ElementContainer extends SearchContext {
    /**
     * Get the id of the container.
     *
     * @return The id of the container.
     */
    String getId();

    /**
     * Get the id of the parent container.
     *
     * @return The id of the parent container or <code>null</code> if there is no parent container.
     *
     * @see ElementContainer#hasParent()
     */
    String getParentId();

    /**
     * Check if the container has a parent container.
     *
     * @return <code>true</code> if the container has a parent container.
     *
     * @see ElementContainer#getParentId()
     */
    boolean hasParent();

    /**
     * Get the size of the container in pixels.
     *
     * @return The size.
     */
    Dimension getSize();

    /**
     * Check if the container is the current active container.
     *
     * @return <code>true</code> if the container is the active container.
     */
    boolean isActive();

    /**
     * Make the container the active container.
     *
     * <p>
     * If the container does not support being activated (e.g. for panels), it is up to the implementing class to decide whether to delegate the call to a parent container,
     * ignore the call or  throw an exception.
     * </p>
     */
    void activate();

    /**
     * Refresh the container.
     *
     * <p>
     * If the container does not support closing, it is up to the implementing class to decide whether to delegate to a parent container,
     * ignore the call or throw an exception.
     * </p>
     */
    void refresh();

    /**
     * Check if the container is a root container, like e.g. a window.
     *
     * @return <code>true</code> if the container is a root container.
     */
    boolean isRootContainer();

    /**
     * Close the container.
     *
     * <p>
     * If the container does not support closing, it is up to the implementing class to decide whether to delegate to a parent container,
     * ignore the call or throw an exception.
     * </p>
     */
    void close();
}