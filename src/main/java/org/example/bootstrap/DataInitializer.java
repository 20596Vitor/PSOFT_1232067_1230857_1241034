package org.example.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.airports.domain.Airport;
import org.example.airports.domain.AirportStatus;
import org.example.airports.domain.AirportType;
import org.example.airports.repositories.AirportRepository;
import org.example.airports.repositories.AirportTypeRepository;

import org.example.user.User;
import org.example.user.UserRepository;

// --- Imports do WP1 (Aeronaves) ---
import org.example.aircraft.domain.Aircraft;
import org.example.aircraft.domain.AircraftModel;
import org.example.aircraft.domain.AircraftStatus;
import org.example.aircraft.repositories.AircraftRepository;
import org.example.aircraft.repositories.AircraftModelRepository;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AirportRepository airportRepository;
    private final AirportTypeRepository airportTypeRepository;
    private final UserRepository userRepository;

    private final AircraftModelRepository aircraftModelRepository;
    private final AircraftRepository aircraftRepository;

    public DataInitializer(AirportRepository airportRepository,
                           AirportTypeRepository airportTypeRepository,
                           UserRepository userRepository,
                           AircraftModelRepository aircraftModelRepository,
                           AircraftRepository aircraftRepository) {
        this.airportRepository = airportRepository;
        this.airportTypeRepository = airportTypeRepository;
        this.userRepository = userRepository;
        this.aircraftModelRepository = aircraftModelRepository;
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- INICIAR BOOTSTRAP DE DADOS (WP #0A & WP #0B) ---");

        if (userRepository.count() == 0) {
            try {
                System.out.println("-> A carregar as credenciais do utilizador");

                User admin = new User("admin", "admin123", "ADMIN");
                User operator = new User("operator1", "operator123", "BACKOFFICE");

                userRepository.save(admin);
                userRepository.save(operator);

                System.out.println("-> Carregadas credenciais de admin e utilizador com sucesso");
            } catch (Exception e) {
                System.out.println("-> Erro ao inicializar utilizadores: " + e.getMessage());
            }
        }

        if (airportTypeRepository.count() == 0) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                InputStream inputStream = getClass().getResourceAsStream("/airportTypes.json");

                if (inputStream != null) {
                    List<AirportType> types = mapper.readValue(inputStream,
                            mapper.getTypeFactory().constructCollectionType(List.class, AirportType.class));

                    airportTypeRepository.saveAll(types);
                    System.out.println("-> [WP #2A] Airport Types preloaded com sucesso a partir do JSON!");
                } else {
                    System.out.println("-> [Aviso] Ficheiro airportTypes.json não foi encontrado em resources.");
                }
            } catch (Exception e) {
                System.out.println("-> Erro ao carregar Airport Types: " + e.getMessage());
            }
        }

        if (airportRepository.count() == 0) {
            Airport lisboa = new Airport("LIS", "Aeroporto Humberto Delgado", "Lisboa", "Portugal", "military", AirportStatus.OPERATIONAL, null, null);
            Airport porto = new Airport("OPO", "Aeroporto Francisco Sá Carneiro", "Porto", "Portugal", "comercial", AirportStatus.OPERATIONAL, null,null);

            airportRepository.save(lisboa);
            airportRepository.save(porto);
            System.out.println("-> Aeroportos de teste inicializados!");
        }

        if (aircraftModelRepository.count() == 0) {
            System.out.println("-> A carregar Modelos de Aeronaves (WP1)...");

            AircraftModel boeing737 = new AircraftModel();
            boeing737.setManufacturer("Boeing");
            boeing737.setModelName("737 MAX 8");
            boeing737.setCruisingSpeed(839.0f); // Adicionado o 'f'
            boeing737.setFuelCapacity(25941.0f); // Adicionado o 'f'
            boeing737.setMaxRange(6570.0f); // Adicionado o 'f'
            boeing737 = aircraftModelRepository.save(boeing737);

            AircraftModel airbusA320 = new AircraftModel();
            airbusA320.setManufacturer("Airbus");
            airbusA320.setModelName("A320neo");
            airbusA320.setCruisingSpeed(828.0f); // Adicionado o 'f'
            airbusA320.setFuelCapacity(24210.0f); // Adicionado o 'f'
            airbusA320.setMaxRange(6100.0f); // Adicionado o 'f'
            airbusA320 = aircraftModelRepository.save(airbusA320);

            if (aircraftRepository.count() == 0) {
                System.out.println("-> A carregar Aeronaves da Frota (WP1)...");

                // Avião 1 - Em Voo e com bastantes horas
                Aircraft aviao1 = new Aircraft();
                aviao1.setRegistrationNumber("CS-TKA");
                aviao1.setManufacturingDate(LocalDate.of(2020, 5, 10)); // Retirado o .toString()
                aviao1.setSeatingCapacity(189);
                aviao1.setStatus(AircraftStatus.IN_FLIGHT);
                aviao1.setTotalOperationalHours(1450.5);
                aviao1.setAircraftModel(boeing737);
                aircraftRepository.save(aviao1);

                // Avião 2 - Em Manutenção
                Aircraft aviao2 = new Aircraft();
                aviao2.setRegistrationNumber("CS-XPTO");
                aviao2.setManufacturingDate(LocalDate.of(2018, 11, 22)); // Retirado o .toString()
                aviao2.setSeatingCapacity(180);
                aviao2.setStatus(AircraftStatus.UNDER_MAINTENANCE);
                aviao2.setTotalOperationalHours(3200.0);
                aviao2.setAircraftModel(airbusA320);
                aircraftRepository.save(aviao2);

                // Avião 3 - Ativo/Disponível na placa
                Aircraft aviao3 = new Aircraft();
                aviao3.setRegistrationNumber("CS-BOM");
                aviao3.setManufacturingDate(LocalDate.of(2023, 1, 15)); // Retirado o .toString()
                aviao3.setSeatingCapacity(189);
                aviao3.setStatus(AircraftStatus.ACTIVE);
                aviao3.setTotalOperationalHours(125.0);
                aviao3.setAircraftModel(boeing737);
                aircraftRepository.save(aviao3);

                System.out.println("-> Frota inicializada com sucesso!");
            }

        System.out.println("--- BOOTSTRAP CONCLUÍDO ---");
    }
}
}