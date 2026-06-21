package org.example.airports.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.ArrayList;

@Embeddable
public record AirportTerminal(
        @JsonProperty("terminal") @NotBlank String terminal,
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "airport_gates", joinColumns = @JoinColumn(name = "airport_id"))
        @JsonProperty("gates") List<AirportGate> gates
) {
    public AirportTerminal {
        if (terminal == null || terminal.isBlank()) {
            throw new IllegalArgumentException("Terminal cannot be null or blank");
        }
        if (gates == null) {
            gates = new ArrayList<>();
        }
    }
}