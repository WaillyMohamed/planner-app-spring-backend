package com.boostcode.plannerappspringbackend.controller;

import com.boostcode.plannerappspringbackend.dto.response.PlannerResponseDTO;
import com.boostcode.plannerappspringbackend.dto.response.ScheduledTaskInstanceResponseDTO;
import com.boostcode.plannerappspringbackend.entity.Planner;
import com.boostcode.plannerappspringbackend.entity.ScheduledTaskInstance;
import com.boostcode.plannerappspringbackend.entity.User;
import com.boostcode.plannerappspringbackend.service.PlannerService;
import com.boostcode.plannerappspringbackend.service.SchedulingService;
import com.boostcode.plannerappspringbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/planner")
public class PlannerController {

    @Autowired
    private PlannerService plannerService;

    @Autowired
    private UserService userService;

    @Autowired
    private SchedulingService schedulingService;

    public PlannerController(PlannerService plannerService) {
        this.plannerService = plannerService;
        this.userService = userService;
    }

    private PlannerResponseDTO createPlannerResponseDTO(Planner planner){
        return new PlannerResponseDTO(
                planner.getId(),
                planner.getDate(),
                createScheduledTaskInstanceResponseDTOList(planner)
        );
    }

    private List<ScheduledTaskInstanceResponseDTO>
    createScheduledTaskInstanceResponseDTOList(
            Planner planner) {

        List<ScheduledTaskInstanceResponseDTO> dtoList =
                new ArrayList<>();

        for (ScheduledTaskInstance instance : planner.getInstances()) {

            dtoList.add(
                    new ScheduledTaskInstanceResponseDTO(
                            instance.getId(),
                            instance.getTask().getTitle(),
                            instance.getDate(),
                            instance.getStartTime(),
                            instance.getEndTime(),
                            instance.getDuration(),
                            instance.getPriority(),
                            instance.getIntensity()
                    )
            );
        }

        return dtoList;
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
