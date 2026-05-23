package org.example.airports.repositories;

import org.example.airports.domain.AirportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportTypeRepository extends JpaRepository<AirportType, Long> {
}