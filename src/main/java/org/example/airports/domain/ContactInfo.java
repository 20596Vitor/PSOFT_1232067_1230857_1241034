package org.example.airports.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class ContactInfo {
    private String email;
    private String phoneNumber;

    public ContactInfo() {}

    public ContactInfo(String email, String phoneNumber) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email cannot be blank");
        if (phoneNumber == null || phoneNumber.isBlank()) throw new IllegalArgumentException("Phone number cannot be blank");
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}