package com.example.the_drone.repositories;

import com.example.the_drone.entities.DroneBatteryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneBatteryHistoryRepository extends JpaRepository<DroneBatteryHistory, String> {
}
