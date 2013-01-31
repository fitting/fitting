package org.fitting.fixture;

import org.fitting.FitnesseContainer;
import org.fitting.FitnesseContext;

/**
 * Fixture for defining proxy settings.
 * Using this fixture will allow you to set host settings.
 */
public class ProxyFixture {
    private String ip;

    /** Default constructor. */
    public ProxyFixture() {
    }

    /**
     * Set the ip address.
     * @param ip The ip address.
     */
    public void setIp(final String ip) {
        this.ip = ip;
    }

    /**
     * Set the domain and adds the ip/domain mapping to the fitnesse context.
     * @param domain The domain.
     */
    public void setDomain(final String domain) {
        final FitnesseContext context = FitnesseContainer.get();
        context.addIpDomainMapping(ip, domain);
        ip = null;
    }
}
