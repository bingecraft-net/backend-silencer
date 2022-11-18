package net.bingecraft.backend_silencer;

import com.google.gson.Gson;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;
import org.slf4j.Logger;

import java.nio.charset.StandardCharsets;

public class NotificationServer implements Listener {
  private final Logger logger;
  private final Configuration configuration;
  private final Gson gson;

  private final EventLoopGroup parentGroup = new NioEventLoopGroup();
  private final EventLoopGroup childGroup = new NioEventLoopGroup();

  private ChannelHandlerContext context;

  public NotificationServer(Logger logger, Configuration configuration, Gson gson) {
    this.logger = logger;
    this.configuration = configuration;
    this.gson = gson;
  }

  public void start(Plugin plugin, BukkitScheduler scheduler) {
    scheduler.runTaskAsynchronously(plugin, this::run);
  }

  private void run() {
    try {
      new ServerBootstrap()
        .group(parentGroup, childGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChildHandler())
        .bind(configuration.port)
        .sync()
        .channel()
        .closeFuture()
        .sync();
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
    }
  }

  private class ChildHandler extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel channel) {
      channel.pipeline().addLast(new ChannelActiveListener());
    }
  }

  private class ChannelActiveListener extends SimpleChannelInboundHandler<Object> {
    @Override
    public void channelActive(ChannelHandlerContext newContext) {
      context = newContext;
      logger.info("listener connected from {}", context.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) {
      logger.info("listener disconnected from {}", context.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
      // discard incoming data
    }
  }

  public void forward(final Notification notification) {
    if (context != null) {
      context.writeAndFlush(
        Unpooled.copiedBuffer(
          gson.toJson(notification),
          StandardCharsets.UTF_8
        )
      );
    }
  }

  public void shutdownGracefully() {
    parentGroup.shutdownGracefully();
    childGroup.shutdownGracefully();
  }
}
