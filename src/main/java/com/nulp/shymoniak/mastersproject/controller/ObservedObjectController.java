package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.ObservedObjectDTO;
import com.nulp.shymoniak.mastersproject.service.ObservedObjectService;
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
@RequestMapping("observedObject")
public class ObservedObjectController extends AbstractCrudController<ObservedObjectDTO> {
    @Autowired
    public ObservedObjectController(ObservedObjectService observedObjectService) {
        this.abstractService = observedObjectService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRIVILEGED_USER', 'ROLE_USER')")
    @GetMapping({"", "/"})
    public ResponseEntity<Page<ObservedObjectDTO>> findAllObservedObjects(
            @PageableDefault(sort = "observedObjectId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(abstractService.findAll(pageable), HttpStatus.OK);
    }
}
