package org.example.airports.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.airports.domain.Airport;
import org.example.airports.domain.ContactInfo;
import org.example.airports.domain.OperationalHours;
import org.example.airports.repositories.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateAirportDetailsUseCase {

    private final AirportRepository airportRepository;

    public UpdateAirportDetailsUseCase(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport execute(String iataCode, OperationalHours operationalHours, ContactInfo contactInfo) {
        Optional<Airport> airportOptional = airportRepository.findByIataCode(iataCode);

        if (airportOptional.isPresent()) {
            Airport airport = airportOptional.get();
            airport.updateOperationalData(operationalHours, contactInfo);
            return airportRepository.save(airport);
        } else {
            throw new EntityNotFoundException(
                    "Aeroporto com o código IATA " + iataCode + " não foi encontrado.");
        }
    }
}