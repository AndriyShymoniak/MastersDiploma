package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.service.IAbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public abstract class AbstractCrudController<DTO> {
    protected IAbstractCrudService<DTO> abstractService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRIVILEGED_USER', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<DTO> findItemById(@PathVariable Long id) {
        return new ResponseEntity<>(abstractService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<DTO> createItem(@RequestBody DTO dto) {
        return new ResponseEntity<>(abstractService.createItem(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<DTO> updateItem(@RequestBody DTO dto) {
        return new ResponseEntity<>(abstractService.updateItem(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<DTO> deleteItem(@PathVariable Long id) {
        return new ResponseEntity<>(abstractService.deleteItem(id), HttpStatus.OK);
    }

}
