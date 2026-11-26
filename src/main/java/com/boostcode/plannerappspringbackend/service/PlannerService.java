package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.dto.response.PlannerResponseDTO;
import com.boostcode.plannerappspringbackend.entity.Planner;
import com.boostcode.plannerappspringbackend.entity.User;
import com.boostcode.plannerappspringbackend.repository.PlannerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PlannerService {
    // Class is used to create or fetch planners for specific dates.
    PlannerRepository plannerRepository;

    public PlannerService(PlannerRepository plannerRepository) {
        this.plannerRepository = plannerRepository;
    }

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
        newPlanner.setUser(user);
        user.getPlanners().add(newPlanner);
        return plannerRepository.save(newPlanner);

    }

    public Planner updatePlannerForDate(Planner planner) {
        return plannerRepository.save(planner);
    }


}
