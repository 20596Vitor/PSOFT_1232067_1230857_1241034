package org.example.airports.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class AirportTerminal {

    @JsonProperty("terminal")
    private String terminal;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "airport_gates", joinColumns = @JoinColumn(name = "airport_id"))
    @JsonProperty("gates")
    private List<AirportGate> gates = new ArrayList<>();

    public AirportTerminal() {}

    public AirportTerminal(String terminal, List<AirportGate> gates) {
        this.terminal = terminal;
        this.gates = gates != null ? gates : new ArrayList<>();
    }

    public String getTerminal() { return terminal; }
    public void setTerminal(String terminal) { this.terminal = terminal; }
    public List<AirportGate> getGates() { return gates; }
    public void setGates(List<AirportGate> gates) { this.gates = gates; }
}