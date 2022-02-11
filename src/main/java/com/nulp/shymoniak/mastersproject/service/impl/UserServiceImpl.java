package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.entity.User;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.UserService;
import com.nulp.shymoniak.mastersproject.utility.mapping.UserMapper;
import com.nulp.shymoniak.mastersproject.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractService<User, UserDTO> implements UserService {
    private final UserRepository userRepository;

    // TODO: 2/11/22 refactor two repositories
    @Autowired
    public UserServiceImpl(UserRepository repository, UserValidator validator) {
        this.userRepository = repository;
        this.validator = validator;
        this.repository = repository;
        this.mapper = UserMapper.INSTANCE;
    }

    // TODO: 2/7/22 fill registeredDate field / change registeredDate to createDate
    // TODO: 2/11/22 refactor findById on exists by id
    // TODO: 2/8/22 remove isActive field ?

    @Override
    public UserDTO findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(item -> (UserDTO) mapper.mapToDTO(item))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }
}