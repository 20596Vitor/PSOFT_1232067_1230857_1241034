package org.example.airports.repositories;

import org.example.airports.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    Optional<Airport> findByIataCode(String iataCode);

    List<Airport> findByCityContainingIgnoreCaseOrCountryContainingIgnoreCaseOrNameContainingIgnoreCase(
            String city, String country, String name
    );
}