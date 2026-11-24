package com.boostcode.plannerappspringbackend.dto.response;

import java.time.LocalDate;

public record PlannerResponseDTO(
        Long id,
        LocalDate date
) {
}
