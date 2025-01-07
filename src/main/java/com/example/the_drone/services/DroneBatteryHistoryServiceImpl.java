package com.example.the_drone.services;

import com.example.the_drone.dtos.BatteryHistoryResponse;
import com.example.the_drone.entities.DroneBatteryHistory;
import com.example.the_drone.repositories.DroneBatteryHistoryRepository;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DroneBatteryHistoryServiceImpl implements DroneBatteryHistoryService {
    private final DroneBatteryHistoryRepository droneBatteryHistoryRepository;
    private final ModelMapper modelMapper;

    public DroneBatteryHistoryServiceImpl(DroneBatteryHistoryRepository droneBatteryHistoryRepository, ModelMapper modelMapper) {
        this.droneBatteryHistoryRepository = droneBatteryHistoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<BatteryHistoryResponse> findAll(Pageable pageable) {
        Page<DroneBatteryHistory> batteryHistoryPage = droneBatteryHistoryRepository.findAll(pageable);
        return batteryHistoryPage.map(batteryHistory -> modelMapper.map(batteryHistory, BatteryHistoryResponse.class));
    }
}
