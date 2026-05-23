package org.example.airports.services;

import org.example.airports.domain.Airport;
import org.example.airports.domain.AirportStatus;
import org.example.airports.repositories.AirportRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RegisterAirportUseCase {

    private final AirportRepository airportRepository;

    public RegisterAirportUseCase(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport execute(Airport newAirport) {

        String iataFormatado = newAirport.getIataCode().toUpperCase().trim();


        Optional<Airport> existing = airportRepository.findByIataCode(iataFormatado);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Já existe um aeroporto registado com o código IATA: " + iataFormatado);
        }
        newAirport.updateStatus(AirportStatus.OPERATIONAL);

        return airportRepository.save(newAirport);
    }
}