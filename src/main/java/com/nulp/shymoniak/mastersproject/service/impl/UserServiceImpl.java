package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.entity.User;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.UserService;
import com.nulp.shymoniak.mastersproject.utility.ObjectMapperUtils;
import com.nulp.shymoniak.mastersproject.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractService<UserDTO> implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapperUtils mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ObjectMapperUtils mapper, UserValidator validator) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return mapper.mapAll(users, UserDTO.class);
    }

    @Override
    public UserDTO findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(item -> mapper.map(item, UserDTO.class))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    @Override
    public UserDTO findUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(item -> mapper.map(item, UserDTO.class))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    // TODO: 2/7/22 fill registeredDate field
    @Transactional
    @Override
    public UserDTO createUser(UserDTO user) {
        User userEntity = mapper.map(user, User.class);
        userRepository.save(userEntity);
        return mapper.map(userEntity, UserDTO.class);
    }

    @Transactional
    @Override
    public UserDTO deleteUser(Long userId) {
        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        userRepository.delete(userEntity);
        return mapper.map(userEntity, UserDTO.class);
    }

    @Transactional
    @Override
    public UserDTO updateUser(UserDTO user) {
        checkIfValid(user);
        userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        userRepository.save(mapper.map(user, User.class));
        return user;
    }
}