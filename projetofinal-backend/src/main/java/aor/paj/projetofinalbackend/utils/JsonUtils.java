package aor.paj.projetofinalbackend.utils;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;


public class JsonUtils {

    private static JsonbConfig config = new JsonbConfig().withFormatting(true);
    private static Jsonb jsonb = JsonbBuilder.create(config);
    private static final String filename = "users.json";
    public static String convertObjectToJson(Object object) {
        return jsonb.toJson(object);
    }

}

