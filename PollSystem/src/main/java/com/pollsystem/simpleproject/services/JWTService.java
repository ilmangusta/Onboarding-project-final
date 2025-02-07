package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.Users;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;

@Service
public class JWTService {

    private static final String SECRET_KEY = "esoeij0er9fu0923u9rfiurflokmk3hwlnnemn45np5o4nmpn";
    @Value("${app.config.expirationTime}")
    private int EXPIRATION_TIME;


    // 🔹 Genera un JWT con username
    public String generateToken(Users user) {
        System.out.println("scadenza: " + 1000*60*EXPIRATION_TIME);

        Date expireDate = new Date(System.currentTimeMillis() + 1000L *60*EXPIRATION_TIME);
        System.out.println("Data scadenza: " + expireDate);
        return Jwts.builder()
                .setSubject(user.getUsername()) // Imposta il payload con l'username
                .setIssuedAt(new Date()) // Data di emissione
                .setExpiration(new Date(System.currentTimeMillis() + 1000L *60*EXPIRATION_TIME)) // Scadenza
                .signWith(getKey(), SignatureAlgorithm.HS256) // Firma con algoritmo HMAC-SHA256
                .compact();
    }

    // 🔹 Estrai l'username dal token
    public String extractUsername(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 🔹 Controlla se il token è valido
    public boolean validateToken(String token, String username) throws ExpiredJwtException {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    // 🔹 Controlla se il token è scaduto
    private boolean isTokenExpired(String token) throws ExpiredJwtException {
        Date date = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        System.out.println("Data scadenza: " + date);
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
    }

    private Key getKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
