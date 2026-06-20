package org.example.airports.controller;

import org.example.airports.domain.Airport;
import org.example.airports.repositories.AirportRepository;
import org.example.flights.repositories.RouteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/airports")
public class AirportStatsController {

    private final AirportRepository airportRepository;
    private final RouteRepository routeRepository;

    public AirportStatsController(AirportRepository airportRepository, RouteRepository routeRepository) {
        this.airportRepository = airportRepository;
        this.routeRepository = routeRepository;
    }

    @GetMapping("/stats/busiest")
    public ResponseEntity<List<Map<String, Object>>> getBusiestAirports() {

        List<Airport> airports = airportRepository.findAll();

        List<Map<String, Object>> result = airports.stream()
                .map(airport -> {
                    String code = airport.getIataCode();
                    long routeCount = routeRepository.findByOriginIataCode(code).size()
                            + routeRepository.findByDestinationIataCode(code).size();

                    Map<String, Object> entry = new LinkedHashMap<>();
                    entry.put("iataCode", code);
                    entry.put("name", airport.getName());
                    entry.put("city", airport.getCity());
                    entry.put("country", airport.getCountry());
                    entry.put("totalRoutes", routeCount);
                    return entry;
                })
                .sorted((a, b) -> Long.compare(
                        (Long) b.get("totalRoutes"),
                        (Long) a.get("totalRoutes")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/grouped")
    public ResponseEntity<Map<String, List<Map<String, String>>>> getAirportsGrouped(
            @RequestParam(required = false) String country) {

        List<Airport> airports = airportRepository.findAll();

        if (country != null && !country.trim().isEmpty()) {
            airports = airports.stream()
                    .filter(a -> a.getCountry().equalsIgnoreCase(country.trim()))
                    .collect(Collectors.toList());
        }

        Map<String, List<Map<String, String>>> grouped = airports.stream()
                .collect(Collectors.groupingBy(
                        Airport::getCountry,
                        Collectors.mapping(airport -> {
                            Map<String, String> info = new LinkedHashMap<>();
                            info.put("iataCode", airport.getIataCode());
                            info.put("name", airport.getName());
                            info.put("city", airport.getCity());
                            info.put("status", airport.getStatus() != null
                                    ? airport.getStatus().name() : "UNKNOWN");
                            return info;
                        }, Collectors.toList())
                ));

        return ResponseEntity.ok(grouped);
    }
}