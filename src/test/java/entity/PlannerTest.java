package entity;

import com.boostcode.plannerappspringbackend.entity.Planner;
import com.boostcode.plannerappspringbackend.entity.ScheduledTaskInstance;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class PlannerTest {
    @Test
    public void addInstanceTest(){
        // Confirming the instance gets the correct planner
        // The instance gets the correct date and the instance list grows

        Planner planner = new Planner();
        planner.setDate(LocalDate.of(2015,4,12));

        ScheduledTaskInstance instance_1 = new ScheduledTaskInstance();
        instance_1.setStartTime(LocalTime.of(5,12)); // the later one

        ScheduledTaskInstance instance_2 = new ScheduledTaskInstance();
        instance_2.setStartTime(LocalTime.of(2,12)); // the early one

        ScheduledTaskInstance test_null = new ScheduledTaskInstance(); // tests with start time set to null
        test_null.setStartTime(null);

        planner.addInstance(instance_1);
        planner.addInstance(instance_2);
        planner.addInstance(test_null);

        // Testing that both are set to the right planner
        assertEquals(planner, instance_1.getPlanner());
        assertEquals(planner, instance_2.getPlanner());
        assertEquals(planner,test_null.getPlanner());


        // Testing that the instance is set to the right time
        assertEquals(LocalDate.of(2015,4,12), instance_1.getDate());
        assertEquals(LocalDate.of(2015,4,12), instance_2.getDate());

        // Check the instance list
        assertEquals(3, planner.getInstances().size());

        // check the sorting
        assertEquals(instance_2, planner.getInstances().get(0));
        assertEquals(instance_1, planner.getInstances().get(1));
        assertEquals(test_null, planner.getInstances().get(2));

        assertNotNull(planner.getInstances());


    }


}
