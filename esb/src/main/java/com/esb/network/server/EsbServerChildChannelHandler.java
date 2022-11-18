/**
 *
 */
package com.esb.network.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author GongYining
 */
public class EsbServerChildChannelHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addFirst("decoder",new LengthFieldBasedFrameDecoder
                (1048576,6,4, 0,0)).addLast(new EsbNettyServerHandler());
    }
}
