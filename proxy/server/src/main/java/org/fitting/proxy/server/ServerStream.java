package org.fitting.proxy.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/** Server Stream. */
public class ServerStream extends BufferedInputStream {
    private Session session;

    /**
     * Constructor.
     * @param session The session.
     * @param in      The InputStream.
     */
    public ServerStream(final Session session, final InputStream in) {
        super(in);
        this.session = session;
    }

    /**
     * Read.
     * @param b The bytes.
     * @return read The read.
     * @throws java.io.IOException
     */
    public int read_f(byte[] b) throws IOException {
        return read(b);
    }
}

