/**
 *
 */
package com.esb.network.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author GongYining
 */
public class EsbClientChildChannelHandler extends ChannelInitializer<SocketChannel> {

    private final EsbNettyClient client;

    public EsbClientChildChannelHandler(EsbNettyClient client) {
        this.client = client;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new EsbNettyClientHandler(client));
    }
}
