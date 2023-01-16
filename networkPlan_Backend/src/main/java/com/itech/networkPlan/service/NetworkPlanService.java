package com.itech.networkPlan.service;

import com.itech.networkPlan.model.Host;
import com.itech.networkPlan.model.Network;
import com.itech.networkPlan.model.Port;
import com.itech.networkPlan.model.Switch;
import com.itech.networkPlan.repository.HostRepository;
import com.itech.networkPlan.repository.NetworkRepository;
import com.itech.networkPlan.repository.PortRepository;
import com.itech.networkPlan.repository.SwitchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Host> getHostsByNetworkId(Integer networkId) {
        Optional<Network> networkById = networkRepository.findById(networkId);
        if (networkById.isPresent()) {
            return hostRepository.findAllByNetwork(networkById.get());
        } else {
            throw new RuntimeException("No hosts have been found by this id! :(");
        }
    }

    public List<Host> getHostsByNetworkName(String networkName) {
        Optional<Network> networkByName = networkRepository.findByName(networkName);
        if (networkByName.isPresent()) {
            return hostRepository.findAllByNetwork(networkByName.get());
        } else {
            throw new RuntimeException("No hosts have been found by this name! :(");
        }
    }

    public List<Host> getHostsBySwitchId(Integer switchId) {
        Optional<Switch> switchById = switchRepository.findById(switchId);
        if (switchById.isPresent()) {
            List<Port> portsBySwitchId = portRepository.findAllBySwitchId(switchById.get());
            List<Host> hostsBySwitch = portsBySwitchId.stream()
                    .map(Port::getHost)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (!hostsBySwitch.isEmpty()){
                return hostsBySwitch;
            }
            else {
                throw new RuntimeException("No host has been found by this switch id! :(");
            }
        } else {
            throw new RuntimeException("No switch has been found by this id! :(");
        }
    }
}
