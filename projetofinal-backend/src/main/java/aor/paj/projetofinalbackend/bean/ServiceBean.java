package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.ejb.Stateless;

@Stateless
public class ServiceBean {

    public Long getUserIdFromToken(String token) {
        Claims claims = JwtUtil.validateToken(token);
        return claims.get("id", Long.class);
    }
}
