package com.boostcode.plannerappspringbackend.service.util;

import java.time.Duration;
import java.time.LocalTime;

public class TimeWindow {
    private LocalTime start;
    private LocalTime end;

    public TimeWindow(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() { return start; }
    public LocalTime getEnd() { return end; }

    public int durationInMinutes() {
        return (int) Duration.between(start, end).toMinutes();
    }
}

