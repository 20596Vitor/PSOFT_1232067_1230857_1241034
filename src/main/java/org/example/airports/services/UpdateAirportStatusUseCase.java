package org.example.airports.services;

import org.example.airports.domain.Airport;
import org.example.airports.domain.AirportStatus;
import org.example.airports.repositories.AirportRepository;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class UpdateAirportStatusUseCase {

    private final AirportRepository airportRepository;

    public UpdateAirportStatusUseCase(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport execute(String iataCode, AirportStatus status) {
        Airport airport = airportRepository.findByIataCode(iataCode)
                .orElseThrow(() -> new NoSuchElementException("Airport not found"));

        airport.changeStatus(status); // Regra de negócio (US109)
        return airportRepository.save(airport);
    }
}