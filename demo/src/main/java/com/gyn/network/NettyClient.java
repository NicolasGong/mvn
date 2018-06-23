package com.gyn.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    private final String ip;
    private final int port;

    private EventLoopGroup group;
    private Bootstrap bootstrap;

    public NettyClient(String ip,int port) {
        this.ip = ip;
        this.port = port;
    }

    public void doConnect() throws InterruptedException {

        group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new NettyClientHandler());
                    }
                });

        // Make the connection attempt.
        ChannelFuture f = b.connect(ip, port).sync();
    }

}
