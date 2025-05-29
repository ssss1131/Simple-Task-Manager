package kz.ssss.taskmanager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kz.ssss.taskmanager.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${token.jwt.key}")
    private String jwtKey;

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        if (user instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
        }
        return generateToken(claims, user);
    }

    private String generateToken(Map<String, Object> claims, UserDetails user) {
        return Jwts.builder().claims(claims).subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //30min
                .signWith(getSigningKey()).compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = (SecretKey) getSigningKey();
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        Date date = extractClaim(token, Claims::getExpiration);
        return (userName.equals(userDetails.getUsername())) && date.after(new Date());
    }
}
