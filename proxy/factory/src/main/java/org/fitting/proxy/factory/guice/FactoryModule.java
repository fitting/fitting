package org.fitting.proxy.factory.guice;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.fitting.proxy.factory.pool.PoolCleaner;
import org.fitting.proxy.factory.pool.ProxyPool;
import org.fitting.proxy.factory.rest.ProxyResource;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;

/** DI Module for the factory. */
public class FactoryModule extends ServletModule {
    /** Logger for this class */
    private static final Log LOG = LogFactory.getLog(FactoryModule.class);

    @Override
    protected void configureServlets() {
        try {
            Configuration configuration = new PropertiesConfiguration("factory.properties");
            bind(Configuration.class).annotatedWith(Names.named("factory")).toInstance(configuration);
        } catch (ConfigurationException e) {
            bind(Configuration.class).annotatedWith(Names.named("factory")).to(PropertiesConfiguration.class);
            LOG.debug("Factory properties could not be read, using default.");
        }

        bind(ProxyResource.class);
        bind(ProxyPool.class).asEagerSingleton();
        bind(PoolCleaner.class).asEagerSingleton();

        serve("/fitting/*").with(GuiceContainer.class, new HashMap<String, String>());
    }
}
