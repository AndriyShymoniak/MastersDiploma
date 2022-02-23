package com.nulp.shymoniak.mastersproject.jwt;

import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.*;


public class JwtTokenVerifier extends OncePerRequestFilter {
    // This method will be invoked for each request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(JWT_AUTHORIZATION);
        if (authorizationHeader.isBlank() || !authorizationHeader.startsWith(JWT_BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        try {
            // Extracting and decoding token
            token = authorizationHeader.replace(JWT_BEARER, "");
            Jwt<Header, Claims> headerClaimsJwt = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJwt(token);
            // Receiving username from token
            String username = headerClaimsJwt.getBody().getSubject();
            // Receiving all user authorities
            List<Map<String, String>> authorities = (List<Map<String, String>>) headerClaimsJwt.getBody().get(JWT_AUTHORITIES);
            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = authorities.stream()
                    .map(item -> new SimpleGrantedAuthority(item.get(JWT_AUTHORITY)))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthoritySet
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException ex) {
            throw new ApiRequestException("Token cannot be trusted: " + token);
        }
    }
}
