package aor.paj.projetofinalbackend.utils;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gson adapter for serializing and deserializing LocalDateTime objects.
 * This adapter converts LocalDateTime objects to and from ISO-8601 date-time format (yyyy-MM-dd'T'HH:mm:ss).
 *
 * @author João Morais
 * @author Ricardo Elias
 */
// Make class non-abstract and public
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    /**
     * Date-time formatter for ISO-8601 date-time format (yyyy-MM-dd'T'HH:mm:ss).
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Serializes a LocalDateTime object to JSON.
     *
     * @param src The LocalDateTime object to serialize.
     * @param typeOfSrc The type of the source object.
     * @param context The serialization context.
     * @return A JsonElement containing the serialized LocalDateTime object.
     */
    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(src));
    }

    /**
     * Deserializes JSON to a LocalDateTime object.
     *
     * @param json The JSON element to deserialize.
     * @param typeOfT The type of the target object.
     * @param context The deserialization context.
     * @return A LocalDateTime object deserialized from the JSON element.
     */
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return LocalDateTime.parse(json.getAsString(), formatter);
    }
}
