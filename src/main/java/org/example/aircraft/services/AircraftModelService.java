package org.example.aircraft.services;

import org.example.aircraft.domain.AircraftModel;
import org.example.aircraft.repositories.AircraftModelRepository;
import org.springframework.stereotype.Service;

@Service
public class AircraftModelService {

    private final AircraftModelRepository repository;

    public AircraftModelService(AircraftModelRepository repository) {
        this.repository = repository;
    }

    public AircraftModel createAircraftModel(AircraftModel newModel) {
        if (repository.existsByModelName(newModel.getModelName())) {
            throw new IllegalArgumentException("Já existe um modelo de aeronave com o nome: " + newModel.getModelName());
        }

        if (newModel.getMaxRange() <= 0 || newModel.getFuelCapacity() <= 0 || newModel.getCruisingSpeed() <= 0) {
            throw new IllegalArgumentException("As especificações (alcance, combustível, velocidade) devem ser valores positivos.");
        }

        return repository.save(newModel);
    }
}