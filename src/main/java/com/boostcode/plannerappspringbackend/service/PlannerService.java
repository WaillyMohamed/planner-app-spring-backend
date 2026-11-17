package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.entity.Planner;
import com.boostcode.plannerappspringbackend.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PlannerService {
    // Class is used to create or fetch planners for specific dates.

    //  A method made for testing for the SchedulingServiceTest
    public Planner getOrCreatePlannerForDate(User user, LocalDate date) {
        // Search for an existing planner
        for (Planner planner: user.getPlanners()){
            if(planner.getDate().equals(date)){ // If a planner is found for that date return it
                return planner;
            }
        }

        // Create a new planner if none exist
        Planner newPlanner = new Planner();
        newPlanner.setDate(date);
        user.getPlanners().add(newPlanner);
        return newPlanner;

    }


}
