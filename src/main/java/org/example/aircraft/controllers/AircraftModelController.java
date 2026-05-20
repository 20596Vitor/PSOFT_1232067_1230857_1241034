package org.example.aircraft.controllers;

import org.example.aircraft.domain.AircraftModel;
import org.example.aircraft.services.AircraftModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aircraft-models")
public class AircraftModelController {

    private final AircraftModelService service;

    public AircraftModelController(AircraftModelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createAircraftModel(@RequestBody AircraftModel newModel) {
        try {
            AircraftModel createdModel = service.createAircraftModel(newModel);

            return new ResponseEntity<>(createdModel, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}