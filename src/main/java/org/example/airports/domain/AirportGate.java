package org.example.airports.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record AirportGate(
        @JsonProperty("gate") @NotBlank String gate
) {
    public AirportGate {
        if (gate == null || gate.isBlank()) {
            throw new IllegalArgumentException("Gate null or empty");
        }
    }
}