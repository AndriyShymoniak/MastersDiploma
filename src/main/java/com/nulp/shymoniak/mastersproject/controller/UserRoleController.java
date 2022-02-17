package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.UserRoleDTO;
import com.nulp.shymoniak.mastersproject.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("userRole")
public class UserRoleController {
    private final UserRoleService userRoleService;

    @GetMapping({"", "/"})
    public ResponseEntity<Page<UserRoleDTO>> findAllUserRoles(
            @PageableDefault(sort = "userRoleId", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(userRoleService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDTO> findUserRoleById(@PathVariable Long id) {
        return new ResponseEntity<>(userRoleService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserRoleDTO> createUserRole(@RequestBody UserRoleDTO userRole) {
        return new ResponseEntity<>(userRoleService.createItem(userRole), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserRoleDTO> updateUserRole(@RequestBody UserRoleDTO userRole) {
        return new ResponseEntity<>(userRoleService.updateItem(userRole), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserRoleDTO> deleteUserRole(@PathVariable Long id) {
        return new ResponseEntity<>(userRoleService.deleteItem(id), HttpStatus.OK);
    }
}
