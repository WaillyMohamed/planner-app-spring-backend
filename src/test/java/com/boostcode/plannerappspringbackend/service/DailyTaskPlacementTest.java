package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.entity.ScheduledTaskInstance;
import com.boostcode.plannerappspringbackend.entity.User;
import com.boostcode.plannerappspringbackend.entity.task.DailyTask;
import com.boostcode.plannerappspringbackend.service.ConflictDetectionService;
import com.boostcode.plannerappspringbackend.service.PlannerService;
import com.boostcode.plannerappspringbackend.service.SchedulingService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DailyTaskPlacementTest {
    private PlannerService plannerService = new PlannerService();
    private ConflictDetectionService conflictService = new ConflictDetectionService();

    @Test
    public void placeDailyTaskTest() {
        // Create a fake user
        User user = new User();
        user.setPlanners(new ArrayList<>());

        // Create a DailyTask
        DailyTask task = new DailyTask();
        task.setStartDate(LocalDate.of(2026, 8, 1));
        task.setEndDate(LocalDate.of(2026, 8, 30));

        // Set the attributes
        task.setDuration(122);
        task.setPriority(3);
        task.setIntensity(2);

        SchedulingService ss = new SchedulingService(plannerService,  conflictService);
        List<ScheduledTaskInstance> tester = ss.placeDailyTask(user, task);

        assertEquals(30, tester.size());
        assertEquals(LocalDate.of(2026, 8, 1), tester.get(0).getDate());
        assertEquals(LocalDate.of(2026, 8, 2), tester.get(1).getDate());
        assertEquals(LocalDate.of(2026, 8, 3), tester.get(2).getDate());

        assertNull(tester.get(0).getStartTime());
        assertNull(tester.get(0).getEndTime());

        assertEquals(30, user.getPlanners().size());


    }

}
