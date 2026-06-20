package org.example.flights.repositories;

import org.example.flights.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {
    boolean existsByOriginIataCodeAndDestinationIataCode(String originIataCode, String destinationIataCode);
    List<Route> findByOriginIataCode(String originIataCode);
    List<Route> findByDestinationIataCode(String destinationIataCode);
    List<Route> findByOriginIataCodeAndDestinationIataCode(String originIataCode, String destinationIataCode);
    @Query("SELECT r FROM Route r WHERE r.minRange <= :maxRange AND r.minCapacity <= :capacity AND r.active = true")
    List<Route> findCompatibleRoutes(@Param("maxRange") Float maxRange, @Param("capacity") Integer capacity);
}