package aor.paj.projetofinalbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * Utility class for JWT (JSON Web Token) generation, validation, and extraction.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class JwtUtil {

    private static final String SECRET_KEY = "your_secret_key_your_secret_key_your_secret_key";  // Replace with your secret key
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    public static final long EXPIRATION_TIME = 3600000;

    /**
     * Generates a JWT token with specified claims.
     *
     * @param email The subject (email) of the token.
     * @param role The role claim of the token.
     * @param id The ID claim of the token.
     * @param username The username claim of the token.
     * @param projectTimestamps The project timestamps associated with the token.
     * @return Generated JWT token as a string.
     */
    public static String generateToken(String email, int role, Long id, String username, Map<Long, LocalDateTime> projectTimestamps) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("id", id)
                .claim("username", username)
                .claim("projectTimestamps", projectTimestamps)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the provided JWT token and retrieves its claims.
     *
     * @param token The JWT token to validate.
     * @return Claims extracted from the token.
     */
    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the expiration time (in milliseconds) for JWT tokens.
     *
     * @return Expiration time of JWT tokens in milliseconds.
     */
    public static long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    /**
     * Extracts the user ID from the provided JWT token.
     *
     * @param token The JWT token from which to extract the user ID.
     * @return User ID extracted from the token.
     * @throws RuntimeException If extraction of user ID fails.
     */
    public static Long extractUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("id", Long.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract user ID from token", e);
        }
    }
}
