package com.example.the_drone.dtos;

import com.example.the_drone.enums.Model;
import com.example.the_drone.enums.State;

import java.util.List;

public class DroneResponse {
        private String id;
        private String serialNumber;
        private Model model;
        private Integer weightLimit;
        private Integer batteryPercentage;
        private State state;
        private List<MedicationResponse> medications;

    public DroneResponse() {
    }

    public DroneResponse(String id, String serialNumber, Model model, Integer weightLimit, Integer batteryPercentage,
                         State state, List<MedicationResponse> medications) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryPercentage = batteryPercentage;
        this.state = state;
        this.medications = medications;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Integer getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Integer weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Integer getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(Integer batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<MedicationResponse> getMedications() {
        return medications;
    }

    public void setMedications(List<MedicationResponse> medications) {
        this.medications = medications;
    }
}
