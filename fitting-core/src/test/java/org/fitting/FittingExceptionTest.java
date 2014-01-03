package org.fitting;

import org.junit.Assert;
import org.junit.Test;

public class FittingExceptionTest {

    private static final Throwable CAUSE = new Throwable();
    private static final String MESSAGE = "Message";

    @Test
    public void testMessage() {
        FittingException fittingException = new FittingException(MESSAGE);
        Assert.assertEquals(MESSAGE, fittingException.getMessage());
    }

    @Test
    public void testCause() {
        FittingException fittingException = new FittingException(CAUSE);
        Assert.assertEquals(CAUSE, fittingException.getCause());
    }

    @Test
    public void testMessageCause() {
        FittingException fittingException = new FittingException(MESSAGE, CAUSE);
        Assert.assertEquals(MESSAGE, fittingException.getMessage());
        Assert.assertEquals(CAUSE, fittingException.getCause());
    }

}
