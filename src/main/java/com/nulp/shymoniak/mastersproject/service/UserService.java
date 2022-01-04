package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAllUsers();

    UserDTO findByUsername(String username);

    UserDTO findUserById(Long userId);

    UserDTO createUser(UserDTO user);

    UserDTO deleteUser(Long userId);

    UserDTO updateUser(UserDTO user);
}
