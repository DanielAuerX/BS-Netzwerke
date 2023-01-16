package com.itech.networkPlan.repository;

import com.itech.networkPlan.model.Network;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkRepository extends JpaRepository<Network, Integer> {
}
