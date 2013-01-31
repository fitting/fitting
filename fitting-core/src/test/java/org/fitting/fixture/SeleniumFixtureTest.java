package org.fitting.fixture;

import org.fitting.FitnesseContainer;
import org.fitting.FitnesseContext;
import org.fitting.SeleniumWindow;
import org.fitting.WebDriverUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/** Test class for SeleniumFixture. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FitnesseContainer.class, SeleniumFixture.class, WebDriverUtil.class, SeleniumWindow.class})
public class SeleniumFixtureTest {
    private SeleniumFixture fixture; // class under test

    @Mock
    private FitnesseContext context;
    @Mock
    private SeleniumWindow window;
    @Mock
    private WebDriver driver;
    @Mock
    private WebElement element;

    @Before
    public void setUp() throws Exception {
        mockStatic(FitnesseContainer.class);
        when(FitnesseContainer.get()).thenReturn(context);
        when(context.getDriver()).thenReturn(driver);
        when(context.getActiveWindow()).thenReturn(window);
        Set<String> handles = new HashSet<String>();
        handles.add("windowHandle");
        when(context.getWindowHandles()).thenReturn(handles);
        when(window.getSelectedWebElement()).thenReturn(element);
        when(window.getId()).thenReturn("windowHandle");
        fixture = new SeleniumFixture() {
        };
    }

    @Test
    public void shouldGetWebDriver() {
        fixture.getDriver();

        verify(context, atLeastOnce()).getDriver();
    }

    @Test
    public void shouldSelectedElementFromActiveWindow() {
        fixture.getSelectedElement();

        verify(context).getActiveWindow();
        verify(window).getSelectedWebElement();
    }

    @Test
    public void shouldGetDriverAsSearchProviderWhenNoIdIsSpecified() {
        assertEquals(driver, fixture.getSearchContext());
    }

    @Test
    public void shouldGetDriverAsSearchProvider() {
        assertEquals(driver, fixture.getSearchContext("driver"));
    }

    @Test
    public void shouldNotGetSearchContextWhenIdNotFound() {
        assertNull(fixture.getSearchContext("nonExistingId"));
    }

    @Test
    public void shouldGetSearchProvider() {
        assertNotNull(fixture.getSearchContextProvider("driver"));
    }

    @Test
    public void shouldSetElementOnActiveWindow() {
        fixture.setSelectedElement(element);

        verify(context).getActiveWindow();
        verify(window).setSelectedWebElement(element);
    }
}
