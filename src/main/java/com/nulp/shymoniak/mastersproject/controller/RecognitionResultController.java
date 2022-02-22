package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("recognitionResult")
public class RecognitionResultController extends AbstractCrudController<RecognitionResultDTO> {
    private final RecognitionResultService recognitionService;

    @Autowired
    public RecognitionResultController(RecognitionResultService recognitionService) {
        this.abstractService = recognitionService;
        this.recognitionService = recognitionService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRIVILEGED_USER', 'ROLE_USER')")
    @GetMapping({"", "/"})
    public ResponseEntity<Page<RecognitionResultDTO>> findAllRecognitionResults(
            @PageableDefault(sort = "recognitionResultId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(recognitionService.findAll(pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRIVILEGED_USER', 'ROLE_USER')")
    @GetMapping("/groupByDate")
    public ResponseEntity<Map<LocalDateTime, List<RecognitionResultDTO>>> findAllRecognitionResultsGroupedByDate() {
        return new ResponseEntity<>(recognitionService.findAllGroupedByDate(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRIVILEGED_USER', 'ROLE_USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecognitionResultDTO>> findRecognitionResultsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(recognitionService.findAllByUserId(userId), HttpStatus.OK);
    }
}
