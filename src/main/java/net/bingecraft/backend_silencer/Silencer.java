package net.bingecraft.backend_silencer;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Silencer implements Listener {
  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    event.joinMessage(null);
  }

  @EventHandler
  public void onChat(final AsyncChatEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerQuit(final PlayerQuitEvent event) {
    event.quitMessage(null);
  }
}
