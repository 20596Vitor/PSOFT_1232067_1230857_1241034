package org.example.aircraft.controller;

import org.example.aircraft.domain.Aircraft;
import org.example.aircraft.domain.AircraftStatus;
import org.example.aircraft.services.AircraftService;
import org.example.flights.domain.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    public record CreateAircraftRequest(
            String registrationNumber,
            LocalDate manufacturingDate,
            Integer seatingCapacity,
            AircraftStatus status,
            Long modelId
    ) {
    }
    public record UpdateStatusRequest(AircraftStatus status) {}

    @PostMapping
    public ResponseEntity<?> registerAircraft(@RequestBody CreateAircraftRequest request) {
        try {
            Aircraft newAircraft = aircraftService.registerAircraft(
                    request.registrationNumber(),
                    request.manufacturingDate(),
                    request.seatingCapacity(),
                    request.status(),
                    request.modelId()
            );

            return new ResponseEntity<>(newAircraft, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{registrationNumber}")
    public ResponseEntity<?> getAircraftDetails(@PathVariable("registrationNumber") String registrationNumber) {
        try {
            Aircraft aircraft = aircraftService.getAircraftByRegistration(registrationNumber);
            return new ResponseEntity<>(aircraft, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Se o avião não existir, o erro é apanhado aqui e devolvemos um 404 NOT FOUND
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<Aircraft>> searchAircrafts(
            @RequestParam(value = "modelName", required = false) String modelName,
            @RequestParam(value = "status", required = false) AircraftStatus status,
            @RequestParam(value = "year", required = false) Integer year) {

        List<Aircraft> result = aircraftService.searchAircrafts(modelName, status, year);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PatchMapping("/{registrationNumber}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable("registrationNumber") String registrationNumber,
            @RequestBody UpdateStatusRequest request) {
        try {
            Aircraft updatedAircraft = aircraftService.updateAircraftStatus(registrationNumber, request.status());
            return new ResponseEntity<>(updatedAircraft, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{registrationNumber}/compatible-routes")
    public ResponseEntity<?> getCompatibleRoutes(@PathVariable("registrationNumber") String registrationNumber) {
        try {
            List<Route> compatibleRoutes = aircraftService.getCompatibleRoutesForAircraft(registrationNumber);
            return new ResponseEntity<>(compatibleRoutes, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/availability-report")
    public ResponseEntity<Map<String, Long>> getFleetAvailabilityStatus() {
        Map<String, Long> report = aircraftService.getFleetStatusSummary();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    @GetMapping("/operational-hours")
    public ResponseEntity<Map<String, Double>> getFleetOperationalHours() {
        Map<String, Double> report = aircraftService.getFleetOperationalHours();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    @PatchMapping("/{registrationNumber}/add-hours")
    public ResponseEntity<?> addOperationalHours(
            @PathVariable("registrationNumber") String registrationNumber,
            @RequestParam("hours") Double hours) {
        try {
            Aircraft updatedAircraft = aircraftService.addOperationalHours(registrationNumber, hours);
            return new ResponseEntity<>(updatedAircraft, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            HttpStatus status = e.getMessage().contains("não encontrada") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(e.getMessage(), status);
        }
    }
}
