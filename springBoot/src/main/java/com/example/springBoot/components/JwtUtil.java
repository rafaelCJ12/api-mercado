package com.example.springBoot.components;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private long expirationTime = 7200000; // duas horas

    public String gerarToken(String s) {
        SecretKey key = Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(s)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.expirationTime))
                .signWith(key, SignatureAlgorithm.HS256) // nova forma
                .compact();
    }
}
