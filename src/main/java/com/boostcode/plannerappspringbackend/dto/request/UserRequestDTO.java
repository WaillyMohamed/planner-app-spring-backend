package com.boostcode.plannerappspringbackend.dto.request;

public record UserRequestDTO(
        String username,
        String password,
        String email
) {
}
