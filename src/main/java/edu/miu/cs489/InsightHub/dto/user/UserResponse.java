package edu.miu.cs489.InsightHub.dto.user;

public record UserResponse(
        Integer userId,
        String firstName,
        String lastName,
        String email,
        String description
) {
}
