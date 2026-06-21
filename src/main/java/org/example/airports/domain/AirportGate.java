package org.example.airports.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class AirportGate {

    @JsonProperty("gate")
    private String gate;

    public AirportGate() {}

    public AirportGate(String gate) {
        this.gate = gate;
    }

    public String getGate() { return gate; }
    public void setGate(String gate) { this.gate = gate; }
}