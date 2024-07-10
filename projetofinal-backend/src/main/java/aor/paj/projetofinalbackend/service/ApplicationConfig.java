package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.security.JwtFilter;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Configuration class for the JAX-RS application.
 * Specifies the base path for RESTful services and registers resources and filters.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationPath("/rest")
public class ApplicationConfig extends Application{

    /**
     * Retrieves the set of classes (resources and filters) to be registered by JAX-RS.
     *
     * @return Set of classes to be registered by JAX-RS.
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(UserService.class);
        resources.add(WorkplaceService.class);
        resources.add(JwtFilter.class);
        resources.add(SkillService.class);
        resources.add(InterestService.class);
        resources.add(ProjectService.class);
        resources.add(ComponentService.class);
        resources.add(ResourceService.class);
        resources.add(TaskService.class);
        resources.add(ProjectHistoryService.class);
        resources.add(MessageService.class);
        resources.add(NotificationService.class);
        resources.add(PdfService.class);
        resources.add(GreetingResource.class);
        return resources;
    }
}
