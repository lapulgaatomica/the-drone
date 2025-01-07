package com.example.the_drone.services;

import com.example.the_drone.dtos.DroneResponse;
import com.example.the_drone.dtos.LoadDroneRequest;
import com.example.the_drone.dtos.MedicationResponse;
import com.example.the_drone.dtos.RegisterDroneRequest;
import com.example.the_drone.enums.State;

import java.util.List;

public interface DroneService {
    DroneResponse registerDrone(RegisterDroneRequest droneRequest);
    DroneResponse loadDrone(String droneId, LoadDroneRequest loadDroneRequest);
    List<DroneResponse> getAvailableDrones();
    int getBatteryLevel(String droneId);
    DroneResponse rechargeDrone(String droneId);
    DroneResponse setState(String droneId, State state);
    List<MedicationResponse> getLoadedMedications(String droneId);
}
