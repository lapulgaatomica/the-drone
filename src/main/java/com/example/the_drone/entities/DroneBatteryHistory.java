package com.example.the_drone.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "drone_battery_history")
public class DroneBatteryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDateTime currentDayTime;
    private Integer batteryPercentage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id", referencedColumnName = "id")
    private Drone drone;

    public DroneBatteryHistory() {
    }

    public DroneBatteryHistory(LocalDateTime currentDayTime, Integer batteryPercentage, Drone drone) {
        this.currentDayTime = currentDayTime;
        this.batteryPercentage = batteryPercentage;
        this.drone = drone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCurrentDayTime() {
        return currentDayTime;
    }

    public void setCurrentDayTime(LocalDateTime currentDayTime) {
        this.currentDayTime = currentDayTime;
    }

    public Integer getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(Integer batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DroneBatteryHistory that = (DroneBatteryHistory) o;
        return Objects.equals(id, that.id) && Objects.equals(currentDayTime, that.currentDayTime) && Objects.equals(batteryPercentage, that.batteryPercentage) && Objects.equals(drone, that.drone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currentDayTime, batteryPercentage, drone);
    }

    @Override
    public String toString() {
        return "DroneBatteryHistory{" +
                "id='" + id + '\'' +
                ", currentTime=" + currentDayTime +
                ", batteryPercentage=" + batteryPercentage +
                ", drone=" + drone +
                '}';
    }
}
