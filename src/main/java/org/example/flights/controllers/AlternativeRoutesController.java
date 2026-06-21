package org.example.flights.controllers;

import org.example.flights.services.FindAlternativeRoutesUseCase;
import org.example.flights.services.FindAlternativeRoutesUseCase.RoutePathDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class AlternativeRoutesController {

    private final FindAlternativeRoutesUseCase findAlternativeRoutesUseCase;

    public AlternativeRoutesController(FindAlternativeRoutesUseCase findAlternativeRoutesUseCase) {
        this.findAlternativeRoutesUseCase = findAlternativeRoutesUseCase;
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoutePathDTO>> searchRoutes(
            @RequestParam String origin,
            @RequestParam String destination) {

        List<RoutePathDTO> paths = findAlternativeRoutesUseCase.execute(origin.toUpperCase(), destination.toUpperCase());

        if (paths.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(paths);
    }
}