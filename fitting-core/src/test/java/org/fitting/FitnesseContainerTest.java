package org.fitting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Constructor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/** Test class for FitnesseContainer. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({WebDriver.class, SeleniumConnector.class})
public class FitnesseContainerTest {
    private FitnesseContext context;

    @Mock
    private SeleniumConnector connector;
    @Mock
    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        when(connector.getDriver()).thenReturn(driver);
        context = new FitnesseContext(connector);
    }

    @Test
    public void shouldGetSetUnsetContext() throws Exception {
        FitnesseContainer.set(context);
        assertEquals(context, FitnesseContainer.get());
        FitnesseContainer.unset();
        assertNull(FitnesseContainer.get());
    }

    @Test
    public void shouldInitialize() throws Exception {
        final Constructor<?> constructor = FitnesseContainer.class
                .getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
