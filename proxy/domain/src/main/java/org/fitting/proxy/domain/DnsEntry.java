package org.fitting.proxy.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** Dns entry. */
@XmlRootElement(name = "dnsEntry")
@XmlAccessorType(XmlAccessType.FIELD)
public class DnsEntry {
    private String domain;
    private String ip;

    /** Default constructor. */
    public DnsEntry() {
    }

    /**
     * Constructor.
     * @param domain The domain.
     * @param ip     The ip.
     */
    public DnsEntry(final String domain, final String ip) {
        this.domain = domain;
        this.ip = ip;
    }

    /**
     * Gets the domain.
     * @return domain The domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Gets the ip.
     * @return ip The ip.
     */
    public String getIp() {
        return ip;
    }
}
