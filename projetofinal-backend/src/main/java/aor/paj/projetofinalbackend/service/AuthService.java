package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.TokenBean;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;

public class AuthService {

    @Inject
    TokenBean tokenBean;

    public Response validateToken(String tokenValue, SecurityContext securityContext) {
        TokenEntity token = tokenBean.findTokenByValue(tokenValue);

        if (token != null) {
            Claims claims = JwtUtil.validateToken(tokenValue);

            if (claims != null && LocalDateTime.now().isBefore(token.getExpirationTime())) {
                String username = claims.getSubject();
                // Additional logic to validate user context and roles
                return Response.ok().build();
            } else {
                // Token has expired
                tokenBean.deleteToken(tokenValue);
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
