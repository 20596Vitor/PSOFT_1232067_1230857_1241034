package org.example.airports.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
public class AirportType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AirportType_dbID")
    private Long id;
    private String type;

    public AirportType(@JsonProperty("type") @NotNull String type) {
        this.type = type;
    }

    public AirportType() {
    }
}
