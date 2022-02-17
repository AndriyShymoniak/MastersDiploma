package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<MLModelDTO>> findAllModels(
            @PageableDefault(sort = "mlModelId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(mlModelService.findAll(pageable), HttpStatus.OK);
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
    public ResponseEntity<MLModelDTO> createModel(@RequestBody MLModelDTO mlModel) {
        return new ResponseEntity<>(mlModelService.createItem(mlModel), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MLModelDTO> updateModel(@RequestBody MLModelDTO mlModel) {
        return new ResponseEntity<>(mlModelService.updateItem(mlModel), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MLModelDTO> deleteModel(@PathVariable Long id) {
        return new ResponseEntity<>(mlModelService.deleteItem(id), HttpStatus.OK);
    }
}
