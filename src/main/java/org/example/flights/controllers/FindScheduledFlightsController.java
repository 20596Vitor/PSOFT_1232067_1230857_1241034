package org.example.flights.controllers;

import org.example.flights.domain.Flight;
import org.example.flights.services.ListScheduledFlightsUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
public class FindScheduledFlightsController {

    private final ListScheduledFlightsUseCase listScheduledFlightsUseCase;

    public FindScheduledFlightsController(ListScheduledFlightsUseCase listScheduledFlightsUseCase) {
        this.listScheduledFlightsUseCase = listScheduledFlightsUseCase;
    }

    @GetMapping("/aircraft/{registrationNumber}/scheduled")
    public ResponseEntity<?> getScheduledFlightsForAircraft(
            @PathVariable String registrationNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("scheduledDatetime").descending());

            Page<Flight> flightsPage = listScheduledFlightsUseCase.execute(registrationNumber, pageable);

            return ResponseEntity.ok(flightsPage);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}