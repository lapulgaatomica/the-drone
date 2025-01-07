package com.example.the_drone.services;


import com.example.the_drone.dtos.BatteryHistoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DroneBatteryHistoryService {
    Page<BatteryHistoryResponse> findAll(Pageable pageable);
}
