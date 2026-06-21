package org.example.flights.services;

import org.example.flights.domain.Route;
import org.example.flights.repositories.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FindAlternativeRoutesUseCase {

    private final RouteRepository routeRepository;

    public FindAlternativeRoutesUseCase(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public record RoutePathDTO(List<Route> legs, int totalEstimatedTime) {}

    public List<RoutePathDTO> execute(String origin, String destination) {
        List<RoutePathDTO> alternativePaths = new ArrayList<>();

        List<Route> directRoutes = routeRepository.findByOriginIataCodeAndDestinationIataCode(origin, destination)
                .stream()
                .filter(Route::isActive)
                .toList();

        for (Route direct : directRoutes) {
            alternativePaths.add(new RoutePathDTO(List.of(direct), direct.getEstimatedFlightTime()));
        }

        List<Route> firstLegs = routeRepository.findByOriginIataCode(origin)
                .stream()
                .filter(Route::isActive)
                .toList();

        for (Route firstLeg : firstLegs) {
            if (firstLeg.getDestinationIataCode().equalsIgnoreCase(destination)) {
                continue;
            }

            List<Route> secondLegs = routeRepository.findByOriginIataCodeAndDestinationIataCode(
                            firstLeg.getDestinationIataCode(), destination)
                    .stream()
                    .filter(Route::isActive)
                    .toList();

            for (Route secondLeg : secondLegs) {
                int totalTime = firstLeg.getEstimatedFlightTime() + secondLeg.getEstimatedFlightTime();
                alternativePaths.add(new RoutePathDTO(List.of(firstLeg, secondLeg), totalTime));
            }
        }

        return alternativePaths;
    }
}