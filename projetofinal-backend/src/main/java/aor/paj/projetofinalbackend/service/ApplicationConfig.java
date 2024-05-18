package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.security.JwtFilter;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class ApplicationConfig extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(UserService.class);
        resources.add(JwtFilter.class);
        return resources;
    }
}
