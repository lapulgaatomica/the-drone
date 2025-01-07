package com.example.the_drone.services;

import com.example.the_drone.dtos.MedicationResponse;
import com.example.the_drone.dtos.RegisterMedicationRequest;
import com.example.the_drone.entities.Drone;
import com.example.the_drone.entities.Medication;
import com.example.the_drone.enums.Model;
import com.example.the_drone.enums.State;
import com.example.the_drone.repositories.DroneRepository;
import com.example.the_drone.repositories.MedicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MedicationServiceImplTest {
    @InjectMocks
    private MedicationServiceImpl medicationService;
    @Mock
    private MedicationRepository medicationRepository;
    @Mock
    private DroneRepository droneRepository;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMedicationShouldReturnNewMedication() {
        RegisterMedicationRequest request = new RegisterMedicationRequest("Med1", 50, "CODE123", null);
        Medication medication = new Medication("Med1", 50, "CODE123");
        medication.setImage(null);
        Medication savedMedication = new Medication("Med1", 50, "CODE123");
        savedMedication.setImage(null);
        savedMedication.setId("med1");
        MedicationResponse response = new MedicationResponse("med1", "Med1", 50, "CODE123", null);

        when(medicationRepository.save(any(Medication.class))).thenReturn(savedMedication);
        when(modelMapper.map(savedMedication, MedicationResponse.class)).thenReturn(response);

        MedicationResponse result = medicationService.createMedication(request);

        assertNotNull(result);
        assertEquals(response, result);
        verify(medicationRepository).save(any(Medication.class));
        verify(modelMapper).map(savedMedication, MedicationResponse.class);
    }

    @Test
    void deliverMedicationShouldDetachDroneAndReturnUpdatedMedication() {
        String medicationId = "med1";
        Medication medication = new Medication("Med1", 50, "CODE123");
        Drone drone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 80, State.LOADED);
        medication.setDrone(drone);
        drone.setMedications(List.of(medication));
        medication.setId(medicationId);
        Medication updatedMedication = new Medication("Med1", 50, "CODE123");
        updatedMedication.setId(medicationId);
        MedicationResponse response = new MedicationResponse("med1", "Med1", 50, "CODE123", null);

        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(medication));
        when(medicationRepository.save(any(Medication.class))).thenReturn(updatedMedication);
        when(modelMapper.map(updatedMedication, MedicationResponse.class)).thenReturn(response);
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        MedicationResponse result = medicationService.deliverMedication(medicationId);

        assertNotNull(result);
        assertEquals(response, result);
        assertNull(updatedMedication.getDrone());
        verify(medicationRepository).findById(medicationId);
        verify(medicationRepository).save(any(Medication.class));
        verify(droneRepository).save(any(Drone.class));
        verify(modelMapper).map(updatedMedication, MedicationResponse.class);
    }

    @Test
    void deliverMedicationShouldThrowEntityNotFoundException_WhenMedicationNotFound() {
        String medicationId = "invalidId";

        when(medicationRepository.findById(medicationId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> medicationService.deliverMedication(medicationId));
        verify(medicationRepository).findById(medicationId);
    }

    @Test
    void getAllMedicationsShouldReturnListOfMedications() {
        Medication medication1 = new Medication("Med1", 50, "CODE123");
        Medication medication2 = new Medication("Med2", 100, "CODE456");
        MedicationResponse response1 = new MedicationResponse("med1", "Med1", 50, "CODE123", null);
        MedicationResponse response2 = new MedicationResponse("med2", "Med2", 100, "CODE456", null);

        when(medicationRepository.findAll()).thenReturn(List.of(medication1, medication2));
        when(modelMapper.map(medication1, MedicationResponse.class)).thenReturn(response1);
        when(modelMapper.map(medication2, MedicationResponse.class)).thenReturn(response2);

        List<MedicationResponse> result = medicationService.getAllMedications();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(response1));
        assertTrue(result.contains(response2));
        verify(medicationRepository).findAll();
        verify(modelMapper).map(medication1, MedicationResponse.class);
        verify(modelMapper).map(medication2, MedicationResponse.class);
    }
}