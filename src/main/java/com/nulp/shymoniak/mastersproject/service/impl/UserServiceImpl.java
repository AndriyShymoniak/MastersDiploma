package com.nulp.shymoniak.mastersproject.service.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.UserDTO;
import com.nulp.shymoniak.mastersproject.entity.User;
import com.nulp.shymoniak.mastersproject.exception.ApiRequestException;
import com.nulp.shymoniak.mastersproject.repository.UserRepository;
import com.nulp.shymoniak.mastersproject.service.AbstractService;
import com.nulp.shymoniak.mastersproject.service.UserService;
import com.nulp.shymoniak.mastersproject.utility.mapping.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractService<UserDTO> implements UserService {
    private final UserRepository userRepository;
    private UserMapper mapper = UserMapper.INSTANCE;

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return mapper.map(users);
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(item -> mapper.map(item))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }

    // TODO: 2/7/22 fill registeredDate field / change registeredDate to createDate
    @Override
    public UserDTO createItem(UserDTO user) {
        User userEntity = mapper.map(user);
        userRepository.save(userEntity);
        return mapper.map(userEntity);
    }

    // TODO: 2/11/22 refactor findById on exists by id
    @Override
    public UserDTO updateItem(UserDTO user) {
        checkIfValid(user);
        userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        userRepository.save(mapper.map(user));
        return user;
    }

    // TODO: 2/8/22 remove isActive field ?
    @Override
    public UserDTO deleteItem(Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
        userRepository.delete(userEntity);
        return mapper.map(userEntity);
    }

    @Override
    public UserDTO findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(item -> mapper.map(item))
                .orElseThrow(() -> new ApiRequestException(ApplicationConstants.ERROR_MESSAGE_RECORD_NOT_FOUND));
    }
}