package com.itech.networkPlan.service;

import com.itech.networkPlan.model.Host;
import com.itech.networkPlan.model.Department;
import com.itech.networkPlan.model.Port;
import com.itech.networkPlan.model.Switch;
import com.itech.networkPlan.repository.HostRepository;
import com.itech.networkPlan.repository.DepartmentRepository;
import com.itech.networkPlan.repository.PortRepository;
import com.itech.networkPlan.repository.SwitchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NetworkPlanService {
    private final DepartmentRepository departmentRepository;
    private final HostRepository hostRepository;
    private final PortRepository portRepository;
    private final SwitchRepository switchRepository;

    public NetworkPlanService(DepartmentRepository departmentRepository, HostRepository hostRepository, PortRepository portRepository, SwitchRepository switchRepository) {
        this.departmentRepository = departmentRepository;
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

    public List<Department> getAllDepartments() {
        List<Department> departmentList = departmentRepository.findAll();
        if (!departmentList.isEmpty()) {
            return departmentList;
        } else {
            throw new RuntimeException("No departments have been found! :(");
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
        Optional<Department> departmentById = departmentRepository.findById(networkId);
        if (departmentById.isPresent()) {
            return hostRepository.findAllByDepartment(departmentById.get());
        } else {
            throw new RuntimeException("No hosts have been found by this id! :(");
        }
    }

    public List<Host> getHostsByNetworkName(String networkName) {
        Optional<Department> departmentByName = departmentRepository.findByName(networkName);
        if (departmentByName.isPresent()) {
            return hostRepository.findAllByDepartment(departmentByName.get());
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

    public List<Host> getHostsByVlan(String vlan) {
        List<Port> portsByVlan = portRepository.findAllByVlan(vlan);
        if (portsByVlan.isEmpty()){
            throw new RuntimeException("No host has been found by this vlan! :(");
        }
        else {
            return portsByVlan.stream()
                    .map(Port::getHost)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }
}
