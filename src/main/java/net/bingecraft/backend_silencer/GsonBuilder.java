package net.bingecraft.backend_silencer;

import com.google.gson.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.Listener;

import java.lang.reflect.Type;

public class GsonBuilder implements Listener {
  private static class ComponentSerializer implements JsonSerializer<Component> {
    private final PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();

    @Override
    public JsonElement serialize(Component component, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(serializer.serialize(component));
    }
  }

  public static Gson create() {
    com.google.gson.GsonBuilder gsonBuilder = new com.google.gson.GsonBuilder();
    gsonBuilder.registerTypeAdapter(Component.class, new ComponentSerializer());
    return gsonBuilder.create();
  }
}
