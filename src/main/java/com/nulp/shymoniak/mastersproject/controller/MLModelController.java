package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.MLModelDTO;
import com.nulp.shymoniak.mastersproject.service.MLModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("mlModel")
public class MLModelController extends AbstractCrudController<MLModelDTO> {
    private final MLModelService mlModelService;

    @Autowired
    public MLModelController(MLModelService mlModelService) {
        this.abstractService = mlModelService;
        this.mlModelService = mlModelService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRIVILEGED_USER', 'ROLE_USER')")
    @GetMapping({"", "/"})
    public ResponseEntity<Page<MLModelDTO>> findAllModels(
            @PageableDefault(sort = "mlModelId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(mlModelService.findAll(pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRIVILEGED_USER', 'ROLE_USER')")
    @GetMapping("/observedObject")
    public ResponseEntity<List<MLModelDTO>> findAllModelsByObservedObject(@RequestParam Set<Long> observedObjectIdSet) {
        return new ResponseEntity<>(mlModelService.findAllModelsByObservedObject(observedObjectIdSet), HttpStatus.OK);
    }
}
