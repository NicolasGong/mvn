package com.gyn.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client channel active!!");
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes("hello!!!".getBytes());
        ctx.writeAndFlush(buf).sync();
        System.out.println("client channel active end!!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client handler error!!");
        cause.printStackTrace();
        ctx.close();
    }
}
