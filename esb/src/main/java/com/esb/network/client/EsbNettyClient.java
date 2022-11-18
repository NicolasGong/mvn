/**
 *
 */
package com.esb.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author GongYining
 */
public class EsbNettyClient {

    private final String ip;
    private final String port;
    private final EventLoopGroup workerGroup;
    private final Bootstrap b;
    private ChannelFuture f;

    public EsbNettyClient(String ip, String port) {
        this.ip = ip;
        this.port = port;
        workerGroup = new NioEventLoopGroup();
        b = new Bootstrap();
    }

    public Bootstrap createBootStrap() throws InterruptedException {
        return createBootStrap(null,null);
    }

    public Bootstrap createBootStrap(Bootstrap bootstrap,EventLoopGroup loop) throws
            InterruptedException {
        if (bootstrap == null || loop == null) {
            bootstrap = b;
            loop = workerGroup;
        }
        bootstrap.group(loop);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new EsbClientChildChannelHandler(this));
        bootstrap.remoteAddress(ip, Integer.valueOf(port));

        f = bootstrap.connect();
        f.addListener(new EsbNettyClientListener(this));

        return bootstrap;

    }

    public void doClose() {
        try {
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
