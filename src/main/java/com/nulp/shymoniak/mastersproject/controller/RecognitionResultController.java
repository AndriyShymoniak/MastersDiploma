package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.RecognitionResultDTO;
import com.nulp.shymoniak.mastersproject.service.RecognitionResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recognitionResult")
public class RecognitionResultController {
    private final RecognitionResultService recognitionService;

    @Autowired
    public RecognitionResultController(RecognitionResultService recognitionService) {
        this.recognitionService = recognitionService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<RecognitionResultDTO>> findAllRecognitionResults() {
        return new ResponseEntity<>(recognitionService.findAllRecognitionResults(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<RecognitionResultDTO>> findRecognitionResultsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(recognitionService.findAllByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecognitionResultDTO> findRecognitionResultById(@PathVariable Long id) {
        return new ResponseEntity<>(recognitionService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecognitionResultDTO> createRecognitionResult(@RequestBody RecognitionResultDTO recognitionResult) {
        return new ResponseEntity<>(recognitionService.createRecognitionResult(recognitionResult), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecognitionResultDTO> deleteRecognitionResult(@PathVariable Long id) {
        return new ResponseEntity<>(recognitionService.deleteRecognitionResult(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RecognitionResultDTO> updateRecognitionResult(@RequestBody RecognitionResultDTO recognitionResult) {
        return new ResponseEntity<>(recognitionService.updateRecognitionResult(recognitionResult), HttpStatus.CREATED);
    }
}
