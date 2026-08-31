package entity.task;

import entity.ScheduledTaskInstance;
import entity.UserAvailability;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Class represents a task that should appear every day in the user's planner
/*
    Examples being:
    Gym
    Meditation
    Daily Reading
    Walking
    Chores
    Journaling
*/

@Entity
@Table(name = "daily_task")
public class DailyTask extends Task{

    private LocalTime preferStart;
    private LocalTime preferEnd;

    private LocalDate startDate;
    private LocalDate endDate;

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public List<ScheduledTaskInstance> generateInstances(UserAvailability availability) {
        ArrayList<ScheduledTaskInstance> instances = new ArrayList<>();
        ScheduledTaskInstance instance = new ScheduledTaskInstance();
        instance.setDuration(this.getDuration());
        instance.setTask(this);
        instance.setIntensity(this.getIntensity());
        instance.setPriority(this.getPriority());
        instances.add(instance);
        return instances;

    }

    public LocalTime getPreferEnd() {
        return preferEnd;
    }

    public void setPreferEnd(LocalTime preferEnd) {
        this.preferEnd = preferEnd;
    }

    public LocalTime getPreferStart() {
        return preferStart;
    }

    public void setPreferStart(LocalTime preferStart) {
        this.preferStart = preferStart;
    }

    public boolean hasPreferredWindow() {
        return preferStart != null && preferEnd != null;
    }

    public boolean fitsPreferredWindow(LocalTime start, LocalTime end){
        // Lets the scheduler check if a candidate placement fits the user's preferred window
        return (!start.isBefore(preferStart) && !end.isAfter(preferEnd));
    }




}
