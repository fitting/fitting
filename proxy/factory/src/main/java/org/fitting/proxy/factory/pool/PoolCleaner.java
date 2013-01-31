package org.fitting.proxy.factory.pool;

import javax.inject.Inject;
import javax.inject.Singleton;

/** Thread responsible for cleaning up the reserved ports. */
@Singleton
public class PoolCleaner {
    @Inject
    private ProxyPool pool;

    public PoolCleaner() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        pool.cleanExpiredReservations();
                    } catch (InterruptedException e) {
                        // try again.
                    }
                }
            }
        }).start();
    }
}
