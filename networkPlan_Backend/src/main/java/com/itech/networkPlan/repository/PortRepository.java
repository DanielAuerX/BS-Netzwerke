package com.itech.networkPlan.repository;

import com.itech.networkPlan.model.Port;
import com.itech.networkPlan.model.PortId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortRepository extends JpaRepository<Port, PortId> {
}
