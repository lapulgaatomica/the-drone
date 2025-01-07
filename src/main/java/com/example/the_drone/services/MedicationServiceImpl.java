package com.example.the_drone.services;

import com.example.the_drone.dtos.MedicationResponse;
import com.example.the_drone.dtos.RegisterMedicationRequest;
import com.example.the_drone.entities.Drone;
import com.example.the_drone.entities.Medication;
import com.example.the_drone.enums.State;
import com.example.the_drone.repositories.DroneRepository;
import com.example.the_drone.repositories.MedicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final DroneRepository droneRepository;
    private final ModelMapper modelMapper;

    public MedicationServiceImpl(MedicationRepository medicationRepository, DroneRepository droneRepository, ModelMapper modelMapper) {
        this.medicationRepository = medicationRepository;
        this.droneRepository = droneRepository;
        this.modelMapper = modelMapper;
    }

    public MedicationResponse createMedication(RegisterMedicationRequest medicationRequest) {
        Medication medication = new Medication(medicationRequest.name(), medicationRequest.weight(), medicationRequest.code());
        if (medicationRequest.image() != null) {
            medication.setImage(medicationRequest.image());
        }
        Medication newMedication = medicationRepository.save(medication);
        return modelMapper.map(newMedication, MedicationResponse.class);
    }

    public MedicationResponse deliverMedication(String medicationId) {
        Medication medication = medicationRepository.findById(medicationId).orElseThrow(() -> new EntityNotFoundException("Medication not found"));
        Drone drone = medication.getDrone();
        medication.setDrone(null);
        Medication updatedMedication = medicationRepository.save(medication);
        resetDroneState(drone);
        return modelMapper.map(updatedMedication, MedicationResponse.class);
    }

    public List<MedicationResponse> getAllMedications() {
        List<Medication> medications = medicationRepository.findAll();
        return medications.stream().map((medication) -> modelMapper.map(medication, MedicationResponse.class)).toList();
    }

    private Drone resetDroneState(Drone drone) {
        if(drone == null) {
            throw new EntityNotFoundException("Drone not found");
        }
        List<Medication> medications = drone.getMedications();
        if(medications.isEmpty()) {
            drone.setState(State.IDLE);
        }
        drone.setBatteryPercentage(drone.getBatteryPercentage() > 10 ? drone.getBatteryPercentage() - 10 : 0);
        return droneRepository.save(drone);
    }
}
