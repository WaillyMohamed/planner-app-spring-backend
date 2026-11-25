package com.boostcode.plannerappspringbackend.controller;

import com.boostcode.plannerappspringbackend.entity.ScheduledTaskInstance;
import com.boostcode.plannerappspringbackend.entity.User;
import com.boostcode.plannerappspringbackend.entity.task.DailyTask;
import com.boostcode.plannerappspringbackend.entity.task.DeadlineTask;
import com.boostcode.plannerappspringbackend.entity.task.ScheduledTask;
import com.boostcode.plannerappspringbackend.entity.task.Task;
import com.boostcode.plannerappspringbackend.service.SchedulingService;
import com.boostcode.plannerappspringbackend.service.TaskService;
import com.boostcode.plannerappspringbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("schedule")
public class SchedulingController {

    @Autowired
    private UserService userService;

    @Autowired
    private SchedulingService schedulingService;

    @Autowired
    private TaskService taskService;

    public SchedulingController(UserService userService,
                                SchedulingService schedulingService,
                                TaskService taskService) {
        this.userService = userService;
        this.schedulingService = schedulingService;
        this.taskService = taskService;
    }

    // Exposing the scheduling engine through a REST endpoint.
    @PostMapping("/deadline/{userID}/{taskID}")
    public List<ScheduledTaskInstance> scheduleDeadlineTask(
            @PathVariable("userID") long userID,
            @PathVariable("taskID") long taskID) {

        // Receive the user and deadline task
        User user = userService.getUserEntityById(userID);
        DeadlineTask dt = taskService.getDeadlineTaskById(taskID);

        return schedulingService.placeDeadlineTask(user, dt);

    }

    // Exposing the scheduling engine through a REST endpoint.
    @PostMapping("/daily/{userID}/{taskID}")
    public List<ScheduledTaskInstance> scheduleDailyTask(
            @PathVariable("userID") long userID,
            @PathVariable("taskID") long taskID) {

        // Receive the user and deadline task
        User user = userService.getUserEntityById(userID);
        DailyTask dt = taskService.getDailyTaskById(taskID);

        return schedulingService.placeDailyTask(user, dt);

    }

    // Exposing the scheduling engine through a REST endpoint.
    @PostMapping("/appointment/{userID}/{taskID}")
    public ScheduledTaskInstance scheduleScheduledTask(
            @PathVariable("userID") long userID,
            @PathVariable("taskID") long taskID) {

        // Receive the user and deadline task
        User user = userService.getUserEntityById(userID);
        ScheduledTask dt = taskService.getScheduledTaskById(taskID);

        return schedulingService.placeScheduledTask(user, dt);

    }
    
}
