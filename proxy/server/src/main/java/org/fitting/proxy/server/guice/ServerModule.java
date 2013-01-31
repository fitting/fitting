package org.fitting.proxy.server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.fitting.proxy.server.Server;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** DI Module for the proxy server. */
public class ServerModule extends AbstractModule {
    /** Logger for this class */
    private static final Log LOG = LogFactory.getLog(ServerModule.class);

    @Override
    protected void configure() {
        try {
            Configuration configuration = new PropertiesConfiguration("server.properties");
            bind(Configuration.class).annotatedWith(Names.named("server")).toInstance(configuration);
        } catch (ConfigurationException e) {
            bind(Configuration.class).annotatedWith(Names.named("server")).to(PropertiesConfiguration.class);
            LOG.debug("Factory properties could not be read, using default.");
        }
        bind(Server.class).toProvider(ServerProvider.class);
    }
}
