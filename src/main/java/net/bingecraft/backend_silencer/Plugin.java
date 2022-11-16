package net.bingecraft.backend_silencer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
  @Override
  public void onEnable() {
    FileConfiguration config = getConfig();
    config.options().copyDefaults(true);
    saveConfig();

    PluginManager pluginManager = getServer().getPluginManager();
    Forwarder forwarder = new Forwarder("operator");
    Silencer silencer = new Silencer(forwarder);
    pluginManager.registerEvents(silencer, this);
    pluginManager.registerEvents(forwarder, this);
  }
}
