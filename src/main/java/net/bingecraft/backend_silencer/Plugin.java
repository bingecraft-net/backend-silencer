package net.bingecraft.backend_silencer;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
  private NotificationServer notificationServer;

  @Override
  public void onEnable() {
    Configuration configuration = new Configurator().getConfiguration(this);
    Server server = getServer();

    notificationServer = new NotificationServer(configuration, GsonBuilder.create());
    notificationServer.start(this, server.getScheduler());

    ServerForwarder serverForwarder = new ServerForwarder(notificationServer);
    PlayerForwarder playerForwarder = new PlayerForwarder(configuration);
    Silencer silencer = new Silencer();

    PluginManager pluginManager = server.getPluginManager();
    pluginManager.registerEvents(silencer, this);
    pluginManager.registerEvents(serverForwarder, this);
    pluginManager.registerEvents(playerForwarder, this);
  }

  @Override
  public void onDisable() {
    notificationServer.shutdownGracefully();
  }
}
