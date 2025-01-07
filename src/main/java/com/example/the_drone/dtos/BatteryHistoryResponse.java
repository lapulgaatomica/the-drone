package com.example.the_drone.dtos;

import java.time.LocalDateTime;

public class BatteryHistoryResponse {
    private String id;
    private LocalDateTime currentDayTime;
    private Integer batteryPercentage;
    private String droneId;

    public BatteryHistoryResponse() {
    }

    public BatteryHistoryResponse(String id, LocalDateTime currentDayTime, Integer batteryPercentage, String droneId) {
        this.id = id;
        this.currentDayTime = currentDayTime;
        this.batteryPercentage = batteryPercentage;
        this.droneId = droneId;
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

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }
}
