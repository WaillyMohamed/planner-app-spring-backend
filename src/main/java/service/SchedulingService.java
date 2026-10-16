package service;

import entity.Planner;
import entity.ScheduledTaskInstance;
import entity.User;

import entity.UserAvailability;
import entity.task.DailyTask;
import entity.task.DeadlineTask;
import entity.task.ScheduledTask;
import org.springframework.stereotype.Service;
import service.util.TimeWindow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SchedulingService {
    // Business logic
    private final PlannerService plannerService;
    private final ConflictDetectionService conflictService;

    public SchedulingService(PlannerService plannerService, ConflictDetectionService conflictService) {
        this.plannerService = plannerService;
        this.conflictService = conflictService;
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

    private List<TimeWindow> computeFreeWindows(Planner planner, UserAvailability availability) {

        LocalTime availStart = availability.getStartTime();
        LocalTime availEnd = availability.getEndTime();

        List<ScheduledTaskInstance> tasks = planner.getInstances();

        // Sort tasks by start time
        tasks.sort(Comparator.comparing(ScheduledTaskInstance::getStartTime,
                Comparator.nullsLast(Comparator.naturalOrder())));

        List<TimeWindow> windows = new ArrayList<>();

        LocalTime cursor = availStart;
        // Iterate through the task instances from the current planner
        for (ScheduledTaskInstance t : tasks) {
            if (t.getStartTime() == null) continue;

            if (cursor.isBefore(t.getStartTime())) { // If the cursor is before the task start time, make a time window ith cursor as beginning, start time as next
                windows.add(new TimeWindow(cursor, t.getStartTime()));
            }
            cursor = t.getEndTime();
        }

        // Last window
        if (cursor.isBefore(availEnd)) {
            windows.add(new TimeWindow(cursor, availEnd));
        }

        return windows;
    }


    public List<ScheduledTaskInstance> placeDeadlineTask(User user, DeadlineTask task){
        // Generate The Sessions
        List<ScheduledTaskInstance> createdInstances = task.generateInstances(user.getAvailability());
        List<ScheduledTaskInstance> placed = new ArrayList<>();

        // Schedule the Sessions
        int sessions = createdInstances.size();
        LocalDate current = task.getDeadline(); // Go from the deadline and work backwards per day
        int intensity = task.getIntensity(); // How many sessions per day are allowed?
        int session_per_day = 0;
        for (ScheduledTaskInstance session : createdInstances) {
            boolean placedSession = false;
            while (!placedSession) {

                if(session_per_day == intensity) { // move backwards one day if we reached the max amount of sessions for that day.
                    current = current.minusDays(1);
                    session_per_day = 0; // reset the number of sessions.

                }
                Planner planner = plannerService.getOrCreatePlannerForDate(user, current);

                // 1. Compute free windows for this day
                List<TimeWindow> freeWindows = computeFreeWindows(planner, user.getAvailability());

                // 2. Try to fit the session in one of the windows
                for (TimeWindow window : freeWindows) {

                    if (window.durationInMinutes() >= session.getDuration()) {

                        // Assign start/end times
                        LocalTime start = window.getStart();
                        LocalTime end = start.plusMinutes(session.getDuration());

                        session.setDate(current);
                        session.setStartTime(start);
                        session.setEndTime(end);

                        // Check conflicts
                        if (!conflictService.hasConflictWithPlanner(planner, session)) {
                            planner.addInstance(session);
                            placed.add(session);
                            session_per_day += 1; // Add 1 to the number of sessions per day if it is added.
                            placedSession = true;
                            System.out.println("Sessions per day incremented by 1: " + session_per_day);

                            break;
                        }
                    }
                }

                // 3. If not placed, move backwards one day
                if (!placedSession) {
                    current = current.minusDays(1);

                    // Optional: fail if we go too far back
                    if (current.isBefore(LocalDate.now())) {
                        throw new RuntimeException("Cannot schedule DeadlineTask before today.");
                    }
                }
            }
        }

        // Place Sessions into planners
        return placed;
    }
}

