package org.example.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- INICIAR BOOTSTRAP DE DADOS (WP #0A) ---");

        // Aqui dentro vais colocar os ifs para salvar o admin,
        // operadores, fabricantes de aviões, tipos de aeroporto, etc.

        System.out.println("--- BOOTSTRAP CONCLUÍDO ---");
    }
}