package aor.paj.projetofinalbackend.security;

import aor.paj.projetofinalbackend.bean.TokenBean;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Provider
public class JwtFilter implements ContainerRequestFilter {

    @Inject
    private TokenBean tokenBean;

    private static final Logger LOGGER = Logger.getLogger(JwtFilter.class.getName());

    // Set of paths to be excluded from JWT validation
    private static final Set<String> EXCLUDED_PATHS = new HashSet<>();

    static {
        EXCLUDED_PATHS.add("/users/login");
        EXCLUDED_PATHS.add("/users/register");
        EXCLUDED_PATHS.add("/users/image");
        EXCLUDED_PATHS.add("/workplaces/all");
        EXCLUDED_PATHS.add("/users/emailRecoveryPassword");
        EXCLUDED_PATHS.add("/users/forgotPassword");
        EXCLUDED_PATHS.add("/users/confirmRegistration");
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
            System.out.println(token);

            if (!tokenBean.isTokenActive(token)) {
                LOGGER.warning("Token is inactive");
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("Invalid token")
                                .build());
                return;
            }
            System.out.println("3");
            LOGGER.info("Token validated successfully");
        } catch (Exception e) {
            // Deactivate token if expired
            System.out.println("nope");
            if (e.getMessage().equals("Token has expired")) {
                tokenBean.deactivateToken(token);
            }

            LOGGER.warning("Invalid token: " + e.getMessage());
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Invalid token")
                            .build());
        }
        System.out.println("4");
    }
}
