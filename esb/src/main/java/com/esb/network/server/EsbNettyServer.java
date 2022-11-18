/**
 *
 */
package com.esb.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author GongYining
 */
public final class EsbNettyServer {

    private final int port;
    private final EventLoopGroup boss;
    private final EventLoopGroup worker;
    private final ServerBootstrap bootstrap;

    public EsbNettyServer(int port) {
        this.port = port;
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
    }

    public void doOpen() throws InterruptedException {

        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new EsbServerChildChannelHandler())
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.bind(port).sync();

//        future.channel().closeFuture().sync();

    }

}
