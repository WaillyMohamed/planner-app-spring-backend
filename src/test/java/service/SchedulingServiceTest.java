package service;

import entity.Planner;
import entity.ScheduledTaskInstance;
import entity.User;
import entity.task.DailyTask;
import entity.task.ScheduledTask;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SchedulingServiceTest {
    private PlannerService plannerService = new PlannerService();

    @Test
    public void placeScheduledTaskTest() {
        // Create a fake user
        User user = new User();
        user.setPlanners(new ArrayList<>());

        // Create a ScheduledTask
        ScheduledTask task = new ScheduledTask();
        task.setStartTime(LocalTime.of(2,3));
        task.setEndTime(LocalTime.of(4,5));
        task.setDate(LocalDate.of(2026, 8, 30));
        task.setDuration(122);
        task.setPriority(3);
        task.setIntensity(2);

        // Second task added to test sorting
        ScheduledTask task2 = new ScheduledTask();
        task2.setStartTime(LocalTime.of(1, 0));
        task2.setEndTime(LocalTime.of(2, 0));
        task2.setDate(task.getDate());
        task2.setDuration(60);
        task2.setPriority(1);
        task2.setIntensity(1);


        // Create a real PlannerService
        Planner planner = user.getPlanners().get(0);
        SchedulingService ss = new SchedulingService(plannerService);
        ScheduledTaskInstance tester = ss.placeScheduledTask(user, task);

        ScheduledTaskInstance tester2 = ss.placeScheduledTask(user, task2);

        assertEquals(tester2, planner.getInstances().get(0)); // earlier time
        assertEquals(tester, planner.getInstances().get(1));  // later time

        // Test properties
        assertEquals(tester.getStartTime(), task.getStartTime());
        assertEquals(tester.getEndTime(), task.getEndTime());
        assertEquals(tester.getDate(), task.getDate());
        assertEquals(tester.getDuration(), task.getDuration());
        assertEquals(tester.getPriority(), task.getPriority());
        assertEquals(tester.getIntensity(), task.getIntensity());

        // Test that the instance was added to the planner
        assertEquals(1, planner.getInstances().size());
        assertEquals(tester, planner.getInstances().get(0));

        // Test that the planner was set
        assertEquals(planner, tester.getPlanner());

        // Verify the instance references the correct task
        assertEquals(task, tester.getTask());
        assertEquals(task2, tester2.getTask());

    }



}
