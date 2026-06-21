package org.example.aircraft.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "aircrafts")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "manufacturing_date", nullable = false)
    private LocalDate manufacturingDate;

    @Column(name = "seating_capacity", nullable = false)
    private Integer seatingCapacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AircraftStatus status;

    @ManyToOne
    @JoinColumn(name = "aircraft_model_id", nullable = false)
    private AircraftModel aircraftModel;

    public Aircraft() {}

    public Aircraft(String registrationNumber, LocalDate manufacturingDate, Integer seatingCapacity, AircraftStatus status, AircraftModel aircraftModel) {
        this.registrationNumber = registrationNumber;
        this.manufacturingDate = manufacturingDate;
        this.seatingCapacity = seatingCapacity;
        this.status = status;
        this.aircraftModel = aircraftModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public AircraftStatus getStatus() {
        return status;
    }

    public void setStatus(AircraftStatus status) {
        this.status = status;
    }

    public AircraftModel getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(AircraftModel aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    private Double totalOperationalHours = 0.0;

    public Double getTotalOperationalHours() {
        return totalOperationalHours;
    }

    public void setTotalOperationalHours(Double totalOperationalHours) {
        this.totalOperationalHours = totalOperationalHours;
    }
}