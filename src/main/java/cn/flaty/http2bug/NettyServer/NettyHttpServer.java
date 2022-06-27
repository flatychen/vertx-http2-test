package cn.flaty.http2bug.NettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class NettyHttpServer {

  public static void main(String[] args) throws IOException {
    new NettyHttpServer().startServer();
    System.in.read();
  }

  public void startServer() throws IOException {
    final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    final EventLoopGroup workerGroup = new NioEventLoopGroup(1);
    ServerBootstrap b = new ServerBootstrap();
    b.group(bossGroup, workerGroup)
        .handler(new LoggingHandler(LogLevel.DEBUG))
        .channel(NioServerSocketChannel.class)
        .childHandler(new HttpCloseHandler());
    log.info("Netty server start at port:{}", 8080);
    b.bind(8080);
  }

  @Slf4j
  @ChannelHandler.Sharable
  public static class HttpCloseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      log.info("start close the connection");
      ctx.channel().close();
    }
  }
}
