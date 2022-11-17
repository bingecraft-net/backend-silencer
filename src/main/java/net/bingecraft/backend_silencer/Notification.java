package net.bingecraft.backend_silencer;

import net.kyori.adventure.text.Component;

import java.util.UUID;

public class Notification {
  public final UUID playerID;
  public final Component message;

  public Notification(UUID playerID, Component message) {
    this.playerID = playerID;
    this.message = message;
  }
}
