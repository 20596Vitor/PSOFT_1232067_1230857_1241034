package org.example.aircraft.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "aircraft_models")
public class AircraftModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String manufacturer;

    @Column(name = "model_name", nullable = false, unique = true)
    private String modelName;

    @Column(name = "cruising_speed", nullable = false)
    private Float cruisingSpeed;

    @Column(name = "fuel_capacity", nullable = false)
    private Float fuelCapacity;

    @Column(name = "max_range", nullable = false)
    private Float maxRange;

    @Column(name = "technical_diagram")
    private String technicalDiagram;

    @Lob
    private byte[] image;

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public AircraftModel() {}

    public AircraftModel(String manufacturer, String modelName, Float cruisingSpeed, Float fuelCapacity, Float maxRange) {
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.cruisingSpeed = cruisingSpeed;
        this.fuelCapacity = fuelCapacity;
        this.maxRange = maxRange;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Float getCruisingSpeed() {
        return cruisingSpeed;
    }

    public void setCruisingSpeed(Float cruisingSpeed) {
        this.cruisingSpeed = cruisingSpeed;
    }

    public Float getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Float fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public Float getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(Float maxRange) {
        this.maxRange = maxRange;
    }

    public String getTechnicalDiagram() {
        return technicalDiagram;
    }

    public void setTechnicalDiagram(String technicalDiagram) {
        this.technicalDiagram = technicalDiagram;
    }
}