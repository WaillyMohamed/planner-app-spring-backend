package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.entity.Planner;
import com.boostcode.plannerappspringbackend.entity.ScheduledTaskInstance;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ConflictDetectionService {

    public boolean hasConflict(ScheduledTaskInstance a, ScheduledTaskInstance b) {

        if (a.getStartTime() == null || b.getStartTime() == null) {
            return false;
        }

        LocalTime aStart = a.getStartTime();
        LocalTime aEnd = a.getEndTime();
        LocalTime bStart = b.getStartTime();
        LocalTime bEnd = b.getEndTime();

        return aStart.isBefore(bEnd) && bStart.isBefore(aEnd);
    }

    public boolean hasConflictWithPlanner(Planner planner, ScheduledTaskInstance instance) {
        for (ScheduledTaskInstance other : planner.getInstances()) {
            if (hasConflict(instance, other)) {
                return true;
            }
        }
        return false;
    }
}

