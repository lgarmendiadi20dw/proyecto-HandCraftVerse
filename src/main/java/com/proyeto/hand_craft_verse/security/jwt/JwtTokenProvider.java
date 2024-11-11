package com.proyeto.hand_craft_verse.security.jwt;


import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {


    @Autowired
    SecretKey key;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        try {
            String username = Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();          
            return username;

        } catch (Exception ex) {
            throw new RuntimeException("Unable to get username from token", ex);
        }
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(authToken);

            return true;
        } catch (io.jsonwebtoken.security.SecurityException ex) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            throw new RuntimeException("Expired JWT token");
        } catch (io.jsonwebtoken.UnsupportedJwtException ex) {
            throw new RuntimeException("Unsupported JWT token");
        } catch (io.jsonwebtoken.MalformedJwtException ex) {
            throw new RuntimeException("Malformed JWT token");
        } catch (Exception ex) {
            throw new RuntimeException("Invalid JWT token");
        }
    }
}