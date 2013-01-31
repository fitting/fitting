package org.fitting;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/** Fitnesse configuration is a singleton that can be used to access immutable fitnesse properties. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FitnesseConfiguration.class, PropertiesConfiguration.class})
public class FitnesseConfigurationTest {
    private FitnesseConfiguration configuration;

    @Mock
    private PropertiesConfiguration propertiesConfiguration;

    @After
    public void after() throws Exception {
    }

    @Test
    public void shouldInitialize() throws Exception {
        whenNew(PropertiesConfiguration.class).withParameterTypes(String.class)
                .withArguments(eq("fitnesse.properties")).thenReturn(propertiesConfiguration);
        when(propertiesConfiguration.getString(eq("selenium.server.ip"))).thenReturn("ip");
        when(propertiesConfiguration.getString(eq("selenium.server.port"))).thenReturn("port");
        when(propertiesConfiguration.getString(eq("selenium.platform"))).thenReturn("xp");
        when(propertiesConfiguration.getString(eq("http.proxyHost"))).thenReturn("");
        when(propertiesConfiguration.getString(eq("http.proxyPort"))).thenReturn("");
        when(propertiesConfiguration.getString(eq("http.proxyUser"))).thenReturn("");
        when(propertiesConfiguration.getString(eq("http.proxyPassword"))).thenReturn("");
        when(propertiesConfiguration.getBoolean(eq("mode.js"))).thenReturn(true);

        configuration = createConfigurationInstance();

        verify(propertiesConfiguration, times(1)).getString(eq("selenium.server.ip"));
        verify(propertiesConfiguration, times(1)).getString(eq("selenium.server.port"));
        verify(propertiesConfiguration, times(1)).getString(eq("selenium.platform"));
        verify(propertiesConfiguration, times(1)).getBoolean(eq("mode.js"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInitialize() throws Exception {
        whenNew(PropertiesConfiguration.class).withParameterTypes(String.class)
                .withArguments(eq("fitnesse.properties")).thenThrow(ConfigurationException.class);
        configuration = createConfigurationInstance();
    }

    @Test
    public void shouldGetDefaultProperties() throws Exception {
        configuration = createConfigurationInstance();
        assertEquals("127.0.0.1", configuration.getDefaultServerHost());
        assertEquals("4444", configuration.getDefaultServerPort());
    }

    @Test
    public void shouldGetDefaultDesiredCapabilities() throws Exception {
        configuration = createConfigurationInstance();
        final DesiredCapabilities capabilities = configuration.getCapabilities(null, "firefox", null);
        assertEquals(Platform.XP, capabilities.getPlatform());
        assertEquals("firefox", capabilities.getBrowserName());
        assertEquals("", capabilities.getVersion());
        assertTrue(capabilities.isJavascriptEnabled());
    }

    @Test
    public void shouldGetDesiredCapabilities() throws Exception {
        configuration = createConfigurationInstance();
        final DesiredCapabilities capabilities = configuration.getCapabilities("linux", "opera", "8");
        assertEquals(Platform.LINUX, capabilities.getPlatform());
        assertEquals("opera", capabilities.getBrowserName());
        assertEquals("8", capabilities.getVersion());
        assertTrue(capabilities.isJavascriptEnabled());
    }

    @Test
    public void shouldGetDesiredCapabilitiesWithoutJavaScript() throws Exception {
        configuration = createConfigurationInstance();
        final DesiredCapabilities capabilities = configuration.getCapabilities("linux", "opera", "8", false);
        assertEquals(Platform.LINUX, capabilities.getPlatform());
        assertEquals("opera", capabilities.getBrowserName());
        assertEquals("8", capabilities.getVersion());
        assertFalse(capabilities.isJavascriptEnabled());
    }

    @Test
    public void shouldChromeDesiredCapabilities() throws Exception {
        configuration = createConfigurationInstance();
        final DesiredCapabilities capabilities = configuration.getCapabilities(null, "chrome", null);
        assertEquals("chrome", capabilities.getBrowserName());
    }

    @Test
    public void shouldIEDesiredCapabilities() throws Exception {
        configuration = createConfigurationInstance();
        final DesiredCapabilities capabilities = configuration.getCapabilities(null, "ie", null);
        assertEquals("internet explorer", capabilities.getBrowserName());
    }

    @Test
    public void shouldInitializeNormally() throws Exception {
        assertNotNull(FitnesseConfiguration.instance());
    }

    @Test
    public void shouldNotBeAbleToAccessHolder() throws Exception {
        final Class<?> holderClass = FitnesseConfiguration.class.getDeclaredClasses()[0];
        holderClass.getConstructor().newInstance();

        assertEquals("FitnesseConfigurationHolder", holderClass.getSimpleName());
        assertTrue("Holder class is not private.", Modifier.isPrivate(holderClass.getModifiers()));
    }

    /**
     * Create a _new_ configuration instance by bypassing the singleton accessor.
     * @return The created configuration instance.
     * @throws Exception When creation failed.
     */
    private FitnesseConfiguration createConfigurationInstance() throws Exception {
        FitnesseConfiguration config = null;
        Constructor<FitnesseConfiguration> constructor = FitnesseConfiguration.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        config = constructor.newInstance();

        return config;
    }
}
