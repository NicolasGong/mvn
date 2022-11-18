/**
 *
 */
package com.esb.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * @author GongYining
 */
public class EsbNettyClientListener implements ChannelFutureListener {

    private final EsbNettyClient client;

    public EsbNettyClientListener(EsbNettyClient client) {
        this.client = client;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {

        if (future.isSuccess()){
            System.out.println("operate:" + future.isSuccess());
        } else {
            System.out.println("Reconnect...");
            final EventLoop loop = future.channel().eventLoop();
            loop.schedule(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                client.createBootStrap(new Bootstrap(),loop);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    },1L, TimeUnit.SECONDS
            );
        }

    }
}
