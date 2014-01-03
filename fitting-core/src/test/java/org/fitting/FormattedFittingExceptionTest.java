package org.fitting;

import org.junit.Assert;
import org.junit.Test;

public class FormattedFittingExceptionTest {

    private static final Throwable CAUSE = new Throwable();
    private static final String MESSAGE = "Message";

    @Test
    public void testMessage() {
        FormattedFittingException fittingException = new FormattedFittingException(MESSAGE);
        Assert.assertEquals("message:<<FITTING_ERROR Message>>", fittingException.getMessage());
    }

    @Test
    public void testCause() {
        FormattedFittingException fittingException = new FormattedFittingException(CAUSE);
        Assert.assertEquals(CAUSE, fittingException.getCause());
    }

    @Test
    public void testMessageCause() {
        FormattedFittingException fittingException = new FormattedFittingException(MESSAGE, CAUSE);
        Assert.assertEquals("message:<<FITTING_ERROR Message>>", fittingException.getMessage());
        Assert.assertEquals(CAUSE, fittingException.getCause());
    }

}
