package com.example.the_drone.controllers;

import com.example.the_drone.dtos.DroneResponse;
import com.example.the_drone.dtos.LoadDroneRequest;
import com.example.the_drone.dtos.MedicationResponse;
import com.example.the_drone.dtos.RegisterDroneRequest;
import com.example.the_drone.enums.State;
import com.example.the_drone.services.DroneService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drones")
public class DroneController {
    //todo, test the image upload, documentation
    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping
    public ResponseEntity<DroneResponse> registerDrone(@Validated @RequestBody RegisterDroneRequest droneRequest) {
        return ResponseEntity.accepted().body(droneService.registerDrone(droneRequest));
    }

    @PutMapping("/{id}/load")
    public ResponseEntity<DroneResponse> loadDrone(@PathVariable("id") String droneId, @RequestBody LoadDroneRequest loadDroneRequest) {
        return ResponseEntity.accepted().body(droneService.loadDrone(droneId, loadDroneRequest));
    }

    @GetMapping("/{id}/loaded-medications")
    public ResponseEntity<List<MedicationResponse>> getLoadedMedications(@PathVariable("id") String droneId) {
        return ResponseEntity.ok(droneService.getLoadedMedications(droneId));
    }

    @GetMapping("/available")
    public ResponseEntity<List<DroneResponse>> getAvailableDrones() {
        return ResponseEntity.ok(droneService.getAvailableDrones());
    }

    @GetMapping("/{id}/battery-level")
    public ResponseEntity<Integer> getBatteryLevel(@PathVariable("id") String droneId) {
        return ResponseEntity.ok(droneService.getBatteryLevel(droneId));
    }

    @PatchMapping("/drone/{id}/recharge-battery")
    public ResponseEntity<DroneResponse> rechargeDrone(@PathVariable("id") String droneId) {
        return ResponseEntity.accepted().body(droneService.rechargeDrone(droneId));
    }

    @PatchMapping("/{id}/set-state")
    public ResponseEntity<DroneResponse> setState(@PathVariable("id") String droneId, @RequestParam("state") State state) {
        return ResponseEntity.accepted().body(droneService.setState(droneId, state));
    }
}
