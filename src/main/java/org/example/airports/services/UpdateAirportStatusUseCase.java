package org.example.airports.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.airports.domain.Airport;
import org.example.airports.domain.AirportStatus;
import org.example.airports.repositories.AirportRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UpdateAirportStatusUseCase {

    private final AirportRepository airportRepository;

    public UpdateAirportStatusUseCase(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport execute(String iataCode, AirportStatus newStatus) {

        Optional<Airport> airportOptional = airportRepository.findByIataCode(iataCode);
        if (airportOptional.isPresent()) {
            Airport airport = airportOptional.get();
            airport.updateStatus(newStatus);
            return airportRepository.save(airport);
        } else {

            throw new EntityNotFoundException("Aeroporto com o código IATA " + iataCode + " não existe.");
        }
    }
}