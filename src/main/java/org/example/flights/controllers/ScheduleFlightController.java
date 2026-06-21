package org.example.flights.controllers;

import org.example.flights.domain.Flight;
import org.example.flights.services.ScheduleFlightUseCase;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/flights")
public class ScheduleFlightController {

    private final ScheduleFlightUseCase scheduleFlightUseCase;

    public ScheduleFlightController(ScheduleFlightUseCase scheduleFlightUseCase) {
        this.scheduleFlightUseCase = scheduleFlightUseCase;
    }

    public record ScheduleFlightRequest(String routeId, String aircraftRegistration, LocalDateTime scheduledDatetime) {}

    @PostMapping
    public ResponseEntity<?> scheduleFlight(@RequestBody ScheduleFlightRequest request) {
        try {
            Flight flight = scheduleFlightUseCase.execute(
                    request.routeId(),
                    request.aircraftRegistration(),
                    request.scheduledDatetime()
            );

            EntityModel<Flight> resource = EntityModel.of(flight);

            resource.add(linkTo(methodOn(ScheduleFlightController.class).scheduleFlight(request)).withSelfRel());

            return ResponseEntity.status(201).body(resource);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409 Conflict
        }
    }
}