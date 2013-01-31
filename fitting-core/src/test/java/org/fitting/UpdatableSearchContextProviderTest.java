package org.fitting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.fitting.ReflectionUtility.extract;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/** Test class for UpdatableSearchContextProvider. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ UpdatableSearchContextProvider.class, FitnesseContainer.class, WebDriverUtil.class })
public class UpdatableSearchContextProviderTest {
    private UpdatableSearchContextProvider provider; // class under test

    @Mock
    private FitnesseContext context;
    @Mock
    private By by;
    @Mock
    private WebElement element;
    @Mock
    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        provider = new UpdatableSearchContextProvider(by) {
            @Override
            public String getId() {
                return "id";
            }
        };

        mockStatic(FitnesseContainer.class);
        when(FitnesseContainer.get()).thenReturn(context);
        when(context.getDriver()).thenReturn(driver);
        mockStatic(WebDriverUtil.class);
        when(WebDriverUtil.getElement(eq(driver), isA(By.class))).thenReturn(element);
    }

    @Test
    public void shouldGetId() throws Exception {
        assertEquals("id", provider.getId());
    }

    @Test
    public void shouldGetSearchContextProvider() throws Exception {
        final SearchContext searchContext = provider.getSearchContext();
        verify(context, times(1)).getDriver();
        assertEquals(searchContext, provider.getSearchContext());
    }
}
