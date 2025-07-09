package com.example.springBoot.components;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Value("${jwt.secret}")
    private String secretKey;

    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse 
    response, @NonNull FilterChain filterChain) throws IOException, ServletException {
        
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        Claims claims = null;
        String cpf = null;
        UsernamePasswordAuthenticationToken authToken = null;

        if (request.getServletPath().equals("/autenticacao/login") || 
        request.getServletPath().equals("/api/cadastro-funcionario")) {
            filterChain.doFilter(request, response); // ignora o login
            return;
        }

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);

            try{
                claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
                cpf = claims.getSubject();

                if(cpf != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    authToken = new UsernamePasswordAuthenticationToken(cpf, null, Collections.emptyList());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }

            catch(Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"erro\": \"Token inválido ou expirado\"}");
                return;
                
            }
        }

        filterChain.doFilter(request, response);


    }
    
}
