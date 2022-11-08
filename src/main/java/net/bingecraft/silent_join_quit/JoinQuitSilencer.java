package net.bingecraft.silent_join_quit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitSilencer implements Listener {
  @EventHandler
  public void onPlayerQuit(final PlayerQuitEvent event) {
    event.quitMessage(null);
  }

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    event.joinMessage(null);
  }
}
