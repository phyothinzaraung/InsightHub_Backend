package edu.miu.cs489.InsightHub.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        String firstName,
        String lastName,
        @NotBlank(message = "email cannot be null or empty")
        String email,
        @NotBlank(message = "password cannot be null or empty")
        String password,
        String description
) {
}
