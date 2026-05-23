package org.example.flights.controllers;

import org.example.flights.domain.Route;
import org.example.flights.domain.RouteRecord;
import org.example.flights.services.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    // ==========================================
    // DTOs (Data Transfer Objects) para os pedidos
    // ==========================================
    public record CreateRouteRequest(String originIata, String destinationIata, int estimatedTime, float minRange, int minCapacity) {}
    public record UpdateRouteRequest(Integer estimatedTime, Float minRange, Integer minCapacity, Boolean active) {}

    // ==========================================
    // US110 - Create a Flight Route
    // ==========================================
    @PostMapping
    public ResponseEntity<Route> createRoute(@RequestBody CreateRouteRequest request) {
        Route newRoute = routeService.createRoute(
                request.originIata(),
                request.destinationIata(),
                request.estimatedTime(),
                request.minRange(),
                request.minCapacity()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoute);
    }

    // ==========================================
    // US113 (Detalhes) - View Route Details
    // ==========================================
    @GetMapping("/{routeId}")
    public ResponseEntity<Route> getRouteDetails(@PathVariable String routeId) {
        Route route = routeService.getRouteDetails(routeId);
        return ResponseEntity.ok(route);
    }

    // ==========================================
    // US111 - View Route History
    // ==========================================
    @GetMapping("/{routeId}/history")
    public ResponseEntity<List<RouteRecord>> getRouteHistory(@PathVariable String routeId) {
        List<RouteRecord> history = routeService.getRouteHistory(routeId);
        return ResponseEntity.ok(history);
    }

    // ==========================================
    // US112 - Update or Deactivate Route
    // ==========================================
    @PatchMapping("/{routeId}")
    public ResponseEntity<Route> updateOrDeactivateRoute(
            @PathVariable String routeId,
            @RequestBody UpdateRouteRequest request) {

        Route updatedRoute;

        // Se enviaram a flag 'active' como false, é um pedido de desativação
        if (request.active() != null && !request.active()) {
            updatedRoute = routeService.deactivateRoute(routeId);
        }
        // Caso contrário, é uma atualização de dados
        else {
            updatedRoute = routeService.updateRoute(
                    routeId,
                    request.estimatedTime(),
                    request.minRange(),
                    request.minCapacity()
            );
        }

        return ResponseEntity.ok(updatedRoute);
    }

    // ==========================================
    // US113 (Lista) & US114 - Search Routes
    // ==========================================
    @GetMapping
    public ResponseEntity<List<Route>> searchRoutes(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination) {

        List<Route> routes = routeService.searchRoutes(origin, destination);
        return ResponseEntity.ok(routes);
    }
}