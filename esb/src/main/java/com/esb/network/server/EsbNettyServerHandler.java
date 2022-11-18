/**
 *
 */
package com.esb.network.server;

import com.esb.network.common.EsbMessage;
import com.esb.network.common.EsbUtils;
import com.esb.operation.fileOperation.FileOperator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author GongYining
 */
public class EsbNettyServerHandler extends ChannelInboundHandlerAdapter {

    private final FileOperator operator;

    public EsbNettyServerHandler() {
        this.operator = new FileOperator();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Read starting...");
        ByteBuf bf = (ByteBuf) msg;
        System.out.println("read data length : " + bf.readableBytes());
        EsbMessage esbMessage = byteBuf2Message(bf);
        switch (esbMessage.getType()) {
            case 0:
                dealRegisterMessage(esbMessage.getBody());
                break;
            case 1:
            case 2:
            case 3:
                dealFileTransMessage(esbMessage.getBody());
                break;
            case 4:

        }
        System.out.println("Read end!");
        bf.clear();
        bf.release();
        ByteBuf resp = Unpooled.copiedBuffer(new String("OK").getBytes());
        ctx.writeAndFlush(resp);

    }

    private void dealRegisterMessage(byte[] regBytes) {
        System.out.println("处理注册信息   " + new String(regBytes));
    }

    private void dealFileTransMessage(byte[] fileTransBytes) {
        byte[] fileNameLength = new byte[2];
        System.arraycopy(fileTransBytes, 0, fileNameLength, 0, fileNameLength.length);
        short nameLength = EsbUtils.byteToShort(fileNameLength);
        System.out.println("~~~~file name length : " + nameLength);
        byte[] fileName = new byte[nameLength];
        System.arraycopy(fileTransBytes, 2, fileName, 0, fileName.length);
        String fileNameStr = new String(fileName);
        System.out.println("~~~~file name : " + fileNameStr);
        byte[] body = new byte[fileTransBytes.length - fileNameLength.length - fileName.length];
        System.arraycopy(fileTransBytes, 2 + fileName.length, body, 0, body.length);
        operator.writeFile(body, "dstdir/" + fileNameStr);
    }

    private EsbMessage byteBuf2Message(ByteBuf buf) {
        short version = buf.readShort();
        int type = buf.readInt();
        int length = buf.readInt();
        byte[] body = new byte[length];
        buf.readBytes(body);

        return new EsbMessage(type, length, body);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
