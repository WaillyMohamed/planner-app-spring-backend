package com.boostcode.plannerappspringbackend.repository;

import com.boostcode.plannerappspringbackend.entity.task.DailyTask;
import com.boostcode.plannerappspringbackend.entity.task.DeadlineTask;
import com.boostcode.plannerappspringbackend.entity.task.ScheduledTask;
import com.boostcode.plannerappspringbackend.entity.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
/*
    Test for the Task Class and its child classes
    Verifies TaskRepository can:
    - Save Deadline, Scheduled, and DailyTasks
    - Retrieve a user by ID
*/

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void saveTaskTest() {
        // Create different types of tasks and add save it into the repository
        DeadlineTask deadlineTask = new DeadlineTask();
        DailyTask dailyTask = new DailyTask();
        ScheduledTask scheduledTask = new ScheduledTask();

        // Set the information for deadline task
        deadlineTask.setDeadline(LocalDate.of(2027, 12, 19));
        deadlineTask.setTitle("Yippeeee DEADLINE");
        deadlineTask.setPacing(50);
        deadlineTask.setDuration(500);
        deadlineTask.setPriority(3);

        // Set the information for the daily
        dailyTask.setTitle("Yippeee Daily Task!");
        dailyTask.setPriority(2);
        dailyTask.setDuration(10);
        dailyTask.setIntensity(2);
        scheduledTask.setTitle("Yippeee Schedule Task!");
        scheduledTask.setPriority(2);
        scheduledTask.setDuration(15);
        scheduledTask.setIntensity(9);

        // Save the tasks
        DeadlineTask savedTask = taskRepository.save(deadlineTask);
        DailyTask savedDailyTask = taskRepository.save(dailyTask);
        ScheduledTask savedScheduledTask = taskRepository.save(scheduledTask);

        // Test if the ID was generated
        assertNotNull(savedTask.getId());
        assertNotNull(savedDailyTask.getId());
        assertNotNull(savedScheduledTask.getId());

        // Retrieve the task
        Optional<Task> deadlineTaskOp = taskRepository.findById(savedTask.getId());
        assertTrue(deadlineTaskOp.isPresent());

        Optional<Task> dailyTaskOp = taskRepository.findById(savedDailyTask.getId());
        assertTrue(dailyTaskOp.isPresent());

        Optional<Task> scheduledTaskOp = taskRepository.findById(savedScheduledTask.getId());
        assertTrue(scheduledTaskOp.isPresent());


        // Test the different attributes of the deadline task
        assertEquals(deadlineTaskOp.get().getTitle(), deadlineTask.getTitle());
        assertEquals(deadlineTaskOp.get().getDuration(), deadlineTask.getDuration());

        // Test Daily task attributes
        assertEquals(dailyTaskOp.get().getTitle(), dailyTask.getTitle());

        // Test Scheduled Task
        assertEquals(scheduledTaskOp.get().getTitle(), scheduledTask.getTitle());


    }
}
