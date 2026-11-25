package com.boostcode.plannerappspringbackend.controller;

import com.boostcode.plannerappspringbackend.dto.response.PlannerResponseDTO;
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

    private PlannerResponseDTO createPlannerResponseDTO(Planner planner){
        return new PlannerResponseDTO(
                planner.getId(),
                planner.getDate()
        );
    }

    @GetMapping("/{userId}/{date}")
    public PlannerResponseDTO getPlanner(
            @PathVariable Long userId,
            @PathVariable LocalDate date) {

        User user = userService.getUserEntityById((userId));

        Planner planner =
                plannerService.getOrCreatePlannerForDate(
                        user,
                        date
                );

        return createPlannerResponseDTO(planner);
    }


}
