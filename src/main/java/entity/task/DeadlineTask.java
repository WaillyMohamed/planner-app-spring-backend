/*
    Description: This class inherits from the Task abstract class.
    The purpose is to store tasks that must be completed before a deadline. The scheduler automatically splits these tasks into multiple sessions and distributes them across days before the deadline.
*/

package entity.task;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "deadline_task")
public class DeadlineTask extends Task {



    @NotNull(message = "Deadline is a mandatory input.")
    private LocalDate deadline;

    @Min(value = 1)
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


    //public abstract List<ScheduledTaskInstance> generateInstances(UserAvailability availability);


}
