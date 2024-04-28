package edu.miu.cs489.InsightHub.dto.user;

public record UserAuthResponse(
        Integer userId,
        String token,
        String firstName,
        String lastName,
        String email
) {
}
