package aor.paj.projetofinalbackend.security;

import aor.paj.projetofinalbackend.bean.TokenBean;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.pojo.ResponseMessage;
import aor.paj.projetofinalbackend.utils.JsonUtils;
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

/**
 * JWT filter to authenticate and authorize incoming requests based on JWT tokens.
 * Excludes certain paths and methods from JWT validation.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Provider
public class JwtFilter implements ContainerRequestFilter {

    @Inject
    private TokenBean tokenBean;

    private static final Logger LOGGER = Logger.getLogger(JwtFilter.class.getName());

    // Paths and methods excluded from JWT validation
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
        EXCLUDED_PATHS_AND_METHODS.put("/greetings", anyMethods);
    }

    /**
     * Filters incoming HTTP requests.
     *
     * @param requestContext The context of the incoming request.
     * @throws IOException If an I/O exception occurs.
     */
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

            // Extend the expiration time
            tokenBean.extendTokenExpirationTime(token);

            int role = claims.get("role", Integer.class);
            requestContext.setProperty("role", role);

            LOGGER.info("Token validated successfully");
        } catch (Exception e) {
            if (e.getMessage().equals("Token has expired")) {
                tokenBean.deactivateToken(token);
                requestContext.abortWith(
                        Response.status(401)
                                .entity(JsonUtils.convertObjectToJson(new ResponseMessage("Unauthorized")))
                                .build());
                return;
            }

            LOGGER.warning("Invalid token: " + e.getMessage());
            requestContext.abortWith(
                    Response.status(401)
                            .entity(JsonUtils.convertObjectToJson(new ResponseMessage("Invalid token")))
                            .build());
        }
    }
}
