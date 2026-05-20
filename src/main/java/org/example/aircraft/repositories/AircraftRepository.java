package org.example.aircraft.repositories;

import org.example.aircraft.domain.Aircraft;
import org.example.aircraft.domain.AircraftStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    boolean existsByRegistrationNumber(String registrationNumber);

    Optional<Aircraft> findByRegistrationNumber(String registrationNumber);

    @Query("SELECT a FROM Aircraft a WHERE " +
            "(:modelName IS NULL OR a.aircraftModel.modelName = :modelName) AND " +
            "(:status IS NULL OR a.status = :status) AND " +
            "(:year IS NULL OR EXTRACT(YEAR FROM a.manufacturingDate) = :year)")
    List<Aircraft> searchAircrafts(@Param("modelName") String modelName,
                                   @Param("status") AircraftStatus status,
                                   @Param("year") Integer year);
}