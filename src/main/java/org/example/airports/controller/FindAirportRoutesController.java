package org.example.airports.controller;

import org.example.flights.domain.Route;
import org.example.flights.repositories.RouteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/airports")
public class FindAirportRoutesController {

    private final RouteRepository routeRepository;

    public FindAirportRoutesController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping("/{iataCode}/routes")
    public ResponseEntity<?> getAirportRoutes(@PathVariable String iataCode) {

        if (iataCode == null || iataCode.trim().length() != 3) {
            return ResponseEntity.badRequest()
                    .body("Erro de Validação: O código IATA deve conter exatamente 3 letras.");
        }

        String code = iataCode.toUpperCase().trim();

        List<Route> departing = routeRepository.findByOriginIataCode(code);
        List<Route> arriving = routeRepository.findByDestinationIataCode(code);

        List<Route> allRoutes = Stream.concat(departing.stream(), arriving.stream())
                .distinct()
                .toList();

        return ResponseEntity.ok(allRoutes);
    }
}