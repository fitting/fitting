package org.fitting.proxy.factory.rest;

import com.google.inject.Singleton;
import org.fitting.proxy.domain.DnsEntry;
import org.fitting.proxy.domain.Proxy;
import org.fitting.proxy.factory.pool.ProxyPool;
import org.fitting.proxy.server.Server;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Path("/proxy")
public class ProxyResource {
    /** Logger for this class */
    private static final Log LOG = LogFactory.getLog(ProxyResource.class);

    @Inject
    private ProxyPool pool;

    /** Default constructor. */
    public ProxyResource() {
    }

    /**
     * Start the proxy on with the given proxy information.
     * @param proxy The proxy information.
     * @return response The response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response start(final Proxy proxy) {
        LOG.debug("Starting proxy on port [" + proxy.getPort() + "]");
        final Server server = pool.borrow(proxy.getPort());
        server.setUseProxy(true);
        server.setEnableCookiesByDefault(true);
        Map<String, String> table = new HashMap<String, String>();
        for (final DnsEntry entry : proxy.getDnsEntries()) {
            table.put(entry.getDomain(), entry.getIp());
        }
        server.setDnsTable(table);
        new Thread(server).start();
        return Response.ok().build();
    }

    /**
     * Stop the proxy that is running on the given port.
     * @param port The port.
     * @return response The response.
     */
    @Path("/{port}")
    @POST // not very clean but delete is not supported in the client
    public Response stop(@PathParam("port") final int port) {
        LOG.debug("Stopping proxy on port [" + port + "]");
        pool.release(port);
        return Response.ok().build();
    }

    /**
     * Gets the active proxies.
     * @return response The response.
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response active() {
        StringBuilder builder = new StringBuilder();
        final Map<Integer, Server> activeProxies = pool.getActiveProxies();
        builder.append("Number of active proxies: " + activeProxies.size() + "\n\n");

        StringBuilder result = new StringBuilder();
        for (final Integer port : activeProxies.keySet()) {
            result.append(port);
            result.append(",");
        }

        builder.append("Active ports [");
        builder.append(result.length() > 0 ? result.substring(0, result.length() - 1) : "");
        builder.append("]");

        return Response.ok(builder.toString()).build();
    }

    /**
     * Reserve a port.
     * @return response The response.
     */
    @Path("/reserve")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reserve() {
        return Response.ok(String.valueOf(pool.reserve())).build();
    }
}
