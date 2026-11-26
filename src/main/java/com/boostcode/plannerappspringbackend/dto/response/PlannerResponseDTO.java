package com.boostcode.plannerappspringbackend.dto.response;

import com.boostcode.plannerappspringbackend.entity.ScheduledTaskInstance;

import java.time.LocalDate;
import java.util.List;

public record PlannerResponseDTO(
        Long id,
        LocalDate date,
        List<ScheduledTaskInstanceResponseDTO> tasks
) {
}
