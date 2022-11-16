package net.bingecraft.backend_silencer;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Silencer implements Listener {
  private final Forwarder forwarder;

  public Silencer(Forwarder forwarder) {
    this.forwarder = forwarder;
  }

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    event.joinMessage(null);
    String message = String.format("%s[%s] joined this backend", event.getPlayer().getName(), event.getPlayer().getAddress());
    forwarder.sendMessage(Component.text(message, NamedTextColor.RED));
  }

  @EventHandler
  public void onChat(final AsyncChatEvent event) {
    event.setCancelled(true);
    forwarder.sendMessage(Component.text(event.getPlayer().getName() + ": ", NamedTextColor.RED).append(event.message()));
  }

  @EventHandler
  public void onPlayerQuit(final PlayerQuitEvent event) {
    event.quitMessage(null);
    String message = String.format("%s[%s] quit this backend", event.getPlayer().getName(), event.getPlayer().getAddress());
    forwarder.sendMessage(Component.text(message, NamedTextColor.RED));
  }
}
