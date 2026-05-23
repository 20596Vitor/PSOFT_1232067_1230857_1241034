package org.example.airports.services;

import org.example.airports.domain.Airport;
import org.example.airports.repositories.AirportRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchAirportUseCase {

    private final AirportRepository airportRepository;

    public SearchAirportUseCase(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> execute(String query) {
        if (query == null || query.trim().isEmpty()) {
            return airportRepository.findAll();
        }
        return airportRepository.findByCityContainingIgnoreCaseOrCountryContainingIgnoreCaseOrNameContainingIgnoreCase(
                query, query, query);
    }
}