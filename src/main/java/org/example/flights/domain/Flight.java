package org.example.flights.domain;

import jakarta.persistence.*;
import org.example.aircraft.domain.Aircraft;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    private String flightId;

    @Column(nullable = false)
    private LocalDateTime scheduledDatetime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(optional = false)
    @JoinColumn(name = "registration_number", nullable = false)
    private Aircraft aircraft;

    @Version
    private Long version;

    protected Flight() {
    }

    public Flight(Route route, Aircraft aircraft, LocalDateTime scheduledDatetime) {
        if (route == null || aircraft == null || scheduledDatetime == null) {
            throw new IllegalArgumentException("Rota, Aeronave e Data/Hora são obrigatórios.");
        }
        if (scheduledDatetime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de agendamento não pode ser no passado.");
        }

        this.flightId = UUID.randomUUID().toString();
        this.route = route;
        this.aircraft = aircraft;
        this.scheduledDatetime = scheduledDatetime;
        this.status = FlightStatus.SCHEDULED;
    }

    public String getFlightId() { return flightId; }
    public LocalDateTime getScheduledDatetime() { return scheduledDatetime; }
    public FlightStatus getStatus() { return status; }
    public Route getRoute() { return route; }
    public Aircraft getAircraft() { return aircraft; }
    public Long getVersion() { return version; }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }
}