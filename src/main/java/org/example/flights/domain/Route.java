package org.example.flights.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    private String routeId;

    @Column(nullable = false, length = 3)
    private String originIataCode;

    @Column(nullable = false, length = 3)
    private String destinationIataCode;

    @Column(nullable = false)
    private int estimatedFlightTime;

    @Column(nullable = false)
    private float minRange;

    @Column(nullable = false)
    private int minCapacity;

    @Column(nullable = false)
    private boolean active;

    @ElementCollection
    @CollectionTable(name = "route_records", joinColumns = @JoinColumn(name = "route_id"))
    private List<RouteRecord> history = new ArrayList<>();

    @Version
    private Long version;

    protected Route() {
        // Construtor vazio exigido pelo JPA
    }

    public Route(String originIataCode, String destinationIataCode,
                 int estimatedFlightTime, float minRange, int minCapacity) {

        if (originIataCode == null || originIataCode.trim().length() != 3) {
            throw new IllegalArgumentException("O código IATA de origem tem de ter exatamente 3 letras.");
        }
        if (destinationIataCode == null || destinationIataCode.trim().length() != 3) {
            throw new IllegalArgumentException("O código IATA de destino tem de ter exatamente 3 letras.");
        }
        if (originIataCode.equalsIgnoreCase(destinationIataCode)) {
            throw new IllegalArgumentException("A origem e o destino não podem ser o mesmo aeroporto.");
        }
        if (estimatedFlightTime <= 0 || minRange <= 0 || minCapacity <= 0) {
            throw new IllegalArgumentException("Tempo de voo, alcance e capacidade têm de ser maiores que zero.");
        }

        this.routeId = UUID.randomUUID().toString();
        this.originIataCode = originIataCode.toUpperCase();
        this.destinationIataCode = destinationIataCode.toUpperCase();
        this.estimatedFlightTime = estimatedFlightTime;
        this.minRange = minRange;
        this.minCapacity = minCapacity;
        this.active = true;
    }

    public void addHistoryRecord() {
        this.history.add(new RouteRecord(this.active, this.estimatedFlightTime, this.minRange, this.minCapacity));
    }

    public void updateRoute(int estimatedFlightTime, float minRange, int minCapacity) {
        if (estimatedFlightTime <= 0 || minRange <= 0 || minCapacity <= 0) {
            throw new IllegalArgumentException("Valores inválidos para atualização.");
        }
        addHistoryRecord();
        this.estimatedFlightTime = estimatedFlightTime;
        this.minRange = minRange;
        this.minCapacity = minCapacity;
    }

    public void deactivate() {
        addHistoryRecord();
        this.active = false;
    }

    // Getters
    public String getRouteId() { return routeId; }
    public String getOriginIataCode() { return originIataCode; }
    public String getDestinationIataCode() { return destinationIataCode; }
    public int getEstimatedFlightTime() { return estimatedFlightTime; }
    public float getMinRange() { return minRange; }
    public int getMinCapacity() { return minCapacity; }
    public boolean isActive() { return active; }
    public List<RouteRecord> getHistory() { return history; }
    public Long getVersion() { return version; }
}