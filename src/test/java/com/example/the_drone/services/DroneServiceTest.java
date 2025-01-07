package com.example.the_drone.services;

import com.example.the_drone.dtos.DroneResponse;
import com.example.the_drone.dtos.LoadDroneRequest;
import com.example.the_drone.dtos.MedicationResponse;
import com.example.the_drone.dtos.RegisterDroneRequest;
import com.example.the_drone.entities.Drone;
import com.example.the_drone.entities.Medication;
import com.example.the_drone.enums.Model;
import com.example.the_drone.enums.State;
import com.example.the_drone.exceptions.LowBatteryException;
import com.example.the_drone.exceptions.WeightLimitExceededException;
import com.example.the_drone.repositories.DroneRepository;
import com.example.the_drone.repositories.MedicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DroneServiceTest {
    @Mock
    private DroneRepository droneRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DroneServiceImpl droneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerDrone() {
        RegisterDroneRequest request = new RegisterDroneRequest("SN123", Model.LIGHTWEIGHT, 500, 80, State.IDLE);
        Drone drone = new Drone("SN123", Model.LIGHTWEIGHT, 500, 80, State.IDLE);
        Drone savedDrone = new Drone("SN123", Model.LIGHTWEIGHT, 500, 80, State.IDLE);
        DroneResponse response = new DroneResponse("id1", "SN123", Model.LIGHTWEIGHT, 500, 80, State.IDLE, List.of());

        when(droneRepository.save(any(Drone.class))).thenReturn(savedDrone);
        when(modelMapper.map(savedDrone, DroneResponse.class)).thenReturn(response);

        DroneResponse result = droneService.registerDrone(request);

        assertNotNull(result);
        assertEquals(response, result);
        verify(droneRepository).save(any(Drone.class));
        verify(modelMapper).map(savedDrone, DroneResponse.class);
    }


    @Test
    void loadDroneShouldThrowLowBatteryExceptionWhenBatteryTooLow() {
        String droneId = "drone1";
        LoadDroneRequest request = new LoadDroneRequest(List.of("med1", "med2"));
        Drone drone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 20, State.IDLE);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        assertThrows(LowBatteryException.class, () -> droneService.loadDrone(droneId, request));
        verify(droneRepository).findById(droneId);
    }

    @Test
    void loadDroneShouldThrowWeightLimitExceededExceptionWhenTotalWeightExceedsLimit() {
        String droneId = "drone1";
        LoadDroneRequest request = new LoadDroneRequest(List.of("med1", "med2"));
        Drone drone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 80, State.IDLE);
        Medication med1 = new Medication("med1", 300, "300", null, drone);
        Medication med2 = new Medication("med2", 300, "300", null, drone);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(medicationRepository.findAllById(request.medicationIds())).thenReturn(List.of(med1, med2));

        assertThrows(WeightLimitExceededException.class, () -> droneService.loadDrone(droneId, request));
        verify(droneRepository).findById(droneId);
        verify(medicationRepository).findAllById(request.medicationIds());
    }

    @Test
    void loadDroneShouldLoadDroneWithMedications() {
        String droneId = "drone1";
        LoadDroneRequest request = new LoadDroneRequest(List.of("med1", "med2"));
        Drone drone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 80, State.IDLE);
        Medication med1 = new Medication("med1", 100, "001", null, drone);
        Medication med2 = new Medication("med2", 100, "002", null, drone);
        Drone updatedDrone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 80, State.LOADED);
        updatedDrone.setMedications(List.of(med1, med2));
        DroneResponse response = new DroneResponse("drone1", "", Model.LIGHTWEIGHT, 500, 80, State.LOADED, any(List.class));


        when(medicationRepository.findAllById(request.medicationIds())).thenReturn(List.of(med1, med2));
        when(droneRepository.save(any(Drone.class))).thenReturn(updatedDrone);
        when(modelMapper.map(updatedDrone, DroneResponse.class)).thenReturn(response);
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        DroneResponse result = droneService.loadDrone(droneId, request);

        assertNotNull(result);
        assertEquals(response, result);
        verify(droneRepository).findById(droneId);
        verify(medicationRepository).findAllById(request.medicationIds());
        verify(droneRepository).save(any(Drone.class));
        verify(modelMapper).map(updatedDrone, DroneResponse.class);
    }

    @Test
    void getLoadedMedicationsShouldReturnMedicationsForDrone() {
        String droneId = "drone1";
        Drone drone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 80, State.LOADED);
        Medication med1 = new Medication("med1", 100, "001", null, drone);
        Medication med2 = new Medication("med2", 100, "002", null, drone);
        drone.setMedications(List.of(med1, med2));
        MedicationResponse medResponse1 = new MedicationResponse("med1", "name1", 200, "code1", null);
        MedicationResponse medResponse2 = new MedicationResponse("med2", "name2", 200, "code2", null);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(modelMapper.map(med1, MedicationResponse.class)).thenReturn(medResponse1);
        when(modelMapper.map(med2, MedicationResponse.class)).thenReturn(medResponse2);

        List<MedicationResponse> result = droneService.getLoadedMedications(droneId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(medResponse1));
        assertTrue(result.contains(medResponse2));
        verify(droneRepository).findById(droneId);
        verify(modelMapper).map(med1, MedicationResponse.class);
        verify(modelMapper).map(med2, MedicationResponse.class);
    }

    @Test
    void getBatteryLevelShouldReturnBatteryLevel() {
        String droneId = "drone1";
        Drone drone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 80, State.IDLE);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        int result = droneService.getBatteryLevel(droneId);

        assertEquals(80, result);
        verify(droneRepository).findById(droneId);
    }

    @Test
    void rechargeDroneShouldSetBatteryToFull() {
        String droneId = "drone1";
        Drone drone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 50, State.IDLE);
        Drone updatedDrone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 100, State.IDLE);
        DroneResponse response = new DroneResponse("drone1", "drone1", Model.LIGHTWEIGHT, 500, 100, State.IDLE, List.of());

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneRepository.save(any(Drone.class))).thenReturn(updatedDrone);
        when(modelMapper.map(updatedDrone, DroneResponse.class)).thenReturn(response);

        DroneResponse result = droneService.rechargeDrone(droneId);

        assertNotNull(result);
        assertEquals(response, result);
        verify(droneRepository).findById(droneId);
        verify(droneRepository).save(any(Drone.class));
        verify(modelMapper).map(updatedDrone, DroneResponse.class);
    }

    @Test
    void getAvailableDronesShouldReturnIdleDrones() {
        Drone drone1 = new Drone("drone1", Model.LIGHTWEIGHT, 500, 80, State.IDLE);
        Drone drone2 = new Drone("drone2", Model.MIDDLEWEIGHT, 600, 70, State.IDLE);
        DroneResponse response1 = new DroneResponse("drone1", "drone1", Model.LIGHTWEIGHT, 500, 80, State.IDLE, List.of());
        DroneResponse response2 = new DroneResponse("drone2", "drone2", Model.MIDDLEWEIGHT, 500, 70, State.IDLE, List.of());

        when(droneRepository.findAllByState(State.IDLE)).thenReturn(List.of(drone1, drone2));
        when(modelMapper.map(drone1, DroneResponse.class)).thenReturn(response1);
        when(modelMapper.map(drone2, DroneResponse.class)).thenReturn(response2);

        List<DroneResponse> result = droneService.getAvailableDrones();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(response1));
        assertTrue(result.contains(response2));
        verify(droneRepository).findAllByState(State.IDLE);
        verify(modelMapper).map(drone1, DroneResponse.class);
        verify(modelMapper).map(drone2, DroneResponse.class);
    }

    @Test
    void getBatteryLevelShouldThrowEntityNotFoundExceptionWhenDroneNotFound() {
        String droneId = "invalidId";

        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> droneService.getBatteryLevel(droneId));
        verify(droneRepository).findById(droneId);
    }

    @Test
    void rechargeDroneShouldThrowEntityNotFoundExceptionWhenDroneNotFound() {
        String droneId = "invalidId";

        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> droneService.rechargeDrone(droneId));
        verify(droneRepository).findById(droneId);
    }

    @Test
    void setStateShouldUpdateStateAndReturnUpdatedDrone() {
        String droneId = "drone1";
        State newState = State.LOADED;
        Drone drone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 80, State.IDLE);
        Drone updatedDrone = new Drone("drone1", Model.LIGHTWEIGHT, 500, 80, newState);
        DroneResponse response = new DroneResponse("drone1", "drone1", Model.LIGHTWEIGHT, 500, 80, newState, List.of());

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneRepository.save(any(Drone.class))).thenReturn(updatedDrone);
        when(modelMapper.map(updatedDrone, DroneResponse.class)).thenReturn(response);

        DroneResponse result = droneService.setState(droneId, newState);

        assertNotNull(result);
        assertEquals(response, result);
        verify(droneRepository).findById(droneId);
        verify(droneRepository).save(any(Drone.class));
        verify(modelMapper).map(updatedDrone, DroneResponse.class);
    }

    @Test
    void setStateShouldThrowEntityNotFoundException_WhenDroneNotFound() {
        String droneId = "invalidId";
        State newState = State.LOADED;

        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> droneService.setState(droneId, newState));
        verify(droneRepository).findById(droneId);
    }
}