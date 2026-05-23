package org.example.airports.controller;

import org.example.airports.domain.Airport;
import org.example.airports.domain.AirportStatus;
import org.example.airports.services.RegisterAirportUseCase;
import org.example.airports.services.SearchAirportUseCase;
import org.example.airports.services.UpdateAirportStatusUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final UpdateAirportStatusUseCase updateAirportStatusUseCase;
    private final SearchAirportUseCase searchAirportUseCase;
    private final RegisterAirportUseCase registerAirportUseCase;

    public AirportController(UpdateAirportStatusUseCase updateAirportStatusUseCase, SearchAirportUseCase searchAirportUseCase,RegisterAirportUseCase registerAirportUseCase) {
        this.updateAirportStatusUseCase = updateAirportStatusUseCase;
        this.searchAirportUseCase = searchAirportUseCase;
        this.registerAirportUseCase = registerAirportUseCase;
    }

    @PostMapping
    public ResponseEntity<?> registerAirport(@RequestBody Airport airport) {

        if (airport.getIataCode() == null || airport.getIataCode().trim().length() != 3) {
            return ResponseEntity.badRequest().body("Erro de Validação: O código IATA deve conter exatamente 3 letras.");
        }

        if (airport.getName() == null || airport.getName().trim().isEmpty() ||
                airport.getCity() == null || airport.getCity().trim().isEmpty() ||
                airport.getCountry() == null || airport.getCountry().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Erro de Validação: Nome, Cidade e País são obrigatórios.");
        }

        try {
            Airport savedAirport = registerAirportUseCase.execute(airport);
            return ResponseEntity.status(201).body(savedAirport); // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{iataCode}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable String iataCode,
            @RequestParam AirportStatus status) {

        if (iataCode == null || iataCode.trim().length() != 3) {
            return ResponseEntity.badRequest().body("Erro de Validação: O código IATA deve conter exatamente 3 letras.");
        }

        try {
            Airport updatedAirport = updateAirportStatusUseCase.execute(iataCode.toUpperCase().trim(), status);
            return ResponseEntity.ok(updatedAirport);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam(required = false) String query) {
        List<Airport> results = searchAirportUseCase.execute(query);
        return ResponseEntity.ok(results);
    }
}