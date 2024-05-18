package aor.paj.projetofinalbackend.security;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Provider
public class JwtFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtFilter.class.getName());

    // Set of paths to be excluded from JWT validation
    private static final Set<String> EXCLUDED_PATHS = new HashSet<>();

    static {
        EXCLUDED_PATHS.add("/users/login");
        EXCLUDED_PATHS.add("/users/register");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        // Allow unauthenticated access to the login and registration endpoints
        if (EXCLUDED_PATHS.contains(path)) {
            return;
        }

        // Check for Authorization header and validate JWT token
        String authorizationHeader = requestContext.getHeaderString("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            LOGGER.warning("Authorization header must be provided");
            requestContext.abortWith(
                    jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.UNAUTHORIZED)
                            .entity("Authorization header must be provided")
                            .build());
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();
        try {
            JwtUtil.validateToken(token);
            LOGGER.info("Token validated successfully");
        } catch (Exception e) {
            LOGGER.warning("Invalid token: " + e.getMessage());
            requestContext.abortWith(
                    jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.UNAUTHORIZED)
                            .entity("Invalid token")
                            .build());
        }
    }
}
