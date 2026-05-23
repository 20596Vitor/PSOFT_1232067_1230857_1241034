package org.example.airports.controller;

import org.example.airports.services.AddCertificationUcase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airports")
public class AddAirplaneCertificationController {

    private final AddCertificationUcase addCertificationUcase;

    public AddAirplaneCertificationController(AddCertificationUcase addCertificationUcase) {
        this.addCertificationUcase = addCertificationUcase;
    }

    @PostMapping("/{iataCode}/certifications")
    public ResponseEntity<String> addCertification(
            @PathVariable String iataCode,
            @RequestParam String type) {
        try {

            addCertificationUcase.execute(iataCode, type);

            return ResponseEntity.ok("Certificação '" + type + "' adicionada ao aeroporto " + iataCode);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}