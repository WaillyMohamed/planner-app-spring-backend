package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.entity.task.DailyTask;
import com.boostcode.plannerappspringbackend.entity.task.DeadlineTask;
import com.boostcode.plannerappspringbackend.entity.task.ScheduledTask;
import com.boostcode.plannerappspringbackend.entity.task.Task;
import com.boostcode.plannerappspringbackend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public DeadlineTask createDeadlineTask(DeadlineTask deadlineTask) {
        return taskRepository.save(deadlineTask);
    }

    public ScheduledTask createScheduledTask(ScheduledTask scheduledTask) {
        return taskRepository.save(scheduledTask);
    }

    public DailyTask createDailyTask(DailyTask dailyTask) {
        return taskRepository.save(dailyTask);
    }

    public Task getTaskById(long id) {
        Optional<Task> task = taskRepository.findById(id);
        Task tempTask = task.get();
        if(task.isPresent()) {
            tempTask = task.get();
            return tempTask;
        }
        return tempTask;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void deleteTaskById(long id) {
        taskRepository.deleteById(id);
    }

}
