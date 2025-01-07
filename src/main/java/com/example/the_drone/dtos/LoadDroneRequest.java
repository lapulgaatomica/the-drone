package com.example.the_drone.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record LoadDroneRequest(
        @NotEmpty(message = "you can't load a drone with empty medication list") List<String> medicationIds
) {
}
