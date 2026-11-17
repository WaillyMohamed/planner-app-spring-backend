package com.boostcode.plannerappspringbackend.service;

import com.boostcode.plannerappspringbackend.entity.Planner;
import com.boostcode.plannerappspringbackend.entity.ScheduledTaskInstance;
import com.boostcode.plannerappspringbackend.entity.User;

import com.boostcode.plannerappspringbackend.entity.UserAvailability;
import com.boostcode.plannerappspringbackend.entity.task.DailyTask;
import com.boostcode.plannerappspringbackend.entity.task.DeadlineTask;
import com.boostcode.plannerappspringbackend.entity.task.ScheduledTask;
import org.springframework.stereotype.Service;
import com.boostcode.plannerappspringbackend.service.util.TimeWindow;

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

    public ScheduledTaskInstance placeScheduledTask(User user, ScheduledTask task) {
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

    public List<ScheduledTaskInstance> placeDailyTask(User user, DailyTask task) {
        List<ScheduledTaskInstance> createdInstances = new ArrayList<>();

        LocalDate current = task.getStartDate();
        LocalDate end = task.getEndDate();

        while (!current.isAfter(end)) { // Loops until we reach the day after the end date
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


    public List<ScheduledTaskInstance> placeDeadlineTask(User user, DeadlineTask task) {
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
                if (session_per_day == intensity) { // move backwards one day if we reached the max amount of sessions for that day.
                    // OR if the amount of sessions cannot fit in the current available time slots.
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
                        // We need to check the end of availability is before ^ this


                        session.setDate(current);
                        session.setStartTime(start);
                        session.setEndTime(end);

                        // Check conflicts
                        if (!conflictService.hasConflictWithPlanner(planner, session)) {
                            planner.addInstance(session);
                            placed.add(session);
                            session_per_day += 1; // Add 1 to the number of sessions per day if it is added.
                            placedSession = true;
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

    public boolean scheduleSessionForDay(ScheduledTaskInstance scheduledInstance, LocalDate date,
                                         UserAvailability availability, Planner planner) {
        /*

        This method computes free windows and tries to fit the session into one window.
        Then it assigns the start/end times and returns success/failure

        */
        boolean success = false;
        // 1. Compute the Window
        List<TimeWindow> freeWindows = computeFreeWindows(planner, availability);

        // 2. Try to fit the session in one of the windows
        for (TimeWindow window : freeWindows) {

            // Check if the window is bigger or equal to the duration of the scheduled task instance.
            if (window.durationInMinutes() >= scheduledInstance.getDuration()) {

                // Assign start/end times
                LocalTime start = window.getStart();
                LocalTime end = start.plusMinutes(scheduledInstance.getDuration());
                // We need to check the end of availability is before ^ this


                scheduledInstance.setDate(planner.getDate());
                scheduledInstance.setStartTime(start);
                scheduledInstance.setEndTime(end);

                // Check conflicts
                if (!conflictService.hasConflictWithPlanner(planner, scheduledInstance)) {
                    planner.addInstance(scheduledInstance); // Add the instance
                    success = true;
                    break;
                }
            }
        }
        return success;
    }

    public TimeWindow findPlacementWindow(List<TimeWindow> windows, int sessionDuration){
        // Loop through windows
        // Return the first window that fits the session duration
        // Return null if none fits

        for(TimeWindow window : windows){
            if (window.durationInMinutes() >= sessionDuration) {
                return window;
            }
        }
        return null;
    }

    public Planner placeSessionInPlanner(Planner planner, ScheduledTaskInstance instance){
        planner.addInstance(instance);

        return planner;
    }
}

