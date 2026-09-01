package service;

import entity.Planner;
import entity.ScheduledTaskInstance;
import entity.User;
import entity.session.DeadlineSession;
import entity.task.DailyTask;
import entity.task.DeadlineTask;
import entity.task.ScheduledTask;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<ScheduledTaskInstance> placeDailyTask(User user, DailyTask task){
        List<ScheduledTaskInstance> createdInstances = new ArrayList<>();

        LocalDate current = task.getStartDate();
        LocalDate end = task.getEndDate();

        while (!current.isAfter(end)){ // Loops until we reach the day after the end date
            Planner planner = plannerService.getOrCreatePlannerForDate(user, current);

            // Create an instance and set its attributes
            ScheduledTaskInstance instance = new ScheduledTaskInstance();
            instance.setTask(task);
            instance.setDate(current);
            instance.setDuration(task.getDuration());
            instance.setIntensity(task.getIntensity());
            instance.setPriority(task.getPriority());

            // Set the time for the instance
            instance.setStartTime(null);
            instance.setEndTime(null);

            planner.addInstance(instance);
            createdInstances.add(instance);

            current = current.plusDays(1);
        }


        return createdInstances;


    }

    public List<DeadlineSession> generateSessions(DeadlineTask task){
        List<DeadlineSession> sessions = new ArrayList<>();
        // Each Deadline Session has its own duration decided by the intensity selected.

        int total = task.getDuration();
        int intensity = task.getIntensity();

        int sessionSize = intensity * 30; // Session time calculation

        int remaining = total;

        while (remaining > 0) {
            DeadlineSession deadlineSession = new DeadlineSession();
            deadlineSession.setDeadlineTask(task);
            deadlineSession.setIntensity(intensity);

            int size = Math.min(sessionSize, remaining); // If remaining time is less than the usual session size just use the remainder
            deadlineSession.setDuration(size);

            sessions.add(deadlineSession);
            remaining -= size;
        }
        return sessions;
    }

    public List<ScheduledTaskInstance> placeDeadlineTask(User user, DeadlineTask task){
        // Generate The Sessions
        List<DeadlineSession> sessions = generateSessions(task);

        // Schedule the Sessions

        // Place Sessions into planners

    }
}
