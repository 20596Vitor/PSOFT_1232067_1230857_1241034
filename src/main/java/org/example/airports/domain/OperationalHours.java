package org.example.airports.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;

@Embeddable
public class OperationalHours {
    private LocalTime openTime;
    private LocalTime closeTime;

    public OperationalHours() {}

    public OperationalHours(LocalTime openTime, LocalTime closeTime) {
        if (openTime != null && closeTime != null && !openTime.isBefore(closeTime)) {
            throw new IllegalArgumentException("openTime must be before closeTime.");
        }
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public LocalTime getOpenTime() { return openTime; }
    public void setOpenTime(LocalTime openTime) { this.openTime = openTime; }
    public LocalTime getCloseTime() { return closeTime; }
    public void setCloseTime(LocalTime closeTime) { this.closeTime = closeTime; }
}