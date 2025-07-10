package com.example.springBoot.components;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        String cpf = "";
        String token = "";
        Claims claims = null;
        String authorizationHeader = null;
        SecretKey key = null;
        UsernamePasswordAuthenticationToken authToken = null;
        List<GrantedAuthority> authorities = null;

        // Ignorar rotas públicas
        if (path.equals("/api/login") || path.equals("/api/funcionarios")) {
            filterChain.doFilter(request, response);
            return;
        }

        authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);

            try {
                key = Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));

                claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
            

                cpf = claims.getSubject();

                if (cpf != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    authorities = List.of(new SimpleGrantedAuthority("USER"));

                    authToken = new UsernamePasswordAuthenticationToken(cpf, 
                    null, authorities);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }

            } 
        
            catch (Exception e) {
                System.out.println("Erro ao validar o token: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado");
                return;
            }
        }

        // Sempre permita que a requisição continue
        filterChain.doFilter(request, response);
    }
}
