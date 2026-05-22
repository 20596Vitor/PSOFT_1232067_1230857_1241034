package org.example.bootstrap;

import org.example.airports.domain.Airport;
import org.example.airports.domain.AirportStatus;
import org.example.airports.repositories.AirportRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AirportRepository airportRepository;

    public DataInitializer(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- INICIAR BOOTSTRAP DE DADOS (WP #0A) ---");
        if (airportRepository.count() == 0) {

            Airport lisboa = new Airport("LIS", "Aeroporto Humberto Delgado", "Lisboa", "Portugal", AirportStatus.OPERATIONAL);
            Airport porto = new Airport("OPO", "Aeroporto Francisco Sá Carneiro", "Porto", "Portugal", AirportStatus.OPERATIONAL);

            airportRepository.save(lisboa);
            airportRepository.save(porto);
        }

        System.out.println("--- BOOTSTRAP CONCLUÍDO ---");
    }
}