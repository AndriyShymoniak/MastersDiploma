package com.nulp.shymoniak.mastersproject.utility.impl;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.service.ApplicationUserService;
import com.nulp.shymoniak.mastersproject.utility.SecuritySessionUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecuritySessionUtilityImpl implements SecuritySessionUtility {
    private final ApplicationUserService userService;

    @Override
    public ApplicationUserDTO getCurrentUserFromSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApplicationUserDTO currentUser = userService.findByUsername(authentication.getName());
        return currentUser;
    }
}
