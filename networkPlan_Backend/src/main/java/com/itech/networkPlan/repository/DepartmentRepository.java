package com.itech.networkPlan.repository;

import com.itech.networkPlan.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Optional<Department> findByName(String name);
}
