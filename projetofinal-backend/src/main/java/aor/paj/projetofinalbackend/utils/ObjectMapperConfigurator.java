package aor.paj.projetofinalbackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Configurator class for setting up an ObjectMapper instance with specific configurations.
 * This class configures ObjectMapper to support Java 8 Date/Time API and serialize dates as ISO strings.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ObjectMapperConfigurator {

    /**
     * Configures and returns an ObjectMapper instance with the following settings:
     * - Registers JavaTimeModule to support Java 8 Date/Time API.
     * - Disables SerializationFeature.WRITE_DATES_AS_TIMESTAMPS to serialize dates as ISO strings.
     *
     * @return Configured ObjectMapper instance.
     */
    public static ObjectMapper configureJackson() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());  // Support for Java 8 Date/Time API
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // To serialize dates as ISO strings
        return mapper;
    }
}
