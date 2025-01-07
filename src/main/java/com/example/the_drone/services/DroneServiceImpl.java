package com.example.the_drone.services;

import com.example.the_drone.dtos.DroneResponse;
import com.example.the_drone.dtos.LoadDroneRequest;
import com.example.the_drone.dtos.MedicationResponse;
import com.example.the_drone.dtos.RegisterDroneRequest;
import com.example.the_drone.entities.Drone;
import com.example.the_drone.entities.Medication;
import com.example.the_drone.enums.State;
import com.example.the_drone.exceptions.LowBatteryException;
import com.example.the_drone.exceptions.WeightLimitExceededException;
import com.example.the_drone.repositories.DroneRepository;
import com.example.the_drone.repositories.MedicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneServiceImpl implements DroneService {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final ModelMapper modelMapper;

    public DroneServiceImpl(DroneRepository droneRepository, MedicationRepository medicationRepository, ModelMapper modelMapper) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
        this.modelMapper = modelMapper;
    }


    public DroneResponse registerDrone(RegisterDroneRequest droneRequest) {
        Drone drone = new Drone(droneRequest.serialNumber(), droneRequest.model(), droneRequest.weightLimit(),
                droneRequest.batteryPercentage(), droneRequest.state());
        Drone newDrone = droneRepository.save(drone);
        return modelMapper.map(newDrone, DroneResponse.class);
    }

    public DroneResponse loadDrone(String droneId, LoadDroneRequest loadDroneRequest) {
        Drone drone = droneRepository.findById(droneId).orElseThrow(() -> new EntityNotFoundException("Drone not found"));

        if (drone.getBatteryPercentage() < 25) {
            throw new LowBatteryException("Battery level too low for loading");
        }

        List<Medication> medications = medicationRepository.findAllById(loadDroneRequest.medicationIds());
        int totalWeight = medications.stream().mapToInt(Medication::getWeight).sum();
        if (totalWeight > drone.getWeightLimit()) {
            throw new WeightLimitExceededException("Total weight exceeds drone's weight limit");
        }

        medications.forEach(medication -> medication.setDrone(drone));
        medicationRepository.saveAll(medications);
        drone.setState(State.LOADED);
        Drone updatedDrone = droneRepository.save(drone);
        updatedDrone.setMedications(medications);
        return modelMapper.map(updatedDrone, DroneResponse.class);
    }

    public List<MedicationResponse> getLoadedMedications(String droneId) {
        Drone drone = droneRepository.findById(droneId).orElseThrow(() -> new EntityNotFoundException("Drone not found"));
        List<Medication> medications = drone.getMedications();
        return medications.stream().map((medication) -> modelMapper.map(medication, MedicationResponse.class)).toList();
    }

    public List<DroneResponse> getAvailableDrones() {
        List<Drone> drones = droneRepository.findAllByState(State.IDLE);
        return drones.stream().map((drone) -> modelMapper.map(drone, DroneResponse.class)).toList();
    }

    public int getBatteryLevel(String droneId) {
        return droneRepository.findById(droneId)
                .map(Drone::getBatteryPercentage)
                .orElseThrow(() -> new EntityNotFoundException("Drone not found"));
    }

    public DroneResponse rechargeDrone(String droneId) {
        Drone drone = droneRepository.findById(droneId).orElseThrow(() -> new EntityNotFoundException("Drone not found"));
        drone.setBatteryPercentage(100);
        Drone updatedDrone = droneRepository.save(drone);
        return modelMapper.map(updatedDrone, DroneResponse.class);
    }

    public DroneResponse setState(String droneId, State state) {
        Drone drone = droneRepository.findById(droneId).orElseThrow(() -> new EntityNotFoundException("Drone not found"));
        drone.setState(state);
        Drone updatedDrone = droneRepository.save(drone);
        return modelMapper.map(updatedDrone, DroneResponse.class);
    }
}
