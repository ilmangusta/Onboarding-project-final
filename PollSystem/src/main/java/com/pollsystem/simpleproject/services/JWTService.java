package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.Users;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JWTService {

    private static final String SECRET_KEY = "esoeij0er9fu0923u9rfiurflokmk3hwlnnemn45np5o4nmpn";
    private static final String time = System.getenv("SYSTEM_EXPIRATION_TIME");
    @Value("${SYSTEM_EXPIRATION_TIME}")
    private static String SYSTEM_EXPIRATION_TIME;
    private static final long EXPIRATION_TIME = (1000 * 60) * 100; //Integer.parseInt(time); // 10 ore


    // 🔹 Genera un JWT con username
    public String generateToken(Users user) {

        Map<String, Objects> mapToken = new HashMap<>();
        String username = user.getUsername();

        String token = Jwts.builder()
                .setSubject(user.getUsername()) // Imposta il payload con l'username
                //.setSubject(user.getPassword())
                .setIssuedAt(new Date()) // Data di emissione
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Scadenza
                .signWith(getKey(), SignatureAlgorithm.HS256) // Firma con algoritmo HMAC-SHA256
                .compact();

        Date date = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        System.out.println("Data scadenza: " + date);

        return token;
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
