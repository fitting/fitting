package org.fitting.documentation;

import org.junit.Assert;
import org.junit.Test;

public class FittingRendererExceptionTest {

    private static final Throwable CAUSE = new Throwable();
    private static final String MESSAGE = "Message";

    @Test
    public void testMessage() {
        FittingRenderingException fittingException = new FittingRenderingException(MESSAGE);
        Assert.assertEquals(MESSAGE, fittingException.getMessage());
    }

    @Test
    public void testCause() {
        FittingRenderingException fittingException = new FittingRenderingException(CAUSE);
        Assert.assertEquals(CAUSE, fittingException.getCause());
    }

    @Test
    public void testMessageCause() {
        FittingRenderingException fittingException = new FittingRenderingException(MESSAGE, CAUSE);
        Assert.assertEquals(MESSAGE, fittingException.getMessage());
        Assert.assertEquals(CAUSE, fittingException.getCause());
    }

}
