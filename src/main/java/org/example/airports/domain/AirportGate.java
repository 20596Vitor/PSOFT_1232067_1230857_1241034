package org.example.airports.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record AirportGate(@NotBlank String gate) {
    public AirportGate {
        if (gate == null || gate.isBlank()){
            throw new IllegalArgumentException("Gate null or empty");
        }
    }
}