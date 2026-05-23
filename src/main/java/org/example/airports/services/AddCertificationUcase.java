package org.example.airports.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.airports.domain.Airport;
import org.example.airports.repositories.AirportRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddCertificationUcase {

    private final AirportRepository airportRepository;
    private final List<String> allowedManufacturers = new ArrayList<>();

    public AddCertificationUcase(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
        carregarFabricantesPermitidos();
    }

    private void carregarFabricantesPermitidos() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/manufactors.json");

            if (inputStream != null) {
                JsonNode rootNode = mapper.readTree(inputStream);
                if (rootNode.isArray()) {
                    for (JsonNode node : rootNode) {
                        JsonNode nameNode = node.get("name");
                        if (nameNode != null) {
                            allowedManufacturers.add(nameNode.asText().toUpperCase().trim());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar marcas permitidas no Caso de Uso: " + e.getMessage());
        }
    }

    public void execute(String iataCode, String airplaneModel) {
        if (airplaneModel == null || airplaneModel.trim().isEmpty()) {
            throw new IllegalArgumentException("O modelo da aeronave não pode estar vazio.");
        }

        String modeloInput = airplaneModel.toUpperCase().trim();

        boolean marcaValida = allowedManufacturers.stream().anyMatch(modeloInput::startsWith);

        if (!marcaValida) {
            throw new IllegalArgumentException("Fabricante não autorizado. O modelo deve começar por um dos seguintes: " + allowedManufacturers);
        }

        Optional<Airport> airportOptional = airportRepository.findByIataCode(iataCode.toUpperCase().trim());

        if (airportOptional.isPresent()) {
            Airport airport = airportOptional.get();
            airport.addAirplaneCertification(airplaneModel);
            airportRepository.save(airport);
        } else {
            throw new RuntimeException("Aeroporto com o código IATA " + iataCode + " não foi encontrado.");
        }
    }
}