package org.example.flights.controllers;

import org.example.flights.services.CalculateTotalDistanceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
public class CalculateTotalDistanceController {

    private final CalculateTotalDistanceUseCase calculateTotalDistanceUseCase;

    public CalculateTotalDistanceController(CalculateTotalDistanceUseCase calculateTotalDistanceUseCase) {
        this.calculateTotalDistanceUseCase = calculateTotalDistanceUseCase;
    }

    @GetMapping("/total-distance")
    public ResponseEntity<?> getTotalDistance() {
        double totalDistance = calculateTotalDistanceUseCase.execute();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("totalDistanceKm", Math.round(totalDistance * 100.0) / 100.0);
        response.put("unit", "kilometers");

        return ResponseEntity.ok(response);
    }
}