package net.bingecraft.backend_silencer;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

public class Forwarder implements Listener {
  private final String name;
  private Player player;

  public Forwarder(String name) {
    this.name = name;
  }

  public void sendMessage(Component message) {
    if (player != null) {
      player.sendMessage(message);
    }
  }

  @EventHandler
  private void onPlayerJoin(final PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (player.getName().equals(name)) {
      this.player = player;
    }
  }

  @EventHandler
  private void onPlayerQuit(final PlayerQuitEvent event) {
    if (event.getPlayer().getName().equals(name)) {
      player = null;
    }
  }
}
