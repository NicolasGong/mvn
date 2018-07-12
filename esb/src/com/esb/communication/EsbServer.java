/**
 *
 */
package com.esb.communication;

import com.esb.network.server.EsbNettyServer;

/**
 * @author GongYining
 */
public class EsbServer {

    private final EsbNettyServer server;

    public EsbServer(int port) {
        server = new EsbNettyServer(port);
    }

    public void open() throws InterruptedException {
        server.doOpen();
    }

}
