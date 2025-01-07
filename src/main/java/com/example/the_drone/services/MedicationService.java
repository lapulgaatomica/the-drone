package com.example.the_drone.services;

import com.example.the_drone.dtos.MedicationResponse;
import com.example.the_drone.dtos.RegisterMedicationRequest;

import java.util.List;

public interface MedicationService {
    MedicationResponse createMedication(RegisterMedicationRequest medicationRequest);
    MedicationResponse deliverMedication(String medicationId);
    List<MedicationResponse> getAllMedications();
}
