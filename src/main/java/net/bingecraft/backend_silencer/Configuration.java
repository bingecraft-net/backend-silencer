package net.bingecraft.backend_silencer;

public class Configuration {
  public final String forwardToName;
  public final int port;

  public Configuration(String forwardToName, int port) {
    this.forwardToName = forwardToName;
    this.port = port;
  }
}
