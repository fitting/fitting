package org.fitting.selenium;

import org.junit.Before;
import org.junit.Test;

import static org.fitting.selenium.Browser.DEFAULT;
import static org.fitting.selenium.Browser.INTERNET_EXPLORER;
import static org.fitting.selenium.Browser.getBrowserForAlias;
import static org.junit.Assert.assertEquals;

public class BrowserTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldCreateDefaultBrowserForUnknownAlias() {
        Browser browser = getBrowserForAlias("invalid name");
        assertEquals(DEFAULT, browser);
    }

    @Test
    public void shouldCreateDesiredCapabilitiesForBrowsers() {
        assertEquals("internet explorer", INTERNET_EXPLORER.createDesiredCapabilities().getBrowserName());
    }
}
