package edu.miu.cs489.InsightHub.dto.user;

public record UserRequestForUpdate(
        Integer userId,
        String firstName,
        String lastName,
        String description
) {
}
