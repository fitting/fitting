package org.fitting;

import static java.lang.String.format;

/**
 * WebDriverException.
 * Exception that is used to throw when any exception from selenium occurs.
 */
public class WebDriverException extends RuntimeException {
    /** The wrapper string for clean fitnesse messages. */
    private static final String MESSAGE_WRAPPER = "message:<<SELENIUM_ERROR %s>>";

    /**
     * Constructor.
     * @param message The message.
     */
    public WebDriverException(final String message) {
        super(format(MESSAGE_WRAPPER, message));
    }

    /**
     * Constructor.
     * @param message The message.
     * @param cause   The cause.
     */
    public WebDriverException(final String message, final Throwable cause) {
        super(format(MESSAGE_WRAPPER, message), cause);
    }

    /**
     * Constructor.
     * @param cause The cause.
     */
    public WebDriverException(final Throwable cause) {
        super(format(MESSAGE_WRAPPER, cause.getMessage()), cause);
    }
}
