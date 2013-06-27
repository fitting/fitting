package org.fitting;

/**
 * Generic exception for Fitting related exceptions.
 */
public class FittingException extends RuntimeException {
    /**
     * Create a new exception.
     *
     * @param message The exception message.
     */
    public FittingException(final String message) {
        super(message);
    }

    /**
     * Create a new exception.
     *
     * @param message The exception message.
     * @param cause   The cause-exception.
     */
    public FittingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new exception.
     *
     * @param cause The cause-exception.
     */
    public FittingException(final Throwable cause) {
        super(cause);
    }
}
