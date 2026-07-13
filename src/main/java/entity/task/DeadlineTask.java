/*
    Description: This class inherits from the Task abstract class.
    The purpose is to store tasks that must be completed before a deadline. The scheduler automatically splits these tasks into multiple sessions and distributes them across days before the deadline.
*/

package entity.task;

import entity.ScheduledTaskInstance;
import entity.UserAvailability;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deadline_task")
public class DeadlineTask extends Task {

    @NotNull(message = "Deadline is a mandatory input.")
    private LocalDate deadline;

    @Min(value = 1, message = "Pacing must be at least 1 minute per session.")
    private int pacing; // Variable that stores each session time leading up to the duration

    public LocalDate getDeadline() { return deadline;}

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getPacing() {
        return pacing;
    }

    public void setPacing(int pacing) {
        this.pacing = pacing;
    }

    /*
        Method that generates raw ScheduledTaskInstances into sessions based on pacing.
        The instances do not have dates or times yet, that will be decided by the SchedulingService class.
     */
    @Override
    public List<ScheduledTaskInstance> generateInstances(UserAvailability availability) {
        ArrayList<ScheduledTaskInstance> instances = new ArrayList<>();
        int remainder = getDuration(); // Each loop subtract the pacing from the remainder until the remainder is less than pacing
        // then set the last duration for that remainder.
        while (remainder > 0) {
            // Create an instance for each ScheduledTaskInstance and add it to the list
            ScheduledTaskInstance instance = new ScheduledTaskInstance();
            if(remainder > pacing){
                instance.setDuration(pacing);
                remainder -= pacing;
            }
            else{
                instance.setDuration(remainder);
                remainder -= remainder;
            }
            instance.setTask(this);
            instances.add(instance);
        }
        return instances;
    }

}
