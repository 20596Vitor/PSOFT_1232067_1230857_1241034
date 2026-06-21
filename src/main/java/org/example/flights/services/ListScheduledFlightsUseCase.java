package org.example.flights.services;

import org.example.aircraft.repositories.AircraftRepository;
import org.example.flights.domain.Flight;
import org.example.flights.domain.FlightStatus;
import org.example.flights.repositories.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ListScheduledFlightsUseCase {

    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;

    public ListScheduledFlightsUseCase(FlightRepository flightRepository, AircraftRepository aircraftRepository) {
        this.flightRepository = flightRepository;
        this.aircraftRepository = aircraftRepository;
    }

    public Page<Flight> execute(String aircraftRegistration, Pageable pageable) {

        // Valida se a aeronave existe
        if (!aircraftRepository.existsByRegistrationNumber(aircraftRegistration)) {
            throw new IllegalArgumentException("Aeronave não encontrada com a matrícula: " + aircraftRegistration);
        }

        return flightRepository.findByAircraftRegistrationNumberAndStatus(
                aircraftRegistration, FlightStatus.SCHEDULED, pageable);
    }
}