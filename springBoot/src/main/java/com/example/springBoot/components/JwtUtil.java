package com.example.springBoot.components;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    private long expirationTime = 7200000; //duas horas

    public String gerarToken(String s) {
        return Jwts.builder().setSubject(s).setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + this.expirationTime))
        .signWith(SignatureAlgorithm.HS256, this.secretKey).compact();
    }
    
}
