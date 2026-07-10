package task;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title cannot be empty.")
    @Size(max = 255, message = "Cannot exceed 255 characters.")
    private String title;

    @Size(max = 500, message = "Cannot exceed 500 characters.")
    private String description; // Optional

    @Min(1)
    @Positive
    private int duration; // Entered in minutes.

    @Min(1)
    @Positive
    private int priority; // On a scale from 1-10 how urgent is it. If not entered, lowest priority is the default.

    @Min(0)
    private int intensity;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    //public abstract List<ScheduledTaskInstance> generateInstances(UserAvailability availability);

}
