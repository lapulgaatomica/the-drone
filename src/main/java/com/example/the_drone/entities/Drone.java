package com.example.the_drone.entities;

import com.example.the_drone.enums.Model;
import com.example.the_drone.enums.State;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "drone")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "serial_number", unique = true)
    private String serialNumber;
    @Column(name = "model")
    @Enumerated(EnumType.STRING)
    private Model model;
    @Column(name = "weight_limit")
    private Integer weightLimit;
    @Column(name = "battery_percentage")
    private Integer batteryPercentage;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "drone")
    private List<Medication> medications;

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

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
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
