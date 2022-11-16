package net.bingecraft.backend_silencer;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
  @Override
  public void onEnable() {
    Configuration configuration = new Configurator().getConfiguration(this);
    PlayerForwarder playerForwarder = new PlayerForwarder(configuration);
    Silencer silencer = new Silencer();

    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(silencer, this);
    pluginManager.registerEvents(playerForwarder, this);
  }
}
