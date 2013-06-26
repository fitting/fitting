package org.fitting;

import static java.lang.String.format;

/**
 * {@link FittingException} implementation that provides FitNesse formatted messages.
 */
public class FormattedFittingException extends FittingException {
    /**
     * The wrapper string for clean fitnesse messages.
     */
    private static final String MESSAGE_WRAPPER = "message:<<FITTING_ERROR %s>>";

    /**
     * Constructor.
     *
     * @param message The message.
     */
    public FormattedFittingException(final String message) {
        super(format(MESSAGE_WRAPPER, message));
    }

    /**
     * Constructor.
     *
     * @param message The message.
     * @param cause   The cause.
     */
    public FormattedFittingException(final String message, final Throwable cause) {
        super(format(MESSAGE_WRAPPER, message), cause);
    }

    /**
     * Constructor.
     *
     * @param cause The cause.
     */
    public FormattedFittingException(final Throwable cause) {
        super(format(MESSAGE_WRAPPER, cause.getMessage()), cause);
    }
}
