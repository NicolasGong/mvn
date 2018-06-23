package com.gyn.demo;

import com.gyn.network.NettyClient;

public class GynClient {

    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1",9977);
        try {
            client.doConnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
