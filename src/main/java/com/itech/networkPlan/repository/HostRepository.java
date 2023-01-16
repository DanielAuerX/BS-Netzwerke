package com.itech.networkPlan.repository;

import com.itech.networkPlan.model.Host;
import com.itech.networkPlan.model.Network;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostRepository extends JpaRepository<Host, String> {

    List<Host> findAllByNetwork (Network network);
}
