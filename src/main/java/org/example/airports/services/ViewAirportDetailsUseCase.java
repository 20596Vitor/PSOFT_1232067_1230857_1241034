package org.example.airports.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.airports.domain.Airport;
import org.example.airports.repositories.AirportRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ViewAirportDetailsUseCase {

    private final AirportRepository airportRepository;


    public ViewAirportDetailsUseCase(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport execute(String iataCode) {
        Optional<Airport> airportOptional = airportRepository.findByIataCode(iataCode);
        if (airportOptional.isPresent()) {
            return airportOptional.get();
        } else {
            throw new EntityNotFoundException("Aeroporto com o código IATA " + iataCode + " não foi encontrado.");
        }
    }
}