package org.fitting.proxy.domain;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/** Proxy. */
@XmlRootElement(name = "proxy")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Proxy {
    private int port;
    private List<DnsEntry> dnsEntries;

    /** Constructor. */
    public Proxy() {
        dnsEntries = new ArrayList<DnsEntry>();
    }

    /**
     * Add the given dns entry.
     * @param entry The entry.
     */
    public void addDnsEntry(final DnsEntry entry) {
        dnsEntries.add(entry);
    }

    /**
     * Gets the proxy port.
     * @param port The proxy port.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Gets the port.
     * @return port The port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the dns entries.
     * @return dnsEntries The dns entries.
     */
    public List<DnsEntry> getDnsEntries() {
        return dnsEntries;
    }
}

