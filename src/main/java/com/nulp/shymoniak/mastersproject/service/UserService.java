package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.UserDTO;

public interface UserService {

    UserDTO findByUsername(String username);

}
