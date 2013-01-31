package org.fitting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/** Test class for WebDriverSearchContextProvider. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({WebDriverSearchContextProvider.class, FitnesseContainer.class})
public class WebDriverSearchContextProviderTest {
    private WebDriverSearchContextProvider provider; // class under test

    @Mock
    private FitnesseContext context;

    @Before
    public void setUp() throws Exception {
        provider = new WebDriverSearchContextProvider();

        mockStatic(FitnesseContainer.class);
        when(FitnesseContainer.get()).thenReturn(context);
    }

    @Test
    public void shouldGetId() throws Exception {
        assertEquals("driver", provider.getId());
    }

    @Test
    public void shouldGetSearchContextProvider() throws Exception {
        provider.getSearchContext();
        verify(context, times(1)).getDriver();
    }
}
