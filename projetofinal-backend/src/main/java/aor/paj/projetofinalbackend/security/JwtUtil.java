package aor.paj.projetofinalbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET_KEY = "your_secret_key_your_secret_key_your_secret_key";  // Replace with your secret key
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    public static final long EXPIRATION_TIME = 3600000;

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

    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static long getExpirationTime() {
        return EXPIRATION_TIME;
    }

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
