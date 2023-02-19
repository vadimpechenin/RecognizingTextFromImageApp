package core.instantGson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

public class InstantGsonSerializer implements JsonSerializer<Instant> {
    private final Gson gson;
    public InstantGsonSerializer(Gson gson) {
        this.gson = gson;
    }
    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
        long epochSeconds = instant.getEpochSecond();
        return gson.toJsonTree(epochSeconds);
    }
}