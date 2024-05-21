package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.bean.TokenBean;
import aor.paj.projetofinalbackend.bean.UserBean;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;

@ApplicationScoped
public class AuthBean {

    @Inject
    TokenBean tokenBean;

    @Inject
    UserBean userBean;

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
