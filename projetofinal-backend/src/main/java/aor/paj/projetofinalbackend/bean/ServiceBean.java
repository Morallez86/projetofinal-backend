package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.ProjectHistoryDao;
import aor.paj.projetofinalbackend.dao.TaskDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.ejb.Stateless;

/**
 * Stateless bean providing utility methods for service operations.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class ServiceBean {

    /**
     * Retrieves the user ID from the JWT token.
     *
     * @param token JWT token from which the user ID is extracted.
     * @return Long value representing the user ID extracted from the token.
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = JwtUtil.validateToken(token);
        return claims.get("id", Long.class);
    }
}
