# Silent Join Quit

Silent Join Quit is a Minecraft server plugin that disables join and quit messages. You're probably going to use this
with a proxy-side plugin that sends its own join and quit messages
like [Velocity Discord Relay](https://github.com/bingecraft-net/velocity-discord-relay).

## demo

The Silent Join Quit plugin is running on the [bingecraft.net Minecraft server](https://bingecraft.net).

## installation

```
git clone git@github.com:bingecraft-net/silent-join-quit.git
cd silent-join-quit
mvn clean package
```

In the `target` directory there will now be a file called `silent-join-quit-$version.jar`. Put this in your Bukkit
server's `plugins` directory and start the server to activate the plugin.
