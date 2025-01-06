package com.example.the_drone.dtos;

import com.example.the_drone.enums.Model;
import com.example.the_drone.enums.State;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterDroneRequest(
        @NotBlank(message = "serial number cannot be blank")
        @Size(max = 100, message = "serial number length cannot exceed 100")
        String serialNumber,
        @NotNull(message = "model cannot be null")
        Model model,
        @Min(value = 0, message = "weight limit value cannot go lower than 0")
        @Max(value = 500, message = "weight limit value cannot go higher than 500")
        int weightLimit,
        @Min(value = 0, message = "battery percentage cannot go lower than 0")
        @Max(value = 100, message = "battery percentage cannot go higher than 100")
        int batteryPercentage,
        @NotNull(message = "drone state cannot be null")
        State state
) {
}
