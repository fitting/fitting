package org.fitting.proxy.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/** Read. */
public class Read extends Thread {
    private final int BUFFER_SIZE = 65535;
    private BufferedInputStream in;
    private BufferedOutputStream out;
    private Session session;
    private Server server;

    public Read(final Server server, final Session session,
                final BufferedInputStream in, final BufferedOutputStream out) {
        this.in = in;
        this.out = out;
        this.session = session;
        this.server = server;
        setPriority(Thread.MIN_PRIORITY);
        start();
    }

    /** {@inheritDoc}. */
    public void run() {
        read();
        server = null;
        session = null;
        in = null;
        out = null;

    }

    /** Read. */
    private void read() {
        int bytes_read = 0;
        byte[] buf = new byte[BUFFER_SIZE];
        try {
            while (true) {
                bytes_read = in.read(buf);
                if (bytes_read != -1) {
                    out.write(buf, 0, bytes_read);
                    out.flush();
                    server.addBytesRead(bytes_read);
                } else
                    break;
            }
        } catch (IOException e) {
        }

        try {
            if (session.getStatus() != session.SC_CONNECTING_TO_HOST) {
                session.getClient().close();
            }

        } catch (IOException e_socket_close) {
        }
        buf = null;
    }

    /** stop the thread by closing the socket */
    public void close() {
        try {
            in.close();
        } catch (Exception e) {
        }
    }
}
