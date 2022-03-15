package com.nulp.shymoniak.mastersproject.service;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;

public interface ApplicationUserService extends AbstractCrudService<ApplicationUserDTO> {

    ApplicationUserDTO findByUsername(String username);

}
