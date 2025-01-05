package com.example.the_drone.entities;

import com.example.the_drone.enums.Model;
import com.example.the_drone.enums.State;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private Model model;
    private Integer weightLimit;
    private Integer batteryPercentage;
    @Enumerated(EnumType.STRING)
    private State state;

    public Drone() {
    }

    public Drone(String serialNumber, Model model, Integer weightLimit,
                 Integer batteryPercentage, State state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryPercentage = batteryPercentage;
        this.state = state;
    }

    public Drone(String id, String serialNumber, Model model,
                 Integer weightLimit, Integer batteryPercentage, State state) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryPercentage = batteryPercentage;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Model getModel() {
        return model;
    }

    public Integer getWeightLimit() {
        return weightLimit;
    }

    public Integer getBatteryPercentage() {
        return batteryPercentage;
    }

    public State getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return Objects.equals(id, drone.id) && Objects.equals(serialNumber, drone.serialNumber) && model == drone.model && Objects.equals(weightLimit, drone.weightLimit) && Objects.equals(batteryPercentage, drone.batteryPercentage) && state == drone.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, model, weightLimit, batteryPercentage, state);
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id='" + id + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", model=" + model +
                ", weightLimit=" + weightLimit +
                ", batteryPercentage=" + batteryPercentage +
                ", state=" + state +
                '}';
    }
}
