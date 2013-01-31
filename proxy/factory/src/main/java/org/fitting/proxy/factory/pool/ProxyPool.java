package org.fitting.proxy.factory.pool;

import org.fitting.proxy.server.Server;
import org.fitting.proxy.server.guice.ServerProvider;
import org.apache.commons.configuration.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/** Proxy pool. */
@Singleton
public class ProxyPool {
    private static final int DEFAULT_PORT_ABOVE = 12000;
    private static final int DEFAULT_PORT_BELOW = 20000;
    private static final int DEFAULT_RESERVE_TIME = 10000;

    private Map<Integer, Server> activeProxies;
    private Map<Integer, Long> reservedPorts;
    private Long reserveTime;
    private Integer portAbove;
    private Integer portBelow;

    @Inject
    private ServerProvider provider;

    /** Default constructor. */
    @Inject
    public ProxyPool(@Named(value = "factory") final Configuration configuration) {
        activeProxies = Collections.synchronizedMap(new HashMap<Integer, Server>());
        reservedPorts = Collections.synchronizedMap(new HashMap<Integer, Long>());

        reserveTime = configuration.getLong("proxy.pool.reserve.time", DEFAULT_RESERVE_TIME);
        portAbove = configuration.getInteger("proxy.pool.port.above", DEFAULT_PORT_ABOVE);
        portBelow = configuration.getInteger("proxy.pool.port.below", DEFAULT_PORT_BELOW);
    }

    /**
     * Borrow a proxy server from the pool.
     * @param port The port that the proxy server will run on.
     * @return server The server.
     */
    public Server borrow(final int port) {
        final Server server = provider.get();
        server.setPort(port);
        if (reservedPorts.containsKey(port)) {
            activeProxies.put(port, server);
        } else if (isFree(port)) {
            reservedPorts.put(port, System.currentTimeMillis() + reserveTime);
            activeProxies.put(port, server);
        } else {
            throw new IllegalStateException("Port is no longer free.");
        }
        reservedPorts.remove(port);
        return server;
    }

    /**
     * Releases the port.
     * @param port
     */
    public Server release(final int port) {
        Server server = null;
        if (activeProxies.containsKey(port)) {
            server = activeProxies.get(port);
            if (server != null && !server.isStopped()) {
                server.stop();
            }
            activeProxies.remove(port);
        }
        return server;
    }

    /**
     * Reserve free port. Ports are reserved for 1 minute.
     * @return port The free port.
     */
    public Integer reserve() {
        Integer possibleFreePort = nextRandomPort();
        while (!isFree(possibleFreePort)) {
            possibleFreePort = nextRandomPort();
        }
        reservedPorts.put(possibleFreePort, System.currentTimeMillis() + reserveTime);
        return possibleFreePort;
    }

    /** Removes expired reserved ports from the list. */
    public void cleanExpiredReservations() {
        final Set<Integer> expiredReservations = new HashSet<Integer>();
        for(final Integer key : reservedPorts.keySet()) {
            if(System.currentTimeMillis() > reservedPorts.get(key)) {
                expiredReservations.add(key);
            }
        }
        for(final Integer expiredReservation : expiredReservations) {
            reservedPorts.remove(expiredReservation);
        }
    }

    /**
     * Gets the active proxies.
     * @return activeProxies The active proxies.
     */
    public Map<Integer, Server> getActiveProxies() {
        return activeProxies;
    }

    /**
     * Indicate if the port is free.
     * @param port The port to check.
     * @return <code>true</code> if free, else <code>false</code>.
     */
    private boolean isFree(final Integer port) {
        return !activeProxies.containsKey(port) && !reservedPorts.containsKey(port);
    }

    /**
     * Gets the next random port.
     * @return port The next random port.
     */
    private Integer nextRandomPort() {
        return new Random().nextInt(portBelow - portAbove) + portAbove;
    }

}
