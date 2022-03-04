package com.nulp.shymoniak.mastersproject.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.*;

@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfig config;
    private final SecretKey secretKey;

    // Receiving credentials from client and validating them
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernamePasswordAuthenticationRequest authRequest = new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordAuthenticationRequest.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    // Generating and sending a token to a client after successful validation
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())                                       // username of client
                .claim(JWT_AUTHORITIES, authResult.getAuthorities())                    // role of client
                .setIssuedAt(new Date())                                                // token start date
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))     // token end date
                .signWith(secretKey)                                                    // setting secret encryption key
                .compact();
        response.addHeader(config.getAuthorizationHeader(), config.getTokenPrefix() + token);
    }
}
