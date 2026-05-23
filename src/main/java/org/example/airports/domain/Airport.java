package org.example.airports.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String iataCode;
    private String name;
    private String city;
    private String country;
    private String type;
    @Enumerated(EnumType.STRING)
    private AirportStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "airport_certified_models", joinColumns = @JoinColumn(name = "airport_id"))
    @Column(name = "airplane_model")
    private List<String> certifiedAirplaneModels = new ArrayList<>();

    public Airport() {}

    public Airport(String iataCode, String name, String city, String country,String type, AirportStatus status) {
        this.iataCode = iataCode;
        this.name = name;
        this.city = city;
        this.country = country;
        this.type = type;
        this.status = status;
    }

    public void addAirplaneCertification(String airplaneModel) {
        String modelUpper = airplaneModel.toUpperCase().trim();
        if (!this.certifiedAirplaneModels.contains(modelUpper)) {
            this.certifiedAirplaneModels.add(modelUpper);
        }
    }

    public List<String> getCertifiedAirplaneModels() {
        return certifiedAirplaneModels;
    }
    public String getIataCode() { return iataCode; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getType() { return type; }
    public AirportStatus getStatus() { return status; }

    public void setAirportType(String airportType) {
        this.type = airportType;
    }
    public String getAirportType() {
        return type;
    }

    public void updateStatus(AirportStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("O novo estado não pode ser nulo.");
        }
        this.status = newStatus;
    }
}