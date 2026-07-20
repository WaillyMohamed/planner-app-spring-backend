package entity;

import entity.task.DailyTask;
import entity.task.DeadlineTask;
import entity.task.ScheduledTask;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;

@Entity
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


    private UserAvailability availability;

    private ArrayList<DeadlineTask> deadlines;
    private ArrayList<DailyTask> dailyTasks;
    private ArrayList<ScheduledTask>  scheduledTasks;
    private ArrayList<ScheduledTaskInstance> instances;
    // Add a list of Planners here
    private ArrayList<Planner> planners;

}
