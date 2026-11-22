package com.boostcode.plannerappspringbackend.repository;

import com.boostcode.plannerappspringbackend.entity.Planner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlannerRepositoryTest {

    @Test
    public void savePlannerTest() {
        Planner planner = new Planner();
    }

}
