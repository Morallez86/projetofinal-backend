package aor.paj.projetofinalbackend.security;

import aor.paj.projetofinalbackend.bean.TokenBean;
import io.jsonwebtoken.Claims;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@Provider
public class JwtFilter implements ContainerRequestFilter {

    @Inject
    private TokenBean tokenBean;

    private static final Logger LOGGER = Logger.getLogger(JwtFilter.class.getName());

    private static final Map<String, Set<String>> EXCLUDED_PATHS_AND_METHODS = new HashMap<>();

    static {
        Set<String> getMethods = new HashSet<>();
        getMethods.add("GET");
        EXCLUDED_PATHS_AND_METHODS.put("/projects", getMethods);

        Set<String> anyMethods = new HashSet<>();
        anyMethods.add("GET");
        anyMethods.add("POST");
        EXCLUDED_PATHS_AND_METHODS.put("/users/login", anyMethods);
        EXCLUDED_PATHS_AND_METHODS.put("/users/register", anyMethods);
        EXCLUDED_PATHS_AND_METHODS.put("/users/image", anyMethods);
        EXCLUDED_PATHS_AND_METHODS.put("/workplaces", anyMethods);
        EXCLUDED_PATHS_AND_METHODS.put("/users/emailRecoveryPassword", anyMethods);
        EXCLUDED_PATHS_AND_METHODS.put("/users/forgotPassword", anyMethods);
        EXCLUDED_PATHS_AND_METHODS.put("/users/confirmRegistration", anyMethods);
        EXCLUDED_PATHS_AND_METHODS.put("/greetings",anyMethods);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();

        Set<String> methods = EXCLUDED_PATHS_AND_METHODS.get(path);
        if (methods != null && methods.contains(method)) {
            return;
        }

        String authorizationHeader = requestContext.getHeaderString("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            LOGGER.warning("Authorization header must be provided");
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Authorization header must be provided")
                            .build());
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();
        try {
            Claims claims = JwtUtil.validateToken(token);

            if (!tokenBean.isTokenActive(token)) {
                LOGGER.warning("Token is inactive");
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("Invalid token")
                                .build());
                return;
            }

            int role = claims.get("role", Integer.class);
            requestContext.setProperty("role", role);

            LOGGER.info("Token validated successfully");
        } catch (Exception e) {
            if (e.getMessage().equals("Token has expired")) {
                tokenBean.deactivateToken(token);
            }

            LOGGER.warning("Invalid token: " + e.getMessage());
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Invalid token")
                            .build());
        }
    }
}
