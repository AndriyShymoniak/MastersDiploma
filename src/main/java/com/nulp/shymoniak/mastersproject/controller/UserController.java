package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.ApplicationUserDTO;
import com.nulp.shymoniak.mastersproject.service.UserService;
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
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @GetMapping({"", "/"})
    public ResponseEntity<Page<ApplicationUserDTO>> findAllUsers(
            @PageableDefault(sort = "userId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUserDTO> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApplicationUserDTO> findUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApplicationUserDTO> createUser(@RequestBody ApplicationUserDTO user) {
        return new ResponseEntity<>(userService.createItem(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApplicationUserDTO> updateUser(@RequestBody ApplicationUserDTO user) {
        return new ResponseEntity<>(userService.updateItem(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApplicationUserDTO> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.deleteItem(id), HttpStatus.OK);
    }
}
