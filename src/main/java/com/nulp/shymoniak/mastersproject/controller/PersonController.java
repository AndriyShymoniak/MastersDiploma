package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("person")
public class PersonController extends AbstractCrudController<PersonDTO> {
    @Autowired
    public PersonController(PersonService personService) {
        this.abstractService = personService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRIVILEGED_USER', 'ROLE_USER')")
    @GetMapping({"", "/"})
    public ResponseEntity<Page<PersonDTO>> findAllPersons(
            @PageableDefault(sort = "personId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(abstractService.findAll(pageable), HttpStatus.OK);
    }
}
