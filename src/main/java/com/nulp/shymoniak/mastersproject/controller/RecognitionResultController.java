package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("recognitionResult")
public class RecognitionResultController {
    private final RecognitionResultService recognitionService;

    @GetMapping({"", "/"})
    public ResponseEntity<Page<RecognitionResultDTO>> findAllRecognitionResults(
            @PageableDefault(sort = "recognitionResultId", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(recognitionService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/groupByDate")
    public ResponseEntity<Map<LocalDateTime, List<RecognitionResultDTO>>> findAllRecognitionResultsGroupedByDate() {
        return new ResponseEntity<>(recognitionService.findAllGroupedByDate(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecognitionResultDTO>> findRecognitionResultsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(recognitionService.findAllByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecognitionResultDTO> findRecognitionResultById(@PathVariable Long id) {
        return new ResponseEntity<>(recognitionService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecognitionResultDTO> createRecognitionResult(@RequestBody RecognitionResultDTO recognitionResult) {
        return new ResponseEntity<>(recognitionService.createItem(recognitionResult), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RecognitionResultDTO> updateRecognitionResult(@RequestBody RecognitionResultDTO recognitionResult) {
        return new ResponseEntity<>(recognitionService.updateItem(recognitionResult), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecognitionResultDTO> deleteRecognitionResult(@PathVariable Long id) {
        return new ResponseEntity<>(recognitionService.deleteItem(id), HttpStatus.OK);
    }
}
