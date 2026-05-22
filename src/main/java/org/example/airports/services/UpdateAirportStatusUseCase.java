package org.example.airports.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.airports.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateAirportStatusUseCase {

    @Autowired
    private AirportRepository airportRepository;

}
