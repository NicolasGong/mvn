/**
 *
 */
package com.esb.network.client;

import com.esb.network.cluster.MessageCache;
import com.esb.network.common.EsbCommons;
import com.esb.network.common.EsbMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import java.util.concurrent.TimeUnit;

/**
 * @author GongYining
 */
public class EsbNettyClientHandler extends ChannelInboundHandlerAdapter {

    private final MessageCache cache;
    private final EsbNettyClient client;

    public EsbNettyClientHandler(EsbNettyClient client) {
        cache = MessageCache.getInstance();
        this.client = client;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {

        System.out.println("EsbNettyClientHandler channel active!!!");

//        while (true) {
        System.out.println("channel active!!");
        EsbMessage message = cache.getMessage();
        if (message == null) {
            System.out.println("Error : message is null!!");
//                continue;
        }
        final ByteBuf byteMessage = messageToByteBuf(ctx, message);
        ChannelFuture sync = ctx.writeAndFlush(byteMessage);
        sync.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("~~~ client operation complete!!!");
                } else {
                    System.out.println("~~~ client operation complete  error !!!");
                }
            }
        });
//        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive!!!");
        System.out.println("Reconnect...");
        final EventLoop loop = ctx.channel().eventLoop();
        loop.schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            client.createBootStrap(new Bootstrap(), loop);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, 1L, TimeUnit.SECONDS
        );
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bf = (ByteBuf) msg;
        byte[] bytes = new byte[bf.readableBytes()];
        bf.readBytes(bytes);
        String code = new String(bytes);
        System.out.println(code);
        if (!code.equals("OK")) {
            System.out.println("Read error!!!!");
        }

        System.out.println("channel read!!");
        EsbMessage message = cache.getMessage();
        if (message == null) {
            System.out.println("Error : message is null!!");
        }
        final ByteBuf byteMessage = messageToByteBuf(ctx, message);
        ChannelFuture sync = ctx.writeAndFlush(byteMessage);
        sync.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("~~~ client operation complete!!!");
                } else {
                    System.out.println("~~~ client operation complete  error !!!");
                }
            }
        });

    }

    private ByteBuf messageToByteBuf(ChannelHandlerContext ctx, EsbMessage message) {
        short version = EsbCommons.VERSION_1;
        int type = message.getType();
        int length = message.getLength();
        byte[] body = message.getBody();
        ByteBuf buf = ctx.alloc().buffer(
                EsbCommons.SHORT_LENGTH + EsbCommons.INT_LENGTH + EsbCommons.INT_LENGTH + length);
        buf.writeShort(version);
        buf.writeInt(type);
        buf.writeInt(length);
        buf.writeBytes(body);
        return buf;

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
