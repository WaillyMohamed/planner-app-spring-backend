package com.boostcode.plannerappspringbackend.service;
import com.boostcode.plannerappspringbackend.entity.task.DailyTask;
import com.boostcode.plannerappspringbackend.entity.task.DeadlineTask;
import com.boostcode.plannerappspringbackend.entity.task.ScheduledTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void createDeadlineTaskTest() {

        DeadlineTask task = new DeadlineTask();

        task.setTitle("Assignment");
        task.setDuration(300);
        task.setPriority(5);
        task.setIntensity(3);
        task.setDeadline(LocalDate.of(2026, 12, 20));
        task.setPacing(60);

        DeadlineTask savedTask =
                (DeadlineTask) taskService.createDeadlineTask(task);

        assertNotNull(savedTask.getId());

        assertEquals(task.getTitle(), savedTask.getTitle());
        assertEquals(task.getDuration(), savedTask.getDuration());
        assertEquals(task.getDeadline(), savedTask.getDeadline());
        assertEquals(task.getPacing(), savedTask.getPacing());
    }

    @Test
    public void createDailyTaskTest() {

        DailyTask task = new DailyTask();

        task.setTitle("Gym");
        task.setDuration(60);
        task.setPriority(2);
        task.setIntensity(2);
        task.setStartDate(LocalDate.of(2026, 1, 1));
        task.setEndDate(LocalDate.of(2026, 1, 31));

        DailyTask savedTask =
                (DailyTask) taskService.createDailyTask(task);

        assertNotNull(savedTask.getId());

        assertEquals(task.getTitle(), savedTask.getTitle());
        assertEquals(task.getStartDate(), savedTask.getStartDate());
        assertEquals(task.getEndDate(), savedTask.getEndDate());
    }

    @Test
    public void createScheduledTaskTest() {

        ScheduledTask task = new ScheduledTask();

        task.setTitle("Interview");
        task.setDuration(60);
        task.setPriority(4);
        task.setIntensity(1);
        task.setDate(LocalDate.of(2026, 5, 21));
        task.setStartTime(LocalTime.of(14, 0));
        task.setEndTime(LocalTime.of(15, 0));

        ScheduledTask savedTask =
                (ScheduledTask) taskService.createScheduledTask(task);

        assertNotNull(savedTask.getId());

        assertEquals(task.getTitle(), savedTask.getTitle());
        assertEquals(task.getDate(), savedTask.getDate());
        assertEquals(task.getStartTime(), savedTask.getStartTime());
        assertEquals(task.getEndTime(), savedTask.getEndTime());
    }
}
