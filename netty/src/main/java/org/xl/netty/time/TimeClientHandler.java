package org.xl.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xulei
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf message;

    public TimeClientHandler() {
        String request = "time";
        this.message = Unpooled.buffer(request.length());
        message.writeBytes(request.getBytes());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 读取服务端响应
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] response = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(response);
        System.out.println("Current Time :" + new String(response));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
