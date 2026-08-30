package service;

import entity.Planner;
import entity.ScheduledTaskInstance;
import entity.User;
import entity.task.ScheduledTask;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SchedulingService {
    // Business logic
    private final PlannerService plannerService;

    public SchedulingService(PlannerService plannerService) {
        this.plannerService = plannerService;
    }

    public ScheduledTaskInstance placeScheduledTask (User user, ScheduledTask task){
        // Get the date from the task
        LocalDate date = task.getDate();

        // Create or go get the corresponding planner
        Planner planner = plannerService.getOrCreatePlannerForDate(user, date);

        // Creat the instance
        ScheduledTaskInstance scheduledInstance = new ScheduledTaskInstance();

        // Copy the fields from the task
        scheduledInstance.setTask(task);
        scheduledInstance.setStartTime(task.getStartTime());
        scheduledInstance.setEndTime(task.getEndTime());
        scheduledInstance.setDuration(task.getDuration());
        scheduledInstance.setIntensity(task.getIntensity());
        scheduledInstance.setPriority(task.getPriority());

        // add the instance to the planner
        planner.addInstance(scheduledInstance);
        return scheduledInstance;
    }
}
