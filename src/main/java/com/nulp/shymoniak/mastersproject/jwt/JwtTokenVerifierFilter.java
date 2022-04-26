package com.nulp.shymoniak.mastersproject.jwt;

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
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(config.getAuthorizationHeader());
        // validates token
        if (isAuthorizationHeaderNotValid(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        Jwt<Header, Claims> token = decodeToken(authHeader);
        String username = token.getBody().getSubject();
        Set<SimpleGrantedAuthority> authorities = extractAuthoritiesFromToken(token);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response); // passes to the next filter of the application
    }

    /**
     * Checks whether authorization header is not valid
     * @param authorizationHeader
     * @return true if authorization header is not valid
     */
    private boolean isAuthorizationHeaderNotValid(String authorizationHeader){
        return authorizationHeader.isBlank()
                || !authorizationHeader.startsWith(config.getTokenPrefix());
    }

    /**
     * Extracting and decoding token from authorization header
     * @param authorizationHeader
     * @return decoded token
     */
    private Jwt<Header, Claims> decodeToken(String authorizationHeader) {
        try {
            String token = authorizationHeader.replace(config.getTokenPrefix(), "");
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parse(token);
        } catch (IllegalArgumentException ex) {
            throw new JwtException("Unable to fetch Java Web Token");
        } catch (ExpiredJwtException ex) {
            throw new JwtException("Java Web Token is expired");
        }
    }

    /**
     * Extracts set of SimpleGrantedAuthority from JWT
     * @param token JWT
     * @return authorities
     */
    private Set<SimpleGrantedAuthority> extractAuthoritiesFromToken(Jwt<Header, Claims> token) {
        List<Map<String, String>> authorities = (List<Map<String, String>>) token.getBody().get(JWT_AUTHORITIES);
        return authorities.stream()
                .map(item -> new SimpleGrantedAuthority(item.get(JWT_AUTHORITY)))
                .collect(Collectors.toSet());
    }
}
