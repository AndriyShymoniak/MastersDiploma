package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mlModel")
public class MLModelController {
    private final MLModelService mlModelService;

    @Autowired
    public MLModelController(MLModelService mlModelService) {
        this.mlModelService = mlModelService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<MLModelDTO>> findAllModels() {
        return new ResponseEntity<>(mlModelService.findAllModels(), HttpStatus.OK);
    }

    @GetMapping("/observedObject/")
    public ResponseEntity<List<MLModelDTO>> findAllModelsByObservedObject(@RequestBody ObservedObjectDTO observedObject) {
        return new ResponseEntity<>(mlModelService.findAllModelsByObservedObject(observedObject), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MLModelDTO> findModelById(@PathVariable Long id) {
        return new ResponseEntity<>(mlModelService.findModelById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MLModelDTO> createcModel(@RequestBody MLModelDTO user) {
        return new ResponseEntity<>(mlModelService.createModel(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MLModelDTO> deleteModel(@PathVariable Long id) {
        return new ResponseEntity<>(mlModelService.deleteModel(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MLModelDTO> updateModel(@RequestBody MLModelDTO user) {
        return new ResponseEntity<>(mlModelService.updateModel(user), HttpStatus.CREATED);
    }
}
