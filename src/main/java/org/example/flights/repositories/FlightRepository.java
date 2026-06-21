package org.example.flights.repositories;

import org.example.flights.domain.Flight;
import org.example.flights.domain.FlightStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

    @Query("SELECT f FROM Flight f WHERE f.aircraft.registrationNumber = :registration " +
            "AND f.status != 'CANCELED' " +
            "AND f.scheduledDatetime BETWEEN :start AND :end")
    List<Flight> findOverlappingFlights(
            @Param("registration") String registration,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    Page<Flight> findByAircraftRegistrationNumberAndStatus(String registrationNumber, FlightStatus status, Pageable pageable);

}