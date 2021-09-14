package org.xl.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author xulei
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收客户端请求
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] request = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(request);

        String body = new String(request);
        System.out.println("The Time Server receive request : " + body);

        // 返回时间
        String currentTime = new Date().toString();
        ByteBuf writeBuf = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(writeBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
