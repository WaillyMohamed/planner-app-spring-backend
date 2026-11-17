package com.boostcode.plannerappspringbackend.repository;

import com.boostcode.plannerappspringbackend.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
