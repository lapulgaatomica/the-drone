package com.example.the_drone.controllers;

import com.example.the_drone.dtos.MedicationResponse;
import com.example.the_drone.dtos.RegisterMedicationRequest;
import com.example.the_drone.entities.Medication;
import com.example.the_drone.services.MedicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications")
public class MedicationController {
    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping
    public ResponseEntity<MedicationResponse> createMedication(@Validated @RequestBody RegisterMedicationRequest medicationRequest) {
        return ResponseEntity.accepted().body(medicationService.createMedication(medicationRequest));
    }

    @PatchMapping("/{id}/deliver")
    public ResponseEntity<MedicationResponse> deliverMedication(@PathVariable("id") String medicationId) {
        return ResponseEntity.accepted().body(medicationService.deliverMedication(medicationId));
    }

    @GetMapping
    public ResponseEntity<List<MedicationResponse>> getAllMedications() {
        return ResponseEntity.ok(medicationService.getAllMedications());
    }
}
