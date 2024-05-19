package aor.paj.projetofinalbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "your_secret_key_your_secret_key_your_secret_key";  // Replace with your secret key
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    public static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(String email, Character role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
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
}
