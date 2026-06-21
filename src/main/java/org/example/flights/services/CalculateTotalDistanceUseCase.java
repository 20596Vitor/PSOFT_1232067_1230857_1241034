package org.example.flights.services;

import org.example.airports.domain.Airport;
import org.example.airports.repositories.AirportRepository;
import org.example.flights.domain.Route;
import org.example.flights.repositories.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CalculateTotalDistanceUseCase {

    private final RouteRepository routeRepository;
    private final AirportRepository airportRepository;

    public CalculateTotalDistanceUseCase(RouteRepository routeRepository, AirportRepository airportRepository) {
        this.routeRepository = routeRepository;
        this.airportRepository = airportRepository;
    }

    public double execute() {
        List<Route> activeRoutes = routeRepository.findByActiveTrue();
        double totalDistance = 0.0;

        for (Route route : activeRoutes) {
            Airport origin = airportRepository.findByIataCode(route.getOriginIataCode()).orElse(null);
            Airport dest = airportRepository.findByIataCode(route.getDestinationIataCode()).orElse(null);

            if (origin != null && dest != null && origin.getCoordinates() != null && dest.getCoordinates() != null) {
                totalDistance += haversineDistance(origin.getCoordinates(), dest.getCoordinates());
            }
        }

        return totalDistance;
    }

    private double haversineDistance(String coords1, String coords2) {
        try {
            String[] c1 = coords1.split(",");
            String[] c2 = coords2.split(",");
            double lat1 = Double.parseDouble(c1[0].trim());
            double lon1 = Double.parseDouble(c1[1].trim());
            double lat2 = Double.parseDouble(c2[0].trim());
            double lon2 = Double.parseDouble(c2[1].trim());

            final int R = 6371; // Raio da Terra em quilómetros
            double latDistance = Math.toRadians(lat2 - lat1);
            double lonDistance = Math.toRadians(lon2 - lon1);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            return R * c;
        } catch (Exception e) {
            return 0.0; // Ignora rotas com coordenadas mal formatadas
        }
    }
}