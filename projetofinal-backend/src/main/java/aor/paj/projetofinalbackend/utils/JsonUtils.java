package aor.paj.projetofinalbackend.utils;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

/**
 * Utility class for JSON operations using Jakarta JSON Binding (JSON-B).
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class JsonUtils {

    private static JsonbConfig config = new JsonbConfig().withFormatting(true);
    private static Jsonb jsonb = JsonbBuilder.create(config);
    private static final String filename = "users.json";

    /**
     * Converts a Java object to its JSON representation.
     *
     * @param object The object to be converted to JSON.
     * @return A JSON string representing the input object.
     */
    public static String convertObjectToJson(Object object) {
        return jsonb.toJson(object);
    }

}

