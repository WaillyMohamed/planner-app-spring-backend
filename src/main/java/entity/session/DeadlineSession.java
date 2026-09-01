package entity.session;

import entity.task.DeadlineTask;

import java.time.LocalDate;
import java.time.LocalTime;

public class DeadlineSession {

    private int duration;
    private int intensity;
    private DeadlineTask deadlineTask;

    private LocalDate date;
    private LocalTime time;
    private LocalTime endTime;

    // Getters and Setter

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DeadlineTask getDeadlineTask() {
        return deadlineTask;
    }

    public void setDeadlineTask(DeadlineTask deadlineTask) {
        this.deadlineTask = deadlineTask;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
