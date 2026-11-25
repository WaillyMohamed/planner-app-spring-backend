package com.boostcode.plannerappspringbackend.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduledTaskInstanceResponseDTO(
        Long id,
        String taskTitle,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        int duration,
        int priority,
        int intensity
) {
}
