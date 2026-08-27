package entity.task;


import entity.ScheduledTaskInstance;
import entity.UserAvailability;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeadlineTaskTest {
    // Used for Unit Testing



    @Test
    public void testGenerateInstances(){

        DeadlineTask task = new DeadlineTask();

        // Since duration is set to 400 pacing should result in:
        // 150, 150, 100 = 400
        task.setDuration(400);
        task.setPacing(150);
        task.setIntensity(7);
        task.setPriority(5);
        task.setDeadline(LocalDate.now().plusDays(5)); // Deadline set to 5 days from now.

        UserAvailability availability = new UserAvailability(); // Not used in deadline task but a requirement from the abstract task class


        // Create a List of ScheduledTaskInstance
        List<ScheduledTaskInstance> instances = task.generateInstances(availability);

        // Assert
        assertEquals(3, instances.size(), "Should generate 3 sessions.");

        // Check durations
        assertEquals(150, instances.get(0).getDuration());
        assertEquals(150, instances.get(1).getDuration());
        assertEquals(100, instances.get(2).getDuration());

        // Check intensity + priority copied
        for (ScheduledTaskInstance instance : instances ){
            assertEquals(7 , instance.getIntensity());
            assertEquals(5 , instance.getPriority());
        }

        // Check task reference
        for (ScheduledTaskInstance instance : instances){
            assertEquals(task, instance.getTask());
        }

        // Check for the date/time not assigned yet
        for (ScheduledTaskInstance instance : instances){
            assertNull(instance.getDate());
            assertNull(instance.getStartTime());
            assertNull(instance.getEndTime());
        }

    }
}
