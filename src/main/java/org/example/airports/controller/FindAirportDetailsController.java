package org.example.airports.controller;

import org.example.airports.domain.Airport;
import org.example.airports.services.ViewAirportDetailsUseCase;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/airports")
public class FindAirportDetailsController {

    private final ViewAirportDetailsUseCase viewAirportDetailsUseCase;

    public FindAirportDetailsController(ViewAirportDetailsUseCase viewAirportDetailsUseCase) {
        this.viewAirportDetailsUseCase = viewAirportDetailsUseCase;
    }

    @GetMapping("/{iataCode}")
    public ResponseEntity<?> getAirportDetails(@PathVariable String iataCode) {

        if (iataCode == null || iataCode.trim().length() != 3) {
            return ResponseEntity.badRequest().body("Erro de Validação: O código IATA deve conter exatamente 3 letras.");
        }

        try {
            Airport airport = viewAirportDetailsUseCase.execute(iataCode.toUpperCase().trim());

            EntityModel<Airport> resource = EntityModel.of(airport);
            resource.add(linkTo(methodOn(FindAirportDetailsController.class).getAirportDetails(iataCode)).withSelfRel());
            resource.add(linkTo(methodOn(FindAirportRoutesController.class).getAirportRoutes(iataCode)).withRel("routes"));

            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}