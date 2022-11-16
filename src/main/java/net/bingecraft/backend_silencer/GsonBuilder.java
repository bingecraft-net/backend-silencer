package net.bingecraft.backend_silencer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.event.Listener;

import java.lang.reflect.Type;

public class GsonBuilder implements Listener {
  private static class ComponentSerializer implements JsonSerializer<Component> {
    private final GsonComponentSerializer gsonComponentSerializer = GsonComponentSerializer.gson();

    @Override
    public JsonElement serialize(Component component, Type typeOfSrc, JsonSerializationContext context) {
      return gsonComponentSerializer.serializeToTree(component);
    }
  }

  public static Gson create() {
    com.google.gson.GsonBuilder gsonBuilder = new com.google.gson.GsonBuilder();
    gsonBuilder.registerTypeAdapter(Component.class, new ComponentSerializer());
    return gsonBuilder.create();
  }
}
