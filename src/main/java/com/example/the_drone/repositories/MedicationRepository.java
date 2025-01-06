package com.example.the_drone.repositories;

import com.example.the_drone.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {
    List<Medication> findByDroneId(Long droneId);
}
