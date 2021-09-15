package org.xl.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;

/**
 * An HTTP server which serves Web Socket requests at:
 *
 * http://localhost:8080/websocket
 *
 * @author xulei
 */
public class WebSocketServer {

    private static final String WEBSOCKET_PATH = "/websocket";

    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            pipeline.addLast(new WebSocketServerCompressionHandler());
                            pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
                            pipeline.addLast(new WebSocketIndexPageHandler(WEBSOCKET_PATH));
                            pipeline.addLast(new WebSocketFrameHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(8080).sync();
            System.out.println("Open your web browser and navigate to http://127.0.0.1:8080/");
            future.channel().closeFuture().sync();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
