package org.example.airports.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;


@Embeddable
public record AirportTerminal(@NotBlank String terminal) {

    public AirportTerminal {
        if (terminal == null || terminal.isBlank()) {
            throw new IllegalArgumentException("Terminal cannot be null or blank");
        }
    }

}
