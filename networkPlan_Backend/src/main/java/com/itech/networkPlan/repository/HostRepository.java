package com.itech.networkPlan.repository;

import com.itech.networkPlan.model.Host;
import com.itech.networkPlan.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HostRepository extends JpaRepository<Host, String> {

    List<Host> findAllByDepartment (Department department);
    Optional<Host> findHostByIp (String ip);
}
