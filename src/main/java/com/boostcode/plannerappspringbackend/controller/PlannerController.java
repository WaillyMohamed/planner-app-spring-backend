package com.boostcode.plannerappspringbackend.controller;

import com.boostcode.plannerappspringbackend.entity.Planner;
import com.boostcode.plannerappspringbackend.entity.User;
import com.boostcode.plannerappspringbackend.service.PlannerService;
import com.boostcode.plannerappspringbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/planner")
public class PlannerController {

    @Autowired
    private PlannerService plannerService;

    @Autowired
    private UserService userService;

    public PlannerController(PlannerService plannerService) {
        this.plannerService = plannerService;
        this.userService = userService;
    }

    @GetMapping("/{userId}/{date}")
    public Planner getPlanner(@PathVariable Long userId, @PathVariable LocalDate date){
        User user = userService.getUserById(userId);
        return plannerService.getOrCreatePlannerForDate(user, date);
    }
}
