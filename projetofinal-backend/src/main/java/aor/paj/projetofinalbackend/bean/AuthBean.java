package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;

/**
 * Class that deals with authentication before accessing endpoints and returns a response.
 * It checks if the token is valid, not expired, and if the user associated with
 * the token exists in the system.
 *
 * @see TokenBean
 * @see UserBean
 * @see JwtUtil
 * @see TokenEntity
 * @see UserEntity
 * @see Response
 * @see ContainerRequestContext
 * @see LocalDateTime
 * @see Claims
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationScoped
public class AuthBean {

    @Inject
    private TokenBean tokenBean;

    @Inject
    private UserBean userBean;

    @Context
    private ContainerRequestContext requestContext;

    /**
     * Validates the user token.
     * This method checks if the token is valid, not expired, and if the user associated with the token exists.
     * If these checks fail, it returns a response indicating the failure.
     *
     * @param tokenValue the token to validate
     * @return a Response indicating the result of the validation
     *         - 200 OK if the token is valid
     *         - 401 UNAUTHORIZED if the token is invalid, expired, or the user is not found
     */
    public Response validateUserToken(String tokenValue) {
        TokenEntity token = tokenBean.findTokenByValue(tokenValue);

        if (token == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        Claims claims;
        try {
            claims = JwtUtil.validateToken(tokenValue);
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        if (LocalDateTime.now().isAfter(token.getExpirationTime())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token has expired").build();
        }

        String email = claims.getSubject();
        UserEntity user = userBean.findUserByEmail(email);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User not found").build();
        }

        return Response.ok().build();
    }
}
