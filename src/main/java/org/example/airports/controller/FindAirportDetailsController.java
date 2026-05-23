package org.example.airports.controller;

import org.example.airports.domain.Airport;
import org.example.airports.services.ViewAirportDetailsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airports")
public class FindAirportDetailsController {

    private final ViewAirportDetailsUseCase viewAirportDetailsUseCase;

    public FindAirportDetailsController(ViewAirportDetailsUseCase viewAirportDetailsUseCase) {
        this.viewAirportDetailsUseCase = viewAirportDetailsUseCase;
    }
    @GetMapping("/{iataCode}")
    public ResponseEntity<?> getAirportDetails(@PathVariable String iataCode) {

        if (iataCode == null || iataCode.trim().length() != 3) {
            return ResponseEntity.badRequest().body("Erro de Validação: O código IATA deve conter exatamente 3 letras.");
        }

        try {
            Airport airport = viewAirportDetailsUseCase.execute(iataCode.toUpperCase().trim());
            return ResponseEntity.ok(airport);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}