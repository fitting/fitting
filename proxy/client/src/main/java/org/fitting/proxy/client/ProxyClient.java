package org.fitting.proxy.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.fitting.proxy.domain.Proxy;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public final class ProxyClient {
    private Client client;
    private String ip;
    private int port;
    private final WebResource resource;

    public ProxyClient(final String ip, final int port) {
        this.ip = ip;
        this.port = port;

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        client = Client.create(clientConfig);
        resource = client.resource("http://" + ip + ":" + port + "/fitting");
    }

    /**
     * Start proxy server.
     * @param proxy The proxy settings.
     */
    public void start(final Proxy proxy) {
        resource.path("proxy")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, proxy);

    }

    /**
     * Stop proxy server.
     * @param proxy The proxy settings.
     */
    public void stop(final Proxy proxy) {
        resource.path("proxy/"+proxy.getPort())
                .post(ClientResponse.class, proxy);

    }

    /**
     * Get active proxies.
     * @return nrOfProxies The number of proxies.
     */
    public String active() {
        final ClientResponse response = client.resource("http://" + ip + ":" + port + "/fitting").path("proxy")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
printResponse(response, "http://" + ip + ":" + port + "/fitting/proxy");
        return response.getEntity(String.class);
    }

    /**
     * Reserve a free port.
     * @return port The reserved free port.
     */
    public Integer reserve() {
        final ClientResponse response = client.resource("http://" + ip + ":" + port + "/fitting").path("proxy/reserve")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        printResponse(response, "http://" + ip + ":" + port + "/fitting/proxy/reserve");
        return response.getEntity(Integer.class);
    }

    private void printResponse(final ClientResponse response, final String request) {
        System.out.println("ProxyClient // [" + request + "] [");
        if (response != null) {
            System.out.println("    Status [");
            System.out.println("        [" + response.getStatus() + "]");
            System.out.println("    ]");
            System.out.println("    Location [");
            System.out.println("        [" + response.getLocation() + "]");
            System.out.println("    ]");
            System.out.println("    Type [");
            System.out.println("        [" + response.getType() + "]");
            System.out.println("    ]");
            System.out.println("    EntityTag [");
            System.out.println("        [" + response.getEntityTag() + "]");
            System.out.println("    ]");
            System.out.println("    Headers [");
            final MultivaluedMap<String, String> headers = response.getHeaders();
            for (final String key : headers.keySet()) {
                System.out.println("        " + key + " [");
                for (final String value : headers.get(key)) {
                    System.out.println("            [" + value + "]");
                }
                System.out.println("        ]");
            }
            System.out.println("    ]");
        } else {
            System.out.println("    * No response received.");
        }
        System.out.println("]");
    }
}