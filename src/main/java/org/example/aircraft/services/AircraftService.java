package org.example.aircraft.services;

import org.example.aircraft.domain.Aircraft;
import org.example.aircraft.domain.AircraftModel;
import org.example.aircraft.domain.AircraftStatus;
import org.example.aircraft.repositories.AircraftModelRepository;
import org.example.aircraft.repositories.AircraftRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import org.example.flights.domain.Route;
import org.example.flights.repositories.RouteRepository;
import java.util.Map;
import java.util.HashMap;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftModelRepository modelRepository;
    private final RouteRepository routeRepository;

    public AircraftService(AircraftRepository aircraftRepository, AircraftModelRepository modelRepository, RouteRepository routeRepository) {
        this.aircraftRepository = aircraftRepository;
        this.modelRepository = modelRepository;
        this.routeRepository = routeRepository;
    }

    public Aircraft registerAircraft(String registrationNumber, LocalDate manufacturingDate, Integer seatingCapacity, AircraftStatus status, Long modelId) {
        if (aircraftRepository.existsByRegistrationNumber(registrationNumber)) {
            throw new IllegalArgumentException("Já existe uma aeronave registada com a matrícula: " + registrationNumber);
        }

        if (seatingCapacity <= 0) {
            throw new IllegalArgumentException("A capacidade de assentos deve ser um valor positivo.");
        }

        AircraftModel model = modelRepository.findById(modelId)
                .orElseThrow(() -> new IllegalArgumentException("Modelo de aeronave com ID " + modelId + " não encontrado."));

        Aircraft newAircraft = new Aircraft(registrationNumber, manufacturingDate, seatingCapacity, status, model);
        return aircraftRepository.save(newAircraft);
    }

    public Aircraft getAircraftByRegistration(String registrationNumber) {
        return aircraftRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Aeronave com a matrícula " + registrationNumber + " não encontrada."));
    }
    public List<Aircraft> searchAircrafts(String modelName, AircraftStatus status, Integer year) {
        return aircraftRepository.searchAircrafts(modelName, status, year);
    }
    public Aircraft updateAircraftStatus(String registrationNumber, AircraftStatus newStatus) {
        Aircraft aircraft = getAircraftByRegistration(registrationNumber);

        aircraft.setStatus(newStatus);

        return aircraftRepository.save(aircraft);
    }
    public List<Route> getCompatibleRoutesForAircraft(String registrationNumber) {
        Aircraft aircraft = aircraftRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Aeronave não encontrada: " + registrationNumber));

        Float maxRange = aircraft.getAircraftModel().getMaxRange();
        Integer actualCapacity = aircraft.getSeatingCapacity();

        return routeRepository.findCompatibleRoutes(maxRange, actualCapacity);
    }
    public Map<String, Long> getFleetStatusSummary() {
        List<Object[]> results = aircraftRepository.countAircraftByStatus();

        Map<String, Long> summary = new HashMap<>();

        for (AircraftStatus status : AircraftStatus.values()) {
            summary.put(status.name(), 0L);
        }

        for (Object[] result : results) {
            AircraftStatus status = (AircraftStatus) result[0];
            Long count = (Long) result[1];
            summary.put(status.name(), count);
        }

        return summary;
    }
    public Map<String, Double> getFleetOperationalHours() {
        List<Aircraft> fleet = aircraftRepository.findAll();

        Map<String, Double> hoursReport = new HashMap<>();

        for (Aircraft aircraft : fleet) {
            Double hours = aircraft.getTotalOperationalHours() != null ? aircraft.getTotalOperationalHours() : 0.0;
            hoursReport.put(aircraft.getRegistrationNumber(), hours);
        }

        return hoursReport;
    }
    public Aircraft addOperationalHours(String registrationNumber, Double additionalHours) {
        Aircraft aircraft = aircraftRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Aeronave não encontrada: " + registrationNumber));

        if (additionalHours <= 0) {
            throw new IllegalArgumentException("As horas a adicionar devem ser superiores a zero.");
        }

        Double currentHours = aircraft.getTotalOperationalHours() != null ? aircraft.getTotalOperationalHours() : 0.0;
        aircraft.setTotalOperationalHours(currentHours + additionalHours);

        return aircraftRepository.save(aircraft);
    }
}