package com.example.the_drone.config;

import com.example.the_drone.entities.Drone;
import com.example.the_drone.entities.DroneBatteryHistory;
import com.example.the_drone.repositories.DroneBatteryHistoryRepository;
import com.example.the_drone.repositories.DroneRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
@EnableAsync
public class AuditService {
    private final DroneRepository droneRepository;
    private final DroneBatteryHistoryRepository batteryHistoryRepository;

    public AuditService(DroneRepository droneRepository, DroneBatteryHistoryRepository batteryHistoryRepository) {
        this.droneRepository = droneRepository;
        this.batteryHistoryRepository = batteryHistoryRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Async
    public void logBatteryLevels() {
        List<Drone> drones = droneRepository.findAll();
        drones.forEach(drone -> System.out.println("Drone " + drone.getId() + " battery: " + drone.getBatteryPercentage() + "%"));
        List<DroneBatteryHistory> droneBatteryHistories = new ArrayList<>();
        for(Drone drone : drones) {
            DroneBatteryHistory droneBatteryHistory = new DroneBatteryHistory();
            droneBatteryHistory.setDrone(drone);
            droneBatteryHistory.setBatteryPercentage(drone.getBatteryPercentage());
            droneBatteryHistory.setCurrentDayTime(LocalDateTime.now());
            droneBatteryHistories.add(droneBatteryHistory);
        }
        batteryHistoryRepository.saveAll(droneBatteryHistories);
    }
}
