package net.bingecraft.backend_silencer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
  @Override
  public void onEnable() {
    FileConfiguration config = getConfig();
    config.options().copyDefaults(true);
    saveConfig();

    getServer().getPluginManager().registerEvents(new Silencer(), this);
  }
}
