package org.fitting;

public class FittingException extends RuntimeException {
    public FittingException(final String message) {
        super(message);
    }

    public FittingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FittingException(final Throwable cause) {
        super(cause);
    }
}
