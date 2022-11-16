package net.bingecraft.backend_silencer;

import net.kyori.adventure.text.Component;

public class Notification {
  public enum Type {
    PlayerDeath, PlayerAdvancementDone
  }

  public final Type type;
  public final Component message;

  public Notification(Type type, Component message) {
    this.type = type;
    this.message = message;
  }
}
