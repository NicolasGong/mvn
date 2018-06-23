package com.gyn.network;

import io.netty.bootstrap.ServerBootstrap;

public class NettyServer {

    private final String ip;
    private final int port;

    private ServerBootstrap bootstrap;

    public NettyServer(String ip,int port) {
        this.ip = ip;
        this.port = port;
    }

    public void doOpen() {


    }

}
