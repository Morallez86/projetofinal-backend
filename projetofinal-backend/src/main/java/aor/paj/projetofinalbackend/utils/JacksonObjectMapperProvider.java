package aor.paj.projetofinalbackend.utils;

import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provider class for Jackson ObjectMapper used by JAX-RS to serialize and deserialize JSON objects.
 * This class is annotated with Provider to register it as a provider for JAX-RS.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper mapper;

    /**
     * Constructs a new instance of JacksonObjectMapperProvider.
     * Initializes the ObjectMapper using a configured instance obtained from ObjectMapperConfigurator.
     */
    public JacksonObjectMapperProvider() {
        mapper = ObjectMapperConfigurator.configureJackson();
    }

    /**
     * Provides the ObjectMapper instance for the given type.
     *
     * @param type The class of the object to serialize or deserialize.
     * @return The ObjectMapper instance configured for JSON processing.
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}