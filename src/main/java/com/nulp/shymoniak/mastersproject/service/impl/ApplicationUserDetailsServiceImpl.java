package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.ApplicationUserRepository;
import com.nulp.shymoniak.mastersproject.service.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsServiceImpl implements ApplicationUserDetailsService {
    private final ApplicationUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> optUser = repository.findByUsername(username);
        return optUser.map(
                        aplUser -> User.builder()
                                .username(aplUser.getUsername())
                                .password(passwordEncoder.encode(aplUser.getPassword()))
                                .roles(aplUser.getRole().name())
                                .build()
                )
                .orElseThrow(() -> new ApiRequestException("There is no such user with username: " + username));
    }
}
