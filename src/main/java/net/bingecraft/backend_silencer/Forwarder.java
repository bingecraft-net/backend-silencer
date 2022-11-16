package net.bingecraft.backend_silencer;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Forwarder implements Listener {
  private final Configuration configuration;
  private Player player;

  public Forwarder(Configuration configuration) {
    this.configuration = configuration;
  }

  public void sendMessage(Component message) {
    if (player != null) {
      player.sendMessage(message);
    }
  }

  @EventHandler
  private void onPlayerJoin(final PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (player.getName().equals(configuration.forwardToName)) {
      this.player = player;
    }
  }

  @EventHandler
  private void onPlayerQuit(final PlayerQuitEvent event) {
    if (event.getPlayer().getName().equals(configuration.forwardToName)) {
      player = null;
    }
  }
}
