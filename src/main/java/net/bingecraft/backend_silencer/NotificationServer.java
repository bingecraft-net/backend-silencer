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

  private ServerHandler serverHandler;
  private final EventLoopGroup parentGroup = new NioEventLoopGroup();
  private final EventLoopGroup childGroup = new NioEventLoopGroup();

  public NotificationServer(Configuration configuration, Gson gson) {
    this.configuration = configuration;
    this.gson = gson;
  }

  private static class ServerHandler extends SimpleChannelInboundHandler<Object> {
    protected ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext context) {
      this.context = context;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
    }
  }

  public void start(Plugin plugin, BukkitScheduler scheduler) {
    scheduler.runTaskAsynchronously(plugin, () -> {
      try {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup, childGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel channel) {
              serverHandler = new ServerHandler();
              channel.pipeline().addLast(serverHandler);
            }
          });

        ChannelFuture future = bootstrap.bind(configuration.port).sync();
        future.channel().closeFuture().sync();
      }
      catch (InterruptedException exception) {
        throw new RuntimeException(exception);
      } finally {
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
      }
    });
  }

  public void shutdownGracefully() {
    parentGroup.shutdownGracefully();
    childGroup.shutdownGracefully();
  }

  public void forward(final Notification notification) {
    if (serverHandler.context != null) {
      serverHandler.context.writeAndFlush(
        Unpooled.copiedBuffer(
          gson.toJson(notification),
          StandardCharsets.UTF_8
        )
      );
    }
  }
}
