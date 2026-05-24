package org.example.flights.services;

import org.example.flights.domain.Route;
import org.example.flights.domain.RouteRecord;
import org.example.flights.repositories.RouteRepository;
import org.example.airports.repositories.AirportRepository; // Import do colega
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final AirportRepository airportRepository;

    public RouteService(RouteRepository routeRepository, AirportRepository airportRepository) {
        this.routeRepository = routeRepository;
        this.airportRepository = airportRepository;
    }

    @Transactional
    public Route createRoute(String originIata, String destinationIata, int estimatedTime, float minRange, int minCapacity) {

        if (airportRepository.findByIataCode(originIata).isEmpty()) {
            throw new IllegalArgumentException("O aeroporto de origem não existe: " + originIata);
        }
        if (airportRepository.findByIataCode(destinationIata).isEmpty()) {
            throw new IllegalArgumentException("O aeroporto de destino não existe: " + destinationIata);
        }

        if (routeRepository.existsByOriginIataCodeAndDestinationIataCode(originIata, destinationIata)) {
            throw new IllegalArgumentException("Já existe uma rota registada entre estes dois aeroportos.");
        }

        Route newRoute = new Route(originIata, destinationIata, estimatedTime, minRange, minCapacity);

        return routeRepository.save(newRoute);
    }

    @Transactional(readOnly = true)
    public List<RouteRecord> getRouteHistory(String routeId) {
        Route route = getRouteDetails(routeId);
        return route.getHistory();
    }

    @Transactional
    public Route updateRoute(String routeId, int estimatedTime, float minRange, int minCapacity) {
        Route route = getRouteDetails(routeId);

        route.updateRoute(estimatedTime, minRange, minCapacity);

        return routeRepository.save(route);
    }

    @Transactional
    public Route deactivateRoute(String routeId) {
        Route route = getRouteDetails(routeId);

        route.deactivate();

        return routeRepository.save(route);
    }

    @Transactional(readOnly = true)
    public List<Route> getRoutesFromAirport(String originIata) {
        return routeRepository.findByOriginIataCode(originIata);
    }

    @Transactional(readOnly = true)
    public Route getRouteDetails(String routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Rota não encontrada com o ID: " + routeId));
    }

    @Transactional(readOnly = true)
    public List<Route> searchRoutes(String originIata, String destinationIata) {

        boolean hasOrigin = (originIata != null && !originIata.trim().isEmpty());
        boolean hasDest = (destinationIata != null && !destinationIata.trim().isEmpty());

        if (hasOrigin && hasDest) {
            return routeRepository.findByOriginIataCodeAndDestinationIataCode(originIata, destinationIata);
        } else if (hasOrigin) {
            return routeRepository.findByOriginIataCode(originIata);
        } else if (hasDest) {
            return routeRepository.findByDestinationIataCode(destinationIata);
        } else {
            return routeRepository.findAll();
        }
    }
}