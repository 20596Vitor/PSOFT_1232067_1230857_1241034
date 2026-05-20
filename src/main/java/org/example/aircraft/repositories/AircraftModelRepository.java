package org.example.aircraft.repositories;

import org.example.aircraft.domain.AircraftModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftModelRepository extends JpaRepository<AircraftModel, Long> {

    boolean existsByModelName(String modelName);
}