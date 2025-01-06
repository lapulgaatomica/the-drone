package com.example.the_drone.services;

import com.example.the_drone.dtos.RegisterDroneRequest;
import com.example.the_drone.entities.Drone;
import com.example.the_drone.entities.Medication;
import com.example.the_drone.enums.State;
import com.example.the_drone.exceptions.LowBatteryException;
import com.example.the_drone.exceptions.WeightLimitExceededException;
import com.example.the_drone.repositories.DroneRepository;
import com.example.the_drone.repositories.MedicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispatchServiceImpl implements DispatchService {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    public DispatchServiceImpl(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    public Drone registerDrone(RegisterDroneRequest droneRequest) {
        Drone drone = new Drone(droneRequest.serialNumber(), droneRequest.model(), droneRequest.weightLimit(),
                droneRequest.batteryPercentage(), droneRequest.state());
        return droneRepository.save(drone);
    }

    public void loadDrone(String droneId, List<Medication> medications) {
        Drone drone = droneRepository.findById(droneId).orElseThrow(() -> new EntityNotFoundException("Drone not found"));

        if (drone.getBatteryPercentage() < 25) {
            throw new LowBatteryException("Battery level too low for loading");
        }

        int totalWeight = medications.stream().mapToInt(Medication::getWeight).sum();
        if (totalWeight > drone.getWeightLimit()) {
            throw new WeightLimitExceededException("Total weight exceeds drone's weight limit");
        }

        medications.forEach(medication -> medication.setDrone(drone));
        medicationRepository.saveAll(medications);
        drone.setState(State.LOADED);
        droneRepository.save(drone);
    }

    public List<Medication> getLoadedMedications(Long droneId) {
        return medicationRepository.findByDroneId(droneId);
    }

    public List<Drone> getAvailableDrones() {
        return droneRepository.findAllByState(State.IDLE);
    }

    public int getBatteryLevel(String droneId) {
        return droneRepository.findById(droneId)
                .map(Drone::getBatteryPercentage)
                .orElseThrow(() -> new EntityNotFoundException("Drone not found"));
    }

    //deliver medication -> this means the weight it's currently carrying reduces
    //recharge drone battery -> that means changing it to 100
    //battery level audit -> per instruction
}
