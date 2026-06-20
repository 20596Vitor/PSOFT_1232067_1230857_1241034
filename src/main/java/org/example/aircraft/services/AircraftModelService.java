package org.example.aircraft.services;

import org.example.aircraft.domain.AircraftModel;
import org.example.aircraft.repositories.AircraftModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class AircraftModelService {

    private final AircraftModelRepository aircraftModelRepository;

    public AircraftModelService(AircraftModelRepository aircraftModelRepository) {
        this.aircraftModelRepository = aircraftModelRepository;
    }

    public AircraftModel createAircraftModel(AircraftModel aircraftModel, MultipartFile file) throws IOException {
        // Se o utilizador enviou um ficheiro, convertemos para bytes e guardamos
        if (file != null && !file.isEmpty()) {
            aircraftModel.setImage(file.getBytes());
        }
        return aircraftModelRepository.save(aircraftModel);
    }


    public AircraftModel updateAircraftModelSpecs(Long id, String manufacturer, String modelName,
                                                  Float cruisingSpeed, Float fuelCapacity, Float maxRange) {

        AircraftModel model = aircraftModelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Modelo de aeronave não encontrado."));

        if (manufacturer != null) model.setManufacturer(manufacturer);
        if (modelName != null) model.setModelName(modelName);
        if (cruisingSpeed != null) {
            if (cruisingSpeed <= 0) throw new IllegalArgumentException("A velocidade cruzeiro deve ser positiva.");
            model.setCruisingSpeed(cruisingSpeed);
        }
        if (fuelCapacity != null) {
            if (fuelCapacity <= 0) throw new IllegalArgumentException("A capacidade de combustível deve ser positiva.");
            model.setFuelCapacity(fuelCapacity);
        }
        if (maxRange != null) {
            if (maxRange <= 0) throw new IllegalArgumentException("O alcance máximo deve ser positivo.");
            model.setMaxRange(maxRange);
        }

        return aircraftModelRepository.save(model);
    }
}