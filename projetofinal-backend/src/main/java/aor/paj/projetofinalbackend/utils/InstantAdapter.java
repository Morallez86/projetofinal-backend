package aor.paj.projetofinalbackend.utils;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Gson adapter for serializing and deserializing objects.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class InstantAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    /**
     * Serializes an object to JSON format.
     *
     * @param src The object to be serialized.
     * @param typeOfSrc The type of the source object.
     * @param context The serialization context.
     * @return A JsonElement containing the serialized Instant object.
     */
    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(src));
    }

    /**
     * Deserializes a JSON element to an Instant object.
     *
     * @param json The JSON element to deserialize.
     * @param typeOfT The target type for deserialization.
     * @param context The deserialization context.
     * @return An Instant object deserialized from the JSON element.
     */
    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return Instant.parse(json.getAsString());
    }
}
