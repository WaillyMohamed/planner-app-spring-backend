package com.boostcode.plannerappspringbackend.controller;

import com.boostcode.plannerappspringbackend.entity.task.DailyTask;
import com.boostcode.plannerappspringbackend.entity.task.DeadlineTask;
import com.boostcode.plannerappspringbackend.entity.task.ScheduledTask;
import com.boostcode.plannerappspringbackend.entity.task.Task;
import com.boostcode.plannerappspringbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable("id") long id){
        return taskService.getTaskById(id);
    }

    @PostMapping("/deadline")
    public Task createDeadlineTask(@RequestBody DeadlineTask task){
        return taskService.createDeadlineTask(task);
    }

    @PostMapping("/scheduled")
    public Task createScheduledTask(@RequestBody ScheduledTask task){
        return taskService.createScheduledTask(task);
    }

    @PostMapping("/daily")
    public Task createDailyTask(@RequestBody DailyTask task){
        return taskService.createDailyTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable("id") long id){
        taskService.deleteTaskById(id);
    }

}
