package com.itech.networkPlan.repository;

import com.itech.networkPlan.model.Network;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NetworkRepository extends JpaRepository<Network, Integer> {

    Optional<Network> findByName(String name);
}
