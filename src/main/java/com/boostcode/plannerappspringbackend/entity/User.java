package com.boostcode.plannerappspringbackend.entity;


import com.boostcode.plannerappspringbackend.entity.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username; // user display name

    @NotBlank
    private String password;

    @NotBlank
    private String email; // must be unique

    @OneToOne(cascade = CascadeType.ALL) // If the user is gone, the user availability is gone
    private UserAvailability availability;

    @OneToMany
    private ArrayList<Task> tasks;



    // Add a list of Planners here
    @OneToMany(mappedBy = "user")
    private ArrayList<Planner> planners;


    public UserAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(UserAvailability availability) {
        this.availability = availability;
    }

    public ArrayList<Planner> getPlanners() {
        return planners;
    }

    public void setPlanners(ArrayList<Planner> planners) {
        this.planners = planners;
    }


}
