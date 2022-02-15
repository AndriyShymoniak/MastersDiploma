package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.MediaDTO;
import com.nulp.shymoniak.mastersproject.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("media")
public class MediaController {
    private final MediaService mediaService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<MediaDTO>> findAllMedias() {
        return new ResponseEntity<>(mediaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaDTO> findMediaById(@PathVariable Long id) {
        return new ResponseEntity<>(mediaService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MediaDTO> createMedia(@RequestBody MediaDTO media) {
        return new ResponseEntity<>(mediaService.createItem(media), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MediaDTO> updateMedia(@RequestBody MediaDTO media) {
        return new ResponseEntity<>(mediaService.updateItem(media), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MediaDTO> deleteMedia(@PathVariable Long id) {
        return new ResponseEntity<>(mediaService.deleteItem(id), HttpStatus.OK);
    }
}