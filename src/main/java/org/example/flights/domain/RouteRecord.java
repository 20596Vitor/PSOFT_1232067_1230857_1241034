package org.example.flights.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class RouteRecord {

    @Column(nullable = false)
    private Date recordDate;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private int estimatedFlightTime;

    @Column(nullable = false)
    private float minRange;

    @Column(nullable = false)
    private int minCapacity;

    protected RouteRecord() {
        // Construtor vazio exigido pelo JPA
    }

    public RouteRecord(boolean active, int estimatedFlightTime, float minRange, int minCapacity) {
        this.recordDate = new Date();
        this.active = active;
        this.estimatedFlightTime = estimatedFlightTime;
        this.minRange = minRange;
        this.minCapacity = minCapacity;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public boolean isActive() {
        return active;
    }

    public int getEstimatedFlightTime() {
        return estimatedFlightTime;
    }

    public float getMinRange() {
        return minRange;
    }

    public int getMinCapacity() {
        return minCapacity;
    }
}