package org.fitting;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/** Test class for WebDriverException. */
public class WebDriverExceptionTest {
    /** The message as it should be formatted to by the exception. */
    private static final String FORMATTED_MESSAGE = "message:<<SELENIUM_ERROR message>>";

    @Test
    public void shouldFormatMessage() throws Exception {
        final WebDriverException exception = new WebDriverException("message");
        assertEquals(FORMATTED_MESSAGE, exception.getMessage());
        assertNull(exception.getCause());

    }

    @Test
    public void shouldFormatMessageAndHandleCause() throws Exception {
        final WebDriverException exception = new WebDriverException("message", new Exception("my message"));
        assertEquals(FORMATTED_MESSAGE, exception.getMessage());
        assertEquals("my message", exception.getCause().getMessage());
    }

    @Test
    public void shouldUseCauseMessageAndHandleCause() throws Exception {
        final WebDriverException exception = new WebDriverException(new Exception("message"));
        assertEquals(FORMATTED_MESSAGE, exception.getMessage());
        assertEquals("message", exception.getCause().getMessage());
    }
}
