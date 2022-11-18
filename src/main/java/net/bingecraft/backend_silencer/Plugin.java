package net.bingecraft.backend_silencer;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
  private NotificationServer notificationServer;

  @Override
  public void onEnable() {
    Configuration configuration = new Configurator().getConfiguration(this);

    notificationServer = new NotificationServer(getSLF4JLogger(), configuration, GsonBuilder.create());
    notificationServer.bind();

    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(new Silencer(), this);
    pluginManager.registerEvents(new ServerForwarder(notificationServer), this);
    pluginManager.registerEvents(new PlayerForwarder(configuration), this);
  }

  @Override
  public void onDisable() {
    notificationServer.shutdownGracefully();
  }
}
