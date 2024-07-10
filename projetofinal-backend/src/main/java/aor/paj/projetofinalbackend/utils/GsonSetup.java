package aor.paj.projetofinalbackend.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Utility class for creating a Gson instance with custom adapters for handling Java 8 Date-Time API types.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class GsonSetup {

    /**
     * Creates and configures a Gson instance with custom adapters for LocalDateTime, Instant, and LocalDate.
     *
     * @return Gson instance configured with custom adapters.
     */
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }
}