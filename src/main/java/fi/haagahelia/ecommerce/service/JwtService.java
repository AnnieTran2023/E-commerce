package fi.haagahelia.ecommerce.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {
    static final long EXPIRATIONTIME = 86400000; // 1 day in ms. Should be shorter in production.
    static final String PREFIX = "Bearer";

    // Generate secret key. Only for the demonstration purposes.
    // In production, you should read it from the application configuration.
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generate signed JWT token
    public String getToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(key)
                .compact();
    }

    // Get a token from request Authorization header,
    // verify the token, and get username
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null && token.startsWith(PREFIX)) {
            return extractUsername(token.replace(PREFIX, "").trim());
        }
        return null;
    }

    // Extract username from JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract a specific claim from JWT token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate JWT token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}