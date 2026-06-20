package org.example.airports.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record ContactInfo(String email, String phoneNumber) {
    public ContactInfo {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email cannot be blank");
        if (phoneNumber == null || phoneNumber.isBlank()) throw new IllegalArgumentException("Phone number cannot be blank");
    }
}