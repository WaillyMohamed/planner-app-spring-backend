package com.boostcode.plannerappspringbackend.repository;

import com.boostcode.plannerappspringbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
