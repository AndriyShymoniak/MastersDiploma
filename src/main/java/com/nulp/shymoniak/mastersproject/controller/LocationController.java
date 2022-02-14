package com.nulp.shymoniak.mastersproject.controller;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("location")
public class LocationController {
    private final LocationService locationService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<LocationDTO>> findAllLocations() {
        return new ResponseEntity<>(locationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> findLocationById(@PathVariable Long id) {
        return new ResponseEntity<>(locationService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO location) {
        return new ResponseEntity<>(locationService.createItem(location), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO location) {
        return new ResponseEntity<>(locationService.updateItem(location), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LocationDTO> deleteLocation(@PathVariable Long id) {
        return new ResponseEntity<>(locationService.deleteItem(id), HttpStatus.OK);
    }
}
