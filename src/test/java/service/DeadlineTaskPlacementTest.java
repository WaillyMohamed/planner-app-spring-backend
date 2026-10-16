package service;

import entity.Planner;
import entity.ScheduledTaskInstance;
import entity.User;
import entity.UserAvailability;
import entity.task.DeadlineTask;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTaskPlacementTest {
    private PlannerService plannerService = new PlannerService();
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
        DeadlineTask task2 = new DeadlineTask();

        LocalDate testDay = LocalDate.of(2026, 10, 17);
        int testDuration = 0;

        // Create a SchedulingService variable
        SchedulingService ss = new SchedulingService(plannerService, conflictService);
        List<ScheduledTaskInstance> test1 = ss.placeDeadlineTask(user, task1);

        for(ScheduledTaskInstance test : test1){
            if(testDuration >= (task1.getPacing() * task1.getIntensity())){
                testDay = testDay.minusDays(1);
                testDuration = 0;
            }

            testDuration += test.getDuration();
            assertEquals(testDay, test.getDate());

            System.out.println(test.getDate());
            System.out.println(test.getStartTime());
            System.out.println(test.getEndTime());
        }

    }
}
