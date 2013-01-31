package org.fitting.proxy.factory.rest;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import org.fitting.proxy.domain.DnsEntry;
import org.fitting.proxy.domain.Proxy;
import org.fitting.proxy.factory.pool.ProxyPool;
import org.fitting.proxy.server.Server;
import org.fitting.proxy.server.guice.ServerModule;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.HashMap;

import static com.google.inject.Guice.createInjector;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Test class for ProxyResource. */
@RunWith(MockitoJUnitRunner.class)
public class ProxyResourceTest {
    @Inject
    private ProxyResource resource;
    @Mock
    private ProxyPool pool;
    @Mock
    private Server server;

    private HashMap<Integer,Server> activeProxies;

    private Proxy proxy;


    private static final Integer TEST_PORT = 15000;

    @Before
    public void setUp() throws Exception {
        final Injector injector = createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                install(new ServerModule());
                bind(ProxyPool.class).toInstance(pool);
            }
        });
        injector.injectMembers(this);
        assertNotNull(pool);

        activeProxies = new HashMap<Integer, Server>();
        when(pool.getActiveProxies()).thenReturn(activeProxies);
        when(pool.borrow(TEST_PORT)).thenReturn(server);

        proxy = new Proxy();
        proxy.setPort(TEST_PORT);
        proxy.addDnsEntry(new DnsEntry("www.fitting-test.org", "127.0.0.1"));
    }

    @Test
    public void shouldNotReturnActivePorts() throws Exception {
        final Response response = resource.active();
        assertTrue(((String) response.getEntity()).contains("Active ports []"));
    }

    @Test
    public void shouldReturnActivePorts() throws Exception {
        activeProxies.put(TEST_PORT, new Server(new PropertiesConfiguration()));
        activeProxies.put(TEST_PORT+1, new Server(new PropertiesConfiguration()));
        final Response response = resource.active();
        final String entity = (String)response.getEntity();
        assertTrue(entity.contains("Active ports [15001,15000]"));
    }

    @Test
    public void shouldStart() throws Exception {
        final Response response = resource.start(proxy);
        assertNull(response.getEntity());
        verify(server, times(1)).setUseProxy(true);
        verify(server, times(1)).setEnableCookiesByDefault(true);
        verify(server, times(1)).setDnsTable(isA(HashMap.class));
        verify(server, times(1)).run();
    }

    @Test
    public void shouldReserve() throws Exception {
        final Response response = resource.reserve();
        assertNotNull(response.getEntity());
    }

    @Test
    public void shouldStop() throws Exception {
        resource.stop(TEST_PORT);
        verify(pool, times(1)).release(TEST_PORT);
    }

    @After
    public void tearDown() throws Exception {
        activeProxies.clear();
    }
}
