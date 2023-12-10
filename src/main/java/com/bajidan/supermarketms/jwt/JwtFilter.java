package com.bajidan.supermarketms.jwt;

import com.bajidan.supermarketms.serviceImp.CustomerUserServiceDetail;
import com.bajidan.supermarketms.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final CustomerUserServiceDetail userServiceDetail;
    private final JwtUtil jwtUtil;

    Claims claims = null;

    private String currentUsername = null;

    public JwtFilter(CustomerUserServiceDetail userServiceDetail, JwtUtil jwtUtil) {
        this.userServiceDetail = userServiceDetail;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().matches("user/signUp | user/login | user/forgetPassword")) {
            filterChain.doFilter(request, response);

        } else {
            String autHeader = request.getHeader("Authorization");
            String token = null;

            if (autHeader != null && autHeader.startsWith("Bearer ")) {
                token = autHeader.substring(7);
                currentUsername = jwtUtil.extractEmail(token);
                claims = jwtUtil.getAllClaims(token);
            }

            if (currentUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetail = userServiceDetail.loadUserByUsername(currentUsername);

                if (jwtUtil.validToken(token, userDetail)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetail, null, userDetail.getAuthorities()
                    );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser() {
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

}