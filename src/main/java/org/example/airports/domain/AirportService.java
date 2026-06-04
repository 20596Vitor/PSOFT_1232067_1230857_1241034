package org.example.airports.domain;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;


@Embeddable
public record AirportService(@NotBlank String service) {
    public AirportService{
        if(service == null || service.isEmpty()) {
            throw new IllegalArgumentException("Service cannot be null or empty");
        }

    }
}
