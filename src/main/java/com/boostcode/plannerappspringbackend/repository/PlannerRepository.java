package com.boostcode.plannerappspringbackend.repository;

import com.boostcode.plannerappspringbackend.entity.Planner;
import com.boostcode.plannerappspringbackend.entity.ScheduledTaskInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
}
