package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import entity.task.Task;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Planner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private User user;

    @NotNull
    private ArrayList<ScheduledTaskInstance> instances;

    public void addInstance(@NotNull ScheduledTaskInstance instance) {
        this.instances.add(instance);
    }
    public void removeInstance(@NotNull ScheduledTaskInstance instance) {
        this.instances.remove(instance);
    }
    //
    public List<ScheduledTaskInstance> getInstanceSorted(@NotNull ArrayList<ScheduledTaskInstance> instances) {
        // We are sorting instances that fall within these cases
        // 1. Instances with a valid starttime
        // 2. Instances with null start time
        // 3. Instances with an equal start time
        return instances.stream().sorted((a,b) -> {
            // Case 1: both have a null start time, so keep original order
            if(a.getStartTime() == null && b.getStartTime() == null) return 0;

            // Case 2: a has no start time, so push a to the end
            if(a.getStartTime() == null) return 1;

            // Case 3: b has no start time, so push b to the end
            if(b.getStartTime() == null) return -1;

            // Case 4: Both have start times so sort normally
            int compareStartTime = a.getStartTime().compareTo(b.getStartTime());
            if(compareStartTime != 0) return compareStartTime;

            // Case 5: same Start time so sort by end time.
            return a.getEndTime().compareTo(b.getStartTime());
        }).toList();
    }

    public boolean hasConflict(@NotNull ScheduledTaskInstance newInstance) {
        boolean conflict = false;
        for (ScheduledTaskInstance existing: this.instances) {
            // If either instance has no time assigned yet, then we skip
            if(existing.getStartTime() == null || existing.getEndTime() == null ||
                    newInstance.getStartTime() == null || newInstance.getEndTime() == null) {continue;}

            // Otherwise we compare
            conflict = newInstance.getStartTime().isBefore(existing.getEndTime()) &&
                    newInstance.getEndTime().isAfter(existing.getStartTime());
            if(conflict){ return true;}
        }
        return false;
    }

}
