package com.jobs.jobportal.Security;
import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jobs.jobportal.model.User;
import com.jobs.jobportal.repository.UserRepo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private UserRepo userRepository;

    public JwtRequestFilter(JwtUtil jwtUtil, UserRepo userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                String email = jwtUtil.extractEmail(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    Optional<User> userOpt = userRepository.findByEmail(email); // fetch from DB

                    if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        if(jwtUtil.validateToken(token, user.getEmail())) {
                            UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        user.getEmail(),
                                        null,
                                        java.util.Collections.emptyList()
                                );

                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }                        
                    }
                }
            } catch (Exception ignored) {
                // Do NOT manually send error
                // Just let Spring Security handle it
            }
        }
        filterChain.doFilter(request, response);
    }
}
