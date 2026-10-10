package ma.enset.tpjwtspring.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret); //
        return Keys.hmacShaKeyFor(keyBytes); //[cite: 2]
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder() //[cite: 2]
                .subject(userDetails.getUsername()) //[cite: 2]
                .issuedAt(new Date()) //[cite: 2]
                .expiration(new Date(System.currentTimeMillis() + expiration)) //[cite: 2]
                .signWith(getSigningKey()) //[cite: 2]
                .compact(); //[cite: 2]
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); //[cite: 2]
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser() //[cite: 3]
                .verifyWith(getSigningKey()) //[cite: 3]
                .build() //[cite: 3]
                .parseSignedClaims(token) //[cite: 3]
                .getPayload(); //[cite: 3]
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token); //[cite: 3]
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token); //[cite: 3]
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration(); //[cite: 3]
        return expiration.before(new Date()); //[cite: 3]
    }
}