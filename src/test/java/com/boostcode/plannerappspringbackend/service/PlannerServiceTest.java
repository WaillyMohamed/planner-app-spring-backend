package com.boostcode.plannerappspringbackend.service;

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
        // Test the getOrCreatePLannerForDate()
        User user = new User();
        user.setPlanners(new ArrayList<Planner>());
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        userService.createUser(user);


        Planner planner = plannerService.getOrCreatePlannerForDate(user, LocalDate.now());
        assertNotNull(planner);
        assertEquals(planner.getDate(), LocalDate.now());
        long oldId = planner.getId();

        // Test if given different user that the existing planner isn't overwritten
        planner = plannerService.getOrCreatePlannerForDate(user, LocalDate.now());

        assertEquals(oldId, planner.getId());

    }
}
