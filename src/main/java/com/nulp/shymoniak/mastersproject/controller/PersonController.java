package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.PersonDTO;
import com.nulp.shymoniak.mastersproject.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("person")
public class PersonController {
    private final PersonService personService;

    @GetMapping({"", "/"})
    public ResponseEntity<Page<PersonDTO>> findAllPersons(
            @PageableDefault(sort = "personId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(personService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findPersonById(@PathVariable Long id) {
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO person) {
        return new ResponseEntity<>(personService.createItem(person), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO person) {
        return new ResponseEntity<>(personService.updateItem(person), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonDTO> deletePerson(@PathVariable Long id) {
        return new ResponseEntity<>(personService.deleteItem(id), HttpStatus.OK);
    }
}
