package com.airtribe.ShareMyRecipe.config.filter;

import com.airtribe.ShareMyRecipe.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final List<String> whitelist = Arrays.asList(
            "/api/v1/chefs/auth/**",
            "/api/v1/users/auth/**",
            "/api/v1/admins/auth/**",
            "/h2-console/**",
            "/actuator/health"
    );

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtAuthorizationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token is missing");
            return; // stop further processing
        }

        // Check for Bearer token (case-insensitive)
        String prefix = "Bearer ";
        if (!authorizationHeader.regionMatches(true, 0, prefix, 0, prefix.length())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token is Invalid");
            return;
        }

        String token = authorizationHeader.substring(prefix.length()).trim();
        try {
            Claims claims = jwtTokenUtil.validateToken(token);

            // setting the claim in the SecurityContextHolder
            String username = claims.getSubject();
            List<String> role = claims.get("roles", List.class);
            List<SimpleGrantedAuthority> authorities = role.stream()
                    .map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token validation failed "+ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        for(String pattern: whitelist){
            if(pathMatcher.match(pattern, path)){
                return true;
            }
        }
        return false;
    }
}
