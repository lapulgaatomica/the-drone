package com.example.the_drone.controllers;

import com.example.the_drone.dtos.BatteryHistoryResponse;
import com.example.the_drone.services.DroneBatteryHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/battery-histories")
public class DroneBatteryHistoryController {
    private final DroneBatteryHistoryService droneBatteryHistoryService;

    public DroneBatteryHistoryController(DroneBatteryHistoryService droneBatteryHistoryService) {
        this.droneBatteryHistoryService = droneBatteryHistoryService;
    }

    @GetMapping
    public ResponseEntity<Page<BatteryHistoryResponse>> findAll(@PageableDefault(size = 100, page = 0)Pageable pageable) {
        Page<BatteryHistoryResponse> batteryHistoryResponsePage = droneBatteryHistoryService.findAll(pageable);
        return ResponseEntity.ok(batteryHistoryResponsePage);
    }
}
