package aor.paj.projetofinalbackend.utils;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Gson adapter for serializing and deserializing LocalDate objects.
 * This adapter converts LocalDate objects to and from ISO-8601 date format (yyyy-MM-dd).
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    /**
     * Date formatter for ISO-8601 date format (yyyy-MM-dd).
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Serializes a LocalDate object to JSON.
     *
     * @param src LocalDate object to serialize.
     * @param typeOfSrc The type of the source object.
     * @param context The serialization context.
     * @return A JsonElement containing the serialized LocalDate object.
     */
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(src)); // Serialize to ISO-8601 date format
    }

    /**
     * Deserializes JSON to a LocalDate object.
     *
     * @param json The JSON element to deserialize.
     * @param typeOfT The type of the target object.
     * @param context The deserialization context.
     * @return A LocalDate object deserialized from the JSON element.
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return LocalDate.parse(json.getAsString(), formatter); // Deserialize from ISO-8601 date format
    }
}