package net.bingecraft.backend_silencer;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Configurator {
  public Configuration getConfiguration(JavaPlugin plugin) {
    FileConfiguration config = plugin.getConfig();
    config.addDefault("forwardToName", "playerNameToForwardCancelledEventsTo");
    config.addDefault("port", 8080);
    config.options().copyDefaults(true);
    plugin.saveConfig();

    return new Configuration(
      config.getString("forwardToName"),
      config.getInt("port")
    );
  }
}
