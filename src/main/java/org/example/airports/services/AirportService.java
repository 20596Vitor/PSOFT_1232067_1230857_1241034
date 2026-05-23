package org.example.airports.services;

import org.example.airports.domain.Airport;
import org.example.airports.domain.AirportStatus;
import org.example.airports.repositories.AirportRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AirportService {

    private final AirportRepository airportRepository;


    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport findByIataCode(String iataCode) {
        return airportRepository.findByIataCode(iataCode)
                .orElseThrow(() -> new RuntimeException("Aeroporto com o código " + iataCode + " não foi encontrado."));
    }

    public Airport updateAirportStatus(String iataCode, AirportStatus newStatus) {

        Airport airport = airportRepository.findByIataCode(iataCode)
                .orElseThrow(() -> new RuntimeException("Aeroporto não encontrado."));


        airport.updateStatus(newStatus);


        return airportRepository.save(airport);
    }
}