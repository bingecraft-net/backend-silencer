package net.bingecraft.backend_silencer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;


public class ServerForwarder implements Listener {
  private final NotificationServer notificationServer;

  public ServerForwarder(NotificationServer notificationServer) {
    this.notificationServer = notificationServer;
  }

  @EventHandler
  private void onPlayerAdvancementDone(final PlayerAdvancementDoneEvent event) {
    notificationServer.forward(new Notification(Notification.Type.PlayerAdvancementDone, event.message()));
  }

  @EventHandler
  private void onPlayerDeath(final PlayerDeathEvent event) {
    notificationServer.forward(new Notification(Notification.Type.PlayerDeath, event.deathMessage()));
  }
}
