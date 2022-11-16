package net.bingecraft.backend_silencer;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerForwarder implements Listener {
  private final Configuration configuration;
  private Player target;

  public PlayerForwarder(Configuration configuration) {
    this.configuration = configuration;
  }

  @EventHandler
  private void onPlayerJoin(final PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (player.getName().equals(configuration.forwardToName)) {
      target = player;
    }

    String message = String.format("%s[%s] joined this backend", player.getName(), player.getAddress());
    sendMessage(Component.text(message, NamedTextColor.RED));
  }

  @EventHandler
  public void onChat(final AsyncChatEvent event) {
    sendMessage(Component.text(event.getPlayer().getName() + ": ", NamedTextColor.RED).append(event.message()));
  }

  @EventHandler
  private void onPlayerQuit(final PlayerQuitEvent event) {
    Player player = event.getPlayer();
    String message = String.format("%s[%s] quit this backend", player.getName(), player.getAddress());
    sendMessage(Component.text(message, NamedTextColor.RED));

    if (player.getName().equals(configuration.forwardToName)) {
      target = null;
    }
  }

  public void sendMessage(Component message) {
    if (target != null) {
      target.sendMessage(message);
    }
  }
}
