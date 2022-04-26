package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.entity.ApplicationUser;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.ApplicationUserRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.ApplicationUserService;
import com.nulp.shymoniak.mastersproject.mapping.UserMapper;
import com.nulp.shymoniak.mastersproject.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nulp.shymoniak.mastersproject.constant.ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND;

@Service
public class ApplicationUserServiceImpl extends AbstractService<ApplicationUser, ApplicationUserDTO> implements ApplicationUserService, UserDetailsService {
    private ApplicationUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserRepository repository, UserValidator validator, PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.repository = repository;
        this.validator = validator;
        this.mapper = UserMapper.INSTANCE;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApplicationUserDTO createItem(ApplicationUserDTO userDTO) {
        ApplicationUser user = (ApplicationUser) mapper.mapToEntity(userDTO);
        user.addPerson(user.getPerson());
        ApplicationUser result = userRepository.save(user);
        return (ApplicationUserDTO) mapper.mapToDTO(result);
    }

    @Override
    public ApplicationUserDTO deleteItem(Long id) {
        Optional<ApplicationUser> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new ApiRequestException(ERROR_MESSAGE_RECORD_NOT_FOUND);
        }
        ApplicationUser user = optionalEntity.get();
        user.removePerson();
        repository.deleteById(id);
        return (ApplicationUserDTO) mapper.mapToDTO(user);
    }

    @Override
    public ApplicationUserDTO findByUsername(String username) {
        Optional<ApplicationUser> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(item -> (ApplicationUserDTO) mapper.mapToDTO(item))
                .orElseThrow(() -> new ApiRequestException("There is no such user with username: " + username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> optUser = userRepository.findByUsername(username);
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