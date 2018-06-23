package com.gyn.demo;

import com.gyn.network.NettyServer;

public class GynServer {

    public static void main(String[] args) {
        NettyServer server = new NettyServer(9977);
        try {
            server.doOpen();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
