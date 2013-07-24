package org.fitting.documentation;

import org.fitting.FittingException;

/**
 * Exception for rendering related exceptions.
 */
public class FittingRenderingException extends FittingException {
    /**
     * Create a new exception.
     *
     * @param message The exception message.
     */
    public FittingRenderingException(String message) {
        super(message);
    }

    /**
     * Create a new exception.
     *
     * @param message The exception message.
     * @param cause   The cause-exception.
     */
    public FittingRenderingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new exception.
     *
     * @param cause The cause-exception.
     */
    public FittingRenderingException(Throwable cause) {
        super(cause);
    }
}
