package com.itech.networkPlan.service;

import com.itech.networkPlan.model.Host;
import com.itech.networkPlan.model.Network;
import com.itech.networkPlan.model.Port;
import com.itech.networkPlan.model.Switch;
import com.itech.networkPlan.repository.HostRepository;
import com.itech.networkPlan.repository.NetworkRepository;
import com.itech.networkPlan.repository.PortRepository;
import com.itech.networkPlan.repository.SwitchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NetworkPlanService {
    private final NetworkRepository networkRepository;
    private final HostRepository hostRepository;
    private final PortRepository portRepository;
    private final SwitchRepository switchRepository;

    public NetworkPlanService(NetworkRepository networkRepository, HostRepository hostRepository, PortRepository portRepository, SwitchRepository switchRepository) {
        this.networkRepository = networkRepository;
        this.hostRepository = hostRepository;
        this.portRepository = portRepository;
        this.switchRepository = switchRepository;
    }

    public List<Port>getAllPorts() {
        List<Port> portList = portRepository.findAll();
        if (!portList.isEmpty()) {
            return portList;
        } else {
            throw new RuntimeException("No ports have been found! :(");
        }
    }

    public List<Network> getAllNetworks() {
        List<Network> networkList = networkRepository.findAll();
        if (!networkList.isEmpty()) {
            return networkList;
        } else {
            throw new RuntimeException("No networks have been found! :(");
        }
    }

    public List<Switch> getAllSwitches() {
        List<Switch> switchList = switchRepository.findAll();
        if (!switchList.isEmpty()) {
            return switchList;
        } else {
            throw new RuntimeException("No switches have been found! :(");
        }
    }

    public List<Host> getAllHosts() {
        List<Host> hostList = hostRepository.findAll();
        if (!hostList.isEmpty()) {
            return hostList;
        } else {
            throw new RuntimeException("No hosts have been found! :(");
        }
    }

    public List<Host> getHostsByNetwork(Integer networkId) {
        Optional<Network> networkById = networkRepository.findById(networkId);
        if (networkById.isPresent()) {
            return hostRepository.findAllByNetwork(networkById.get());
        } else {
            throw new RuntimeException("No hosts have been found by this id! :(");
        }
    }
}
