package org.example.airports.services;

import org.example.airports.domain.Airport;
import org.example.airports.domain.AirportStatus;
import org.example.airports.repositories.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterAirportUseCaseTest {

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private RegisterAirportUseCase registerAirportUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void RegistarAeroportoComSucesso() {
        Airport novo = new Airport("OPO", "Aeroporto do Porto", "Porto", "Portugal", "comercial", null);
        Airport guardado = new Airport("OPO", "Aeroporto do Porto", "Porto", "Portugal", "comercial", AirportStatus.OPERATIONAL);

        when(airportRepository.findByIataCode("OPO")).thenReturn(Optional.empty());
        when(airportRepository.save(any(Airport.class))).thenReturn(guardado);

        Airport resultado = registerAirportUseCase.execute(novo);


        assertNotNull(resultado);
        assertEquals(AirportStatus.OPERATIONAL, resultado.getStatus());
        verify(airportRepository, times(1)).save(novo);
        System.out.println("-> AEROPORTO CRIADO COM SUCESSO!");
        System.out.println("-> Código IATA: " + resultado.getIataCode());
        System.out.println("-> Estado Operacional: " + resultado.getStatus());
    }

    @Test
    void LancarexcecaoQuandoIataDuplicado() {
        Airport duplicado = new Airport("MAD", "Madrid", "Madrid", "Espanha", "comercial", null);
        when(airportRepository.findByIataCode("MAD")).thenReturn(Optional.of(duplicado));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registerAirportUseCase.execute(duplicado);
        });
        System.out.println(exception.getMessage());
        assertTrue(exception.getMessage().contains("Já existe um aeroporto registado com o código IATA:"));
        verify(airportRepository, never()).save(any(Airport.class));
    }
}