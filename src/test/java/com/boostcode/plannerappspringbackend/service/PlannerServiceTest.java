package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.dto.request.UserRequestDTO;
import com.boostcode.plannerappspringbackend.dto.response.UserResponseDTO;
import com.boostcode.plannerappspringbackend.entity.Planner;
import com.boostcode.plannerappspringbackend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PlannerServiceTest {
    @Autowired
    private PlannerService plannerService;
    @Autowired
    private UserService userService;

    @Test
    public void savePlannerServiceTest() {

        // Create and persist a user
        UserRequestDTO userRequestDTO =
                new UserRequestDTO(
                        "username",
                        "password",
                        "email"
                );

        UserResponseDTO userResponseDTO =
                userService.createUser(userRequestDTO);

        // Retrieve the actual persisted User entity
        User user =
                userService.getUserById(userResponseDTO.id());

        // Create planner
        Planner planner =
                plannerService.getOrCreatePlannerForDate(
                        user,
                        LocalDate.now()
                );

        // Verify planner was created
        assertNotNull(planner);

        assertEquals(
                LocalDate.now(),
                planner.getDate()
        );

        Long oldId = planner.getId();

        // Verify calling it again returns the same planner
        Planner planner2 =
                plannerService.getOrCreatePlannerForDate(
                        user,
                        LocalDate.now()
                );

        assertEquals(
                oldId,
                planner2.getId()
        );
    }
}
