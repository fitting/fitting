package org.fitting;

import static java.lang.String.format;

/** Exception for when an {@link org.fitting.ElementContainer} could not been found. */
public class NoSuchContainerException extends FormattedFittingException {
    /** The message for when no container has been registered with the given ID. */
    private static final String NO_CONTAINER_MESSAGE = "No container has been registered with the ID %s";
    /** The message for when no container has been registered with the given ID and parent. */
    private static final String NO_CONTAINER_WITH_PARENT_MESSAGE = "No container has been registered with the ID %s and with a parent container with the ID %s";

    /**
     * Create a new NoSuchContainerException.
     * @param id The ID of the element container that was attempted to be retrieved.
     */
    public NoSuchContainerException(final String id) {
        super(format(NO_CONTAINER_MESSAGE, id));
    }

    /**
     * Create a new NoSuchContainerException for a failed lookup on a container with a specific parent container.
     * @param id The ID of the element container that was attempted to be retrieved.
     * @param parentId The ID of the parent container.
     */
    public NoSuchContainerException(final String id, final String parentId) {
        super(format(NO_CONTAINER_WITH_PARENT_MESSAGE, id, parentId));
    }

}
