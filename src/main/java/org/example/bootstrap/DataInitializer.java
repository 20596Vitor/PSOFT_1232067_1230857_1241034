package org.example.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.airports.domain.Airport;
import org.example.airports.domain.AirportStatus;
import org.example.airports.domain.AirportType;
import org.example.airports.repositories.AirportRepository;
import org.example.airports.repositories.AirportTypeRepository;

import org.example.user.User;
import org.example.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AirportRepository airportRepository;
    private final AirportTypeRepository airportTypeRepository;
    private final UserRepository userRepository;

    public DataInitializer(AirportRepository airportRepository, AirportTypeRepository airportTypeRepository, UserRepository userRepository) {
        this.airportRepository = airportRepository;
        this.airportTypeRepository = airportTypeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- INICIAR BOOTSTRAP DE DADOS (WP #0A) ---");

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
            Airport lisboa = new Airport("LIS", "Aeroporto Humberto Delgado", "Lisboa", "Portugal", "military", AirportStatus.OPERATIONAL);
            Airport porto = new Airport("OPO", "Aeroporto Francisco Sá Carneiro", "Porto", "Portugal", "comercial", AirportStatus.OPERATIONAL);

            airportRepository.save(lisboa);
            airportRepository.save(porto);
            System.out.println("-> Aeroportos de teste inicializados!");
        }

        System.out.println("--- BOOTSTRAP CONCLUÍDO ---");
    }
}