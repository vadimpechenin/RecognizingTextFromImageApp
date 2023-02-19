package core.instantGson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

public class InstantGsonDeserializer implements JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        long epochSeconds = json.getAsLong();
        return Instant.ofEpochSecond(epochSeconds);
    }
}
