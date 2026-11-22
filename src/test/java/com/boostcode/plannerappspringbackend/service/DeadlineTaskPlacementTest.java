package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.entity.ScheduledTaskInstance;
import com.boostcode.plannerappspringbackend.entity.User;
import com.boostcode.plannerappspringbackend.entity.UserAvailability;
import com.boostcode.plannerappspringbackend.entity.task.DeadlineTask;
import com.boostcode.plannerappspringbackend.service.ConflictDetectionService;
import com.boostcode.plannerappspringbackend.service.PlannerService;
import com.boostcode.plannerappspringbackend.service.SchedulingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class DeadlineTaskPlacementTest {
    @Autowired
    private PlannerService plannerService;

    private ConflictDetectionService conflictService = new ConflictDetectionService();

    @Test
    public void DeadlineTaskPlacementTest(){
        // Create a fake user
        User user = new User();
        UserAvailability userAvailability = new UserAvailability();
        userAvailability.setStartTime(LocalTime.of(7, 45));
        userAvailability.setEndTime(LocalTime.of(23, 45));
        user.setAvailability(userAvailability);
        user.setPlanners(new ArrayList<>());


        // Create Deadline tasks
        DeadlineTask task1 = new DeadlineTask();
        task1.setDeadline(LocalDate.of(2026, 10, 17));
        task1.setDuration(322);
        task1.setPacing(60);
        task1.setIntensity(3);
        task1.setPriority(5);


        // Create Deadline tasks
        DeadlineTask task2 = new DeadlineTask();
        task2.setDeadline(LocalDate.of(2026, 12, 19));
        task2.setDuration(500);
        task2.setPacing(40);
        task2.setIntensity(5);
        task2.setPriority(5);

        LocalDate testDay = LocalDate.of(2026, 10, 17);
        LocalDate testDay2 = LocalDate.of(2026, 12, 19);
        int testDuration = 0;

        // Create a SchedulingService variable
        SchedulingService ss = new SchedulingService(plannerService, conflictService);
        List<ScheduledTaskInstance> test1 = ss.placeDeadlineTask(user, task1);

        for(ScheduledTaskInstance test : test1){
            if(testDuration >= (task1.getPacing() * task1.getIntensity())){
                testDay = testDay.minusDays(1);
                testDuration = 0;
            }
            // Test if each day is correct
            assertEquals(testDay, test.getDate());
            // Test start time and end time
            assertEquals(user.getAvailability().getStartTime().plusMinutes(testDuration), test.getStartTime());
            testDuration += test.getDuration(); // change testDuration to take into account the end time

            assertEquals(user.getAvailability().getStartTime().plusMinutes(testDuration), test.getEndTime());
        }
        // Set new availability for the second test
        userAvailability.setStartTime(LocalTime.of(11, 0));
        userAvailability.setEndTime(LocalTime.of(12, 15));
        user.setAvailability(userAvailability);
        testDuration = 0; // reset test duration

        List<ScheduledTaskInstance> test2 = ss.placeDeadlineTask(user, task2);

        long availableMinutes = Duration.between(
                user.getAvailability().getStartTime(),
                user.getAvailability().getEndTime()
        ).toMinutes(); // Stores the duration between

        for(ScheduledTaskInstance test : test2){
            if(testDuration + test.getDuration() > availableMinutes){
                testDay2 = testDay2.minusDays(1);
                testDuration = 0;
            }
            // Testing
            System.out.println("Expected day: " + testDay2);
            System.out.println("Actual day: " + test.getDate());
            System.out.println("Current usage: " + testDuration);
            System.out.println("Session duration: " + test.getDuration());

            // Test if each day is correct
            assertEquals(testDay2, test.getDate());
            // Test start time and end time
            assertEquals(user.getAvailability().getStartTime().plusMinutes(testDuration), test.getStartTime());
            testDuration += test.getDuration(); // change testDuration to take into account the end time
            assertEquals(user.getAvailability().getStartTime().plusMinutes(testDuration), test.getEndTime());


        }

    }
}
