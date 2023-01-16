package com.itech.networkPlan.repository;

import com.itech.networkPlan.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortRepository extends JpaRepository<Port, PortId> {

    List<Port> findAllBySwitchId (Switch switchId);
}
