package entity.task;

import entity.ScheduledTaskInstance;
import entity.UserAvailability;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Scheduled_Task")
public class ScheduledTask extends Task{

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
        instance.setDuration(this.getDuration());
        instance.setTask(this);
        instance.setStartTime(this.startTime);
        instance.setEndTime(this.endTime);
        instance.setDate(this.date);
        instances.add(instance);
        return instances;
    }
}
