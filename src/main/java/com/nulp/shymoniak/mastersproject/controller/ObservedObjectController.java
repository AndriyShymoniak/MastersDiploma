package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.service.ObservedObjectService;
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
@RequestMapping("observedObject")
public class ObservedObjectController {
    private final ObservedObjectService observedObjectService;

    @GetMapping({"", "/"})
    public ResponseEntity<Page<ObservedObjectDTO>> findAllObservedObjects(
            @PageableDefault(sort = "observedObjectId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(observedObjectService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservedObjectDTO> findObservedObjectById(@PathVariable Long id) {
        return new ResponseEntity<>(observedObjectService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ObservedObjectDTO> createObservedObject(@RequestBody ObservedObjectDTO observedObject) {
        return new ResponseEntity<>(observedObjectService.createItem(observedObject), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObservedObjectDTO> deleteObservedObject(@PathVariable Long id) {
        return new ResponseEntity<>(observedObjectService.deleteItem(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ObservedObjectDTO> updateObservedObject(@RequestBody ObservedObjectDTO observedObject) {
        return new ResponseEntity<>(observedObjectService.updateItem(observedObject), HttpStatus.CREATED);
    }
}
