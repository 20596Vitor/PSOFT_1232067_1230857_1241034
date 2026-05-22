package org.example.airports.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private String iataCode;
    private String name;
    private String city;
    private String country;

    @Enumerated(EnumType.STRING)
    private AirportStatus status;

    protected Airport() {}


    public Airport(String iataCode, String name, String city, String country, AirportStatus status) {
        this.iataCode = iataCode;
        this.name = name;
        this.city = city;
        this.country = country;
        this.status = status;
    }


    public void updateStatus(AirportStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("O novo estado não pode ser nulo.");
        }
        this.status = newStatus;
    }

    public String getIataCode() { return iataCode; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public AirportStatus getStatus() { return status; }
}