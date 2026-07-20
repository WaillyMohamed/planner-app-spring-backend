package entity.task;

import entity.ScheduledTaskInstance;
import entity.UserAvailability;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily_task")
public class DailyTask extends Task{

    @Override
    public List<ScheduledTaskInstance> generateInstances(UserAvailability availability) {
        ArrayList<ScheduledTaskInstance> instances = new ArrayList<>();
        ScheduledTaskInstance instance = new ScheduledTaskInstance();
        instance.setDuration(this.getDuration());
        instance.setTask(this);
        instances.add(instance);
        return instances;

    }
}
