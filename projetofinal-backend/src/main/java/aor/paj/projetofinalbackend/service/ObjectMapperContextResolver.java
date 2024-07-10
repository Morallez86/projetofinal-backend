package aor.paj.projetofinalbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

/**
 * Custom ContextResolver implementation for configuring Jackson's ObjectMapper.
 * This class ensures that JavaTimeModule is registered and disables writing dates as timestamps.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    /**
     * Constructs an instance of ObjectMapperContextResolver.
     * Initializes the ObjectMapper with JavaTimeModule and disables WRITE_DATES_AS_TIMESTAMPS.
     */
    public ObjectMapperContextResolver() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Provides the configured ObjectMapper instance to the application context.
     *
     * @param type the class of the object (ignored by this method)
     * @return the configured ObjectMapper instance
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
