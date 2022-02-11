package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("mlModel")
public class MLModelController {
    private final MLModelService mlModelService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<MLModelDTO>> findAllModels() {
        return new ResponseEntity<>(mlModelService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/observedObject")
    public ResponseEntity<List<MLModelDTO>> findAllModelsByObservedObject(@RequestParam Set<Long> observedObjectIdSet) {
        return new ResponseEntity<>(mlModelService.findAllModelsByObservedObject(observedObjectIdSet), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MLModelDTO> findModelById(@PathVariable Long id) {
        return new ResponseEntity<>(mlModelService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MLModelDTO> createModel(@RequestBody MLModelDTO user) {
        return new ResponseEntity<>(mlModelService.createItem(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MLModelDTO> deleteModel(@PathVariable Long id) {
        return new ResponseEntity<>(mlModelService.deleteItem(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MLModelDTO> updateModel(@RequestBody MLModelDTO user) {
        return new ResponseEntity<>(mlModelService.updateItem(user), HttpStatus.CREATED);
    }
}
