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
import java.time.LocalTime;
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
    public void saveDailyTaskClassTest(){
        DailyTask dailyTask = new DailyTask();

        // Set the information for the daily
        dailyTask.setTitle("Yippeee Daily Task!");
        dailyTask.setPriority(2);
        dailyTask.setDuration(10);
        dailyTask.setIntensity(2);
        dailyTask.setStartDate(LocalDate.now());
        DailyTask savedDailyTask = taskRepository.save(dailyTask);

        assertNotNull(savedDailyTask.getId());

        Optional<Task> dailyTaskOp = taskRepository.findById(savedDailyTask.getId());
        assertTrue(dailyTaskOp.isPresent());

        // Test the inheritance mapping
        Task task = dailyTaskOp.get();
        assertTrue(task instanceof DailyTask);

        // Test Daily task attributes
        assertEquals(dailyTaskOp.get().getTitle(), dailyTask.getTitle());

    }

    @Test
    public void saveScheduledTaskClassTest(){
        ScheduledTask scheduledTask = new ScheduledTask();

        scheduledTask.setTitle("Yippeee Schedule Task!");
        scheduledTask.setPriority(2);
        scheduledTask.setDuration(15);
        scheduledTask.setIntensity(9);
        scheduledTask.setDate(LocalDate.now());
        scheduledTask.setStartTime(LocalTime.of(8,2,1));
        scheduledTask.setEndTime(LocalTime.of(9,2,1));

        ScheduledTask savedScheduledTask = taskRepository.save(scheduledTask);

        assertNotNull(savedScheduledTask.getId());

        Optional<Task> scheduledTaskOp = taskRepository.findById(savedScheduledTask.getId());
        assertTrue(scheduledTaskOp.isPresent());

        // Test the inheritance mapping
        Task task = scheduledTaskOp.get();
        assertTrue(task instanceof ScheduledTask);

        // Test Scheduled Task
        assertEquals(scheduledTaskOp.get().getTitle(), scheduledTask.getTitle());
    }


    @Test
    public void saveDeadlineTaskTest() {

        // Create different types of tasks and add save it into the repository
        DeadlineTask deadlineTask = new DeadlineTask();

        // Set the information for deadline task
        deadlineTask.setDeadline(LocalDate.of(2027, 12, 19));
        deadlineTask.setTitle("Yippeeee DEADLINE");
        deadlineTask.setPacing(50);
        deadlineTask.setDuration(500);
        deadlineTask.setPriority(3);

        // Save the tasks
        DeadlineTask savedTask = taskRepository.save(deadlineTask);

        // Test if the ID was generated
        assertNotNull(savedTask.getId());

        // Retrieve the task
        Optional<Task> deadlineTaskOp = taskRepository.findById(savedTask.getId());
        assertTrue(deadlineTaskOp.isPresent());

        // Testing inheritance mapping
        // Test the inheritance mapping
        Task task = deadlineTaskOp.get();
        assertTrue(task instanceof DeadlineTask);

        // Test the different attributes of the deadline task
        assertEquals(deadlineTaskOp.get().getTitle(), deadlineTask.getTitle());
        assertEquals(deadlineTaskOp.get().getDuration(), deadlineTask.getDuration());

    }
}
