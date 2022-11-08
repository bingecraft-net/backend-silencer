# Backend Silencer

Backend Silencer is a Minecraft server plugin that disables chat, join and quit messages. You're probably going to use this
with a proxy-side plugin that handles chat, joins and quits instead like [Velocity Discord Relay](https://github.com/bingecraft-net/velocity-discord-relay).

## demo

The Backend Silencer plugin is running on the [bingecraft.net Minecraft server](https://bingecraft.net).

## installation

```
git clone git@github.com:bingecraft-net/backend-silencer.git
cd backend-silencer
mvn clean package
```

In the `target` directory there will now be a file called `backend-silencer-$version.jar`. Put this in your Bukkit
server's `plugins` directory and start the server to activate the plugin.
