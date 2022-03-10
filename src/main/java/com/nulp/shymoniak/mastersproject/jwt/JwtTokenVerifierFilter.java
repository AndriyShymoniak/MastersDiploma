package com.nulp.shymoniak.mastersproject.jwt;

import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.JWT_AUTHORITIES;
import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.JWT_AUTHORITY;

@RequiredArgsConstructor
public class JwtTokenVerifierFilter extends OncePerRequestFilter {
    private final SecretKey secretKey;
    private final JwtConfig config;

    // This method will be invoked for each request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(config.getAuthorizationHeader());
        if (authorizationHeader.isBlank() || !authorizationHeader.startsWith(config.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        try {
            // Extracting and decoding token
            token = authorizationHeader.replace(config.getTokenPrefix(), "");
            Jwt<Header, Claims> headerClaimsJwt = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parse(token);
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
        filterChain.doFilter(request, response);    // passes to the next filter of the application
    }
}
