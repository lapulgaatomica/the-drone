package com.example.the_drone.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record RegisterMedicationRequest(
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "only letters, numbers, '-', '_' are allowed")
        String name,
        @Min(value = 0, message = "weight limit cannot go lower than 0")
        @Max(value = 500, message = "weight limit cannot go higher than 500")
        int weight,
        @Pattern(regexp = "^[A-Z0-9_]+$", message = "only upper case letters, underscore and numbers are allowed")
        String code,
        byte[] image
) {
}
