package org.fitting.proxy.factory.guice;

import com.google.inject.Injector;
import org.fitting.proxy.factory.pool.ProxyPool;
import org.fitting.proxy.factory.rest.ProxyResource;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.inject.Inject;

import static org.fitting.util.ReflectionUtility.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.*;

/** Test class for GuiceServletConfig. */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GuiceServletConfig.class, FactoryModule.class, PropertiesConfiguration.class})
public class GuiceServletConfigTest {
    private GuiceServletConfig config; // class under test.

    @Inject
    private ProxyResource resource;
    private Injector injector;

    @Before
    public void setUp() throws Exception {
        config = new GuiceServletConfig();
    }

    @Test
    public void shouldInitFactoryModule() throws Exception {
        config.getInjector().injectMembers(this);
        assertNotNull(resource);
        final ProxyPool proxyPool = (ProxyPool) extract(resource, "pool");
        assertNotNull(proxyPool);
        assertEquals(22000, extract(proxyPool, "portBelow"));
    }

    @Test
    public void shouldUseDefaultFactorySettingsAsThePropertyFileIsNotFound() throws Exception {
        whenNew(PropertiesConfiguration.class).withParameterTypes(String.class).withArguments("factory.properties").
                thenThrow(ConfigurationException.class);
        config.getInjector().injectMembers(this);

        assertNotNull(resource);
        final ProxyPool proxyPool = (ProxyPool) extract(resource, "pool");
        assertNotNull(proxyPool);
        assertEquals(20000, extract(proxyPool, "portBelow"));

    }
}
