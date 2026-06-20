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

    // US207 - terminals, gates, services
    @Embedded
    private AirportGate gate;

    @Embedded
    private AirportTerminal terminal;

    @Embedded
    private AirportService service;

    @Embedded
    private ContactInfo contactInfo;

    @Embedded
    private OperationalHours operationalHours;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "airport_certified_models", joinColumns = @JoinColumn(name = "airport_id"))
    @Column(name = "airplane_model")
    private List<String> certifiedAirplaneModels = new ArrayList<>();

    public Airport() {}

    public Airport(String iataCode, String name, String city, String country,
                   String type, AirportStatus status, AirportGate gate, AirportTerminal terminal) {
        this.iataCode = iataCode;
        this.name = name;
        this.city = city;
        this.country = country;
        this.type = type;
        this.status = status;
        this.gate = gate;
        this.terminal = terminal;
    }

    public void addAirplaneCertification(String airplaneModel) {
        String modelUpper = airplaneModel.toUpperCase().trim();
        if (!this.certifiedAirplaneModels.contains(modelUpper)) {
            this.certifiedAirplaneModels.add(modelUpper);
        }
    }
    public void updateOperationalData(OperationalHours operationalHours, ContactInfo contactInfo) {
        if (operationalHours != null) this.operationalHours = operationalHours;
        if (contactInfo != null) this.contactInfo = contactInfo;
    }

    public void updateStatus(AirportStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("O novo estado não pode ser nulo.");
        }
        this.status = newStatus;
    }

    // Getters
    public Long getId() { return id; }
    public String getIataCode() { return iataCode; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getType() { return type; }
    public String getAirportType() { return type; }
    public AirportStatus getStatus() { return status; }
    public AirportGate getGate() { return gate; }
    public AirportTerminal getTerminal() { return terminal; }
    public AirportService getAirportService() { return service; }
    public ContactInfo getContactInfo() { return contactInfo; }
    public OperationalHours getOperationalHours() { return operationalHours; }
    public List<String> getCertifiedAirplaneModels() { return certifiedAirplaneModels; }

    public void setAirportType(String airportType) { this.type = airportType; }

    public void changeStatus(AirportStatus status) {
    }
}