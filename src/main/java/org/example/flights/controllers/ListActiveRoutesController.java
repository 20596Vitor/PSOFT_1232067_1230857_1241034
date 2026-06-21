package org.example.flights.controllers;

import org.example.flights.domain.Route;
import org.example.flights.services.ListActiveRoutesUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
public class ListActiveRoutesController {

    private final ListActiveRoutesUseCase listActiveRoutesUseCase;

    public ListActiveRoutesController(ListActiveRoutesUseCase listActiveRoutesUseCase) {
        this.listActiveRoutesUseCase = listActiveRoutesUseCase;
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveRoutes(
            @RequestParam(defaultValue = "popularity") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Route> routesPage = listActiveRoutesUseCase.execute(sortBy, pageable);

            return ResponseEntity.ok(routesPage);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}