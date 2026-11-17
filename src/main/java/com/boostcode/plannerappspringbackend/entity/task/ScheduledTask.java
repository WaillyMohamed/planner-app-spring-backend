package com.boostcode.plannerappspringbackend.entity.task;

import com.boostcode.plannerappspringbackend.entity.ScheduledTaskInstance;
import com.boostcode.plannerappspringbackend.entity.UserAvailability;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Scheduled_Task")
public class ScheduledTask extends Task{
    // Represents a task with a fixed date and fixed time chosen by the user
    // Does not need the scheduler to decide when it happens


    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public List<ScheduledTaskInstance> generateInstances(UserAvailability availability) {
        ArrayList<ScheduledTaskInstance> instances = new ArrayList<>();
        ScheduledTaskInstance instance = new ScheduledTaskInstance();
        instance.setDuration( (int) Duration.between(startTime, endTime).toMinutes());
        instance.setTask(this);
        instance.setStartTime(this.startTime);
        instance.setEndTime(this.endTime);
        instance.setDate(this.date);
        instance.setIntensity(this.getIntensity());
        instance.setPriority(this.getPriority());
        instances.add(instance);
        return instances;
    }

    public boolean validateAgainstAvailability(UserAvailability availability) {
        return !(startTime.isBefore(availability.getStartTime()) || endTime.isAfter(availability.getEndTime()));
    }


}
