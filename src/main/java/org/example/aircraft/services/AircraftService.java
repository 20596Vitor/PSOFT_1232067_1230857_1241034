package org.example.aircraft.services;

import org.example.aircraft.domain.Aircraft;
import org.example.aircraft.domain.AircraftModel;
import org.example.aircraft.domain.AircraftStatus;
import org.example.aircraft.repositories.AircraftModelRepository;
import org.example.aircraft.repositories.AircraftRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftModelRepository modelRepository;

    public AircraftService(AircraftRepository aircraftRepository, AircraftModelRepository modelRepository) {
        this.aircraftRepository = aircraftRepository;
        this.modelRepository = modelRepository;
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
}