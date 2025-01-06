package com.example.the_drone.repositories;

import com.example.the_drone.entities.Drone;
import com.example.the_drone.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findAllByState(State state);
}
