package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component 
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    
    @Override 
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        
        System.out.println("Authorization header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            String userId = jwtService.extractUserId(token);

            System.out.println("User ID from JWT: " + userId);

            User user = userRepository.findById(Long.parseLong(userId))
                    .orElseThrow();

            Authentication authentication = 
                    new UsernamePasswordAuthenticationToken(user, null, java.util.List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

            System.out.println( "Authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

            System.out.println( "Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        filterChain.doFilter(request, response);
    }
}
