package org.example.airports.controller;

import org.example.airports.domain.*;
import org.example.airports.services.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final UpdateAirportStatusUseCase updateAirportStatusUseCase;
    private final SearchAirportUseCase searchAirportUseCase;
    private final RegisterAirportUseCase registerAirportUseCase;
    private final UpdateAirportDetailsUseCase updateAirportDetailsUseCase;

    public AirportController(UpdateAirportStatusUseCase updateAirportStatusUseCase,
                             SearchAirportUseCase searchAirportUseCase,
                             RegisterAirportUseCase registerAirportUseCase,
                             UpdateAirportDetailsUseCase updateAirportDetailsUseCase) {
        this.updateAirportStatusUseCase = updateAirportStatusUseCase;
        this.searchAirportUseCase = searchAirportUseCase;
        this.registerAirportUseCase = registerAirportUseCase;
        this.updateAirportDetailsUseCase = updateAirportDetailsUseCase;
    }

    public record UpdateAirportDetailsRequest(
            String email,
            String phoneNumber,
            String openTime,
            String closeTime
    ) {}

    @PostMapping
    public ResponseEntity<?> registerAirport(@RequestBody Airport airport) {
        if (airport.getIataCode() == null || airport.getIataCode().trim().length() != 3) {
            return ResponseEntity.badRequest()
                    .body("Erro de Validação: O código IATA deve conter exatamente 3 letras.");
        }
        if (airport.getName() == null || airport.getName().trim().isEmpty() ||
                airport.getCity() == null || airport.getCity().trim().isEmpty() ||
                airport.getCountry() == null || airport.getCountry().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Erro de Validação: Nome, Cidade e País são obrigatórios.");
        }
        try {
            Airport savedAirport = registerAirportUseCase.execute(airport);

            EntityModel<Airport> resource = EntityModel.of(savedAirport);
            resource.add(linkTo(methodOn(FindAirportDetailsController.class).getAirportDetails(savedAirport.getIataCode())).withSelfRel());

            return ResponseEntity.status(201).body(resource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{iataCode}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable String iataCode,
            @RequestParam AirportStatus status) {
        if (iataCode == null || iataCode.trim().length() != 3) {
            return ResponseEntity.badRequest()
                    .body("Erro de Validação: O código IATA deve conter exatamente 3 letras.");
        }
        try {
            Airport updatedAirport = updateAirportStatusUseCase.execute(
                    iataCode.toUpperCase().trim(), status);

            EntityModel<Airport> resource = EntityModel.of(updatedAirport);
            resource.add(linkTo(methodOn(FindAirportDetailsController.class).getAirportDetails(updatedAirport.getIataCode())).withSelfRel());

            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<CollectionModel<EntityModel<Airport>>> searchAirports(
            @RequestParam(required = false) String query) {
        List<Airport> results = searchAirportUseCase.execute(query);

        List<EntityModel<Airport>> airports = results.stream()
                .map(airport -> EntityModel.of(airport,
                        linkTo(methodOn(FindAirportDetailsController.class).getAirportDetails(airport.getIataCode())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Airport>> collectionModel = CollectionModel.of(airports,
                linkTo(methodOn(AirportController.class).searchAirports(query)).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @PatchMapping("/{iataCode}/details")
    public ResponseEntity<?> updateAirportDetails(
            @PathVariable String iataCode,
            @RequestBody UpdateAirportDetailsRequest request) {

        if (iataCode == null || iataCode.trim().length() != 3) {
            return ResponseEntity.badRequest()
                    .body("Erro de Validação: O código IATA deve conter exatamente 3 letras.");
        }

        try {
            ContactInfo contactInfo = null;
            if (request.email() != null || request.phoneNumber() != null) {
                contactInfo = new ContactInfo(request.email(), request.phoneNumber());
            }

            OperationalHours operationalHours = null;
            if (request.openTime() != null && request.closeTime() != null) {
                operationalHours = new OperationalHours(
                        LocalTime.parse(request.openTime()),
                        LocalTime.parse(request.closeTime())
                );
            }

            Airport updated = updateAirportDetailsUseCase.execute(
                    iataCode.toUpperCase().trim(), operationalHours, contactInfo);

            EntityModel<Airport> resource = EntityModel.of(updated);
            resource.add(linkTo(methodOn(FindAirportDetailsController.class).getAirportDetails(updated.getIataCode())).withSelfRel());

            return ResponseEntity.ok(resource);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}