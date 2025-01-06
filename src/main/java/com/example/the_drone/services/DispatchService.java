package com.example.the_drone.services;

import com.example.the_drone.dtos.RegisterDroneRequest;
import com.example.the_drone.entities.Drone;
import com.example.the_drone.entities.Medication;

import java.util.List;

public interface DispatchService {
    Drone registerDrone(RegisterDroneRequest droneRequest);
    void loadDrone(String droneId, List<Medication> medications);
    List<Medication> getLoadedMedications(Long droneId);
    List<Drone> getAvailableDrones();
    int getBatteryLevel(String droneId);

}
