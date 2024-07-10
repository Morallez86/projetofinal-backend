package aor.paj.projetofinalbackend.utils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Custom JSON deserializer for LocalDateTime objects.
 * This class implements the Jackson JsonDeserializer interface to deserialize
 * JSON string representation of LocalDateTime into LocalDateTime objects.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    /**
     * DateTimeFormatter used to parse LocalDateTime from JSON string representation.
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Deserializes JSON string representation of LocalDateTime into LocalDateTime object.
     *
     * @param jsonParser The JsonParser object used to read JSON content.
     * @param deserializationContext The DeserializationContext object that can be used to access information about deserialization context.
     * @return LocalDateTime object parsed from JSON string representation.
     * @throws IOException If there is an error reading JSON content.
     */
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateStr = jsonParser.getValueAsString();
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateStr, formatter);
    }
}
