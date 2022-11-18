package net.bingecraft.backend_silencer;

import com.google.gson.Gson;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

import java.nio.charset.StandardCharsets;

public class NotificationServer implements Listener {
  private final Configuration configuration;
  private final Gson gson;

  private final EventLoopGroup parentGroup = new NioEventLoopGroup();
  private final EventLoopGroup childGroup = new NioEventLoopGroup();

  private ChannelHandlerContext context;

  public NotificationServer(Configuration configuration, Gson gson) {
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
      throw new RuntimeException(exception);
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
    public void channelActive(ChannelHandlerContext _context) {
      context = _context;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
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
