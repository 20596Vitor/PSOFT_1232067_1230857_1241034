package org.example.flights.services;

import org.example.aircraft.domain.Aircraft;
import org.example.aircraft.domain.AircraftStatus;
import org.example.aircraft.repositories.AircraftRepository;
import org.example.flights.domain.Flight;
import org.example.flights.domain.Route;
import org.example.flights.repositories.FlightRepository;
import org.example.flights.repositories.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ScheduleFlightUseCase {

    private final FlightRepository flightRepository;
    private final RouteRepository routeRepository;
    private final AircraftRepository aircraftRepository;

    public ScheduleFlightUseCase(FlightRepository flightRepository, RouteRepository routeRepository, AircraftRepository aircraftRepository) {
        this.flightRepository = flightRepository;
        this.routeRepository = routeRepository;
        this.aircraftRepository = aircraftRepository;
    }

    public Flight execute(String routeId, String aircraftRegistration, LocalDateTime scheduledDatetime) {

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Rota não encontrada."));

        if (!route.isActive()) {
            throw new IllegalArgumentException("A rota selecionada não está ativa.");
        }

        Aircraft aircraft = aircraftRepository.findByRegistrationNumber(aircraftRegistration)
                .orElseThrow(() -> new IllegalArgumentException("Aeronave não encontrada."));

        if (aircraft.getStatus() == AircraftStatus.UNDER_MAINTENANCE || aircraft.getStatus() == AircraftStatus.INACTIVE) {
            throw new IllegalStateException("A aeronave selecionada não está operacional (Estado: " + aircraft.getStatus() + ").");
        }

        // Validação de negócio 1: Range
        if (aircraft.getAircraftModel().getMaxRange() < route.getMinRange()) {
            throw new IllegalArgumentException("O alcance da aeronave é insuficiente para esta rota.");
        }

        // Validação de negócio 2: Capacidade
        if (aircraft.getSeatingCapacity() < route.getMinCapacity()) {
            throw new IllegalArgumentException("A capacidade da aeronave é inferior ao exigido pela rota.");
        }

        // Validação de negócio 3: Disponibilidade (assumimos uma janela de tempo genérica de 12 horas para bloqueio do avião)
        LocalDateTime windowStart = scheduledDatetime.minusHours(6);
        LocalDateTime windowEnd = scheduledDatetime.plusHours(6);

        List<Flight> overlaps = flightRepository.findOverlappingFlights(aircraftRegistration, windowStart, windowEnd);
        if (!overlaps.isEmpty()) {
            throw new IllegalStateException("A aeronave já tem um voo agendado próximo desta data/hora.");
        }

        Flight newFlight = new Flight(route, aircraft, scheduledDatetime);
        return flightRepository.save(newFlight);
    }
}