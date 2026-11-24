package com.boostcode.plannerappspringbackend.dto.response;

public record UserResponseDTO(
        Long id,
        String username,
        String email
) {
}
