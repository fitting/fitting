package org.fitting.proxy.factory.pool;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.fitting.proxy.server.Server;
import org.fitting.proxy.server.guice.ServerModule;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import java.util.Map;

import static com.google.inject.Guice.*;
import static org.fitting.util.ReflectionUtility.extract;
import static org.fitting.util.ReflectionUtility.inject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/** Test class for ProxyPool. */
@RunWith(MockitoJUnitRunner.class)
public class ProxyPoolTest {
    @Inject
    private ProxyPool pool; // class under test.

    @Mock
    private Server server;

    private static final Integer TEST_PORT = 15000;

    @Before
    public void setUp() throws Exception {
        final Injector injector = createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                install(new ServerModule());
                try {
                    Configuration configuration = new PropertiesConfiguration("factory.test.properties");
                    bind(Configuration.class).annotatedWith(Names.named("factory")).toInstance(configuration);
                    bind(PoolCleaner.class).asEagerSingleton();
                } catch (ConfigurationException e) {
                    fail();
                }

            }
        });
        injector.injectMembers(this);
        assertNotNull(pool);
    }

    @Test
    public void shouldReserve() throws Exception {
        assertNotNull(pool.reserve());
        assertEquals(1, getReservedPorts().keySet().size());
    }

    @Test
    public void shouldBorrowObject() throws Exception {
        final Integer port = pool.reserve();
        assertEquals(1, getReservedPorts().keySet().size());

        final Server server = pool.borrow(port);
        assertEquals(0, getReservedPorts().keySet().size());
        assertEquals(port, server.getPort(), 0);
    }

    @Test
    public void shouldAddPortToReservedPortsWhenBorrowObjectPortIsNotReservedAnymore() throws Exception {
        assertEquals(0, getReservedPorts().keySet().size());

        final Server server = pool.borrow(TEST_PORT);
        assertEquals(0, getReservedPorts().keySet().size());
        assertEquals(TEST_PORT, server.getPort(), 0);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenBorrowObjectPortIsNotFree() throws Exception {
        final Map<Integer, Server> activeProxies = pool.getActiveProxies();
        activeProxies.put(TEST_PORT, new Server(new PropertiesConfiguration()));

        assertEquals(1, activeProxies.keySet().size());
        assertEquals(0, getReservedPorts().keySet().size());

        pool.borrow(TEST_PORT);
    }

    @Test
    public void shouldRelease() throws Exception {
        final Map<Integer, Server> activeProxies = pool.getActiveProxies();
        assertEquals(0, activeProxies.keySet().size());
        pool.release(TEST_PORT);
        assertEquals(0, activeProxies.keySet().size());

        pool.borrow(TEST_PORT); // not started
        assertEquals(1, activeProxies.keySet().size());

        pool.release(TEST_PORT);
        assertEquals(0, activeProxies.keySet().size());

        pool.getActiveProxies().put(TEST_PORT, server);
        assertEquals(1, activeProxies.keySet().size());

        Mockito.when(server.isStopped()).thenReturn(false);
        pool.release(TEST_PORT);
        assertEquals(0, activeProxies.keySet().size());
    }

    @Test
    public void shouldClean() throws InterruptedException {
        pool.reserve();
        assertEquals(1, getReservedPorts().size());
        Thread.sleep(2200);
        assertEquals(0, getReservedPorts().size());
    }

    /**
     * Get the reserved ports map from the pool.
     * @return reservedPorts The reserved ports map.
     */
    private Map<Integer, Long> getReservedPorts() {
        return ((Map<Integer, Long>) extract(pool, "reservedPorts"));
    }
}
