package org.example.airports.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;

@Embeddable
public record OperationalHours(LocalTime openTime, LocalTime closeTime) {
    public OperationalHours {
        if (openTime != null && closeTime != null && !openTime.isBefore(closeTime)) {
            throw new IllegalArgumentException("openTime must be before closeTime.");
        }
    }
}