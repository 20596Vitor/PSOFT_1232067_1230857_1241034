package org.example.aircraft.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.aircraft.domain.AircraftModel;
import org.example.aircraft.services.AircraftModelService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/aircraft-models")
public class AircraftModelController {

    private final AircraftModelService aircraftModelService;

    public AircraftModelController(AircraftModelService aircraftModelService) {
        this.aircraftModelService = aircraftModelService;
    }

    public record UpdateAircraftModelRequest(
            String manufacturer,
            String modelName,
            Float cruisingSpeed,
            Float fuelCapacity,
            Float maxRange
    ) {}

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerAircraftModel(
            @RequestPart("model") AircraftModel aircraftModel,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            AircraftModel newModel = aircraftModelService.createAircraftModel(aircraftModel, image);
            return new ResponseEntity<>(newModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAircraftModel(
            @PathVariable("id") Long id,
            @RequestBody UpdateAircraftModelRequest request) {
        try {
            AircraftModel updatedModel = aircraftModelService.updateAircraftModelSpecs(
                    id,
                    request.manufacturer(),
                    request.modelName(),
                    request.cruisingSpeed(),
                    request.fuelCapacity(),
                    request.maxRange()
            );
            return new ResponseEntity<>(updatedModel, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            HttpStatus status = e.getMessage().contains("não encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(e.getMessage(), status);
        }
    }
}