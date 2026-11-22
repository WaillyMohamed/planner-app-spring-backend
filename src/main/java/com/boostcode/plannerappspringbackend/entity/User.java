package com.boostcode.plannerappspringbackend.entity;


import com.boostcode.plannerappspringbackend.entity.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

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
    private List<Task> tasks;

    // Add a list of Planners here
    @OneToMany(mappedBy = "user")
    private List<Planner> planners;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(UserAvailability availability) {
        this.availability = availability;
    }

    public List<Planner> getPlanners() {
        return planners;
    }

    public void setPlanners(ArrayList<Planner> planners) {
        this.planners = planners;
    }


}
