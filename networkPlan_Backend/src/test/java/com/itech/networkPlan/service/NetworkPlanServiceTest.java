package com.itech.networkPlan.service;

import com.itech.networkPlan.model.*;
import com.itech.networkPlan.repository.DepartmentRepository;
import com.itech.networkPlan.repository.HostRepository;
import com.itech.networkPlan.repository.PortRepository;
import com.itech.networkPlan.repository.SwitchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NetworkPlanServiceTest {

    @Mock
    DepartmentRepository mockedDepartmentRepository;
    @Mock
    HostRepository mockedHostRepository;
    @Mock
    PortRepository mockedPortRepository;
    @Mock
    SwitchRepository mockedSwitchRepository;


    @Test
    void getAllPorts_shouldReturnListOfPorts() {
        Switch switch1 = new Switch(1, "Consulting");
        Department department1 = new Department(1, "Consulting", "Steindamm 81");
        Host host1 = new Host("00:00:5e:00:53:af", "PC-CO01", department1, "192.168.0.1", "PC");
        Port port1 = new Port(switch1, "FastEthernet0/1", "Access", host1, "VLAN30");
        Port port2 = new Port(switch1, "FastEthernet0/2", "Access", host1, "VLAN30");
        List<Port> samplePorts = List.of(port1, port2);
        when(mockedPortRepository.findAll()).thenReturn(samplePorts);
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);


        List<Port> allPorts = networkPlanService.getAllPorts();

        assertEquals(port1, allPorts.get(0));
        assertEquals(port2, allPorts.get(1));
    }

    @Test
    void getAllPorts_noPortsShouldThrowException() {
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        assertThrows(RuntimeException.class, networkPlanService::getAllPorts);
    }



    @Test
    void getAllDepartments_shouldReturnListOfDepartments() {
        Department department1 = new Department(1, "Consulting", "Steindamm 81");
        Department department2 = new Department(2, "IT", "Steindamm 81");
        List<Department> departments = List.of(department1, department2);
        when(mockedDepartmentRepository.findAll()).thenReturn(departments);
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        List<Department> allDepartments = networkPlanService.getAllDepartments();

        assertEquals(department1, allDepartments.get(0));
        assertEquals(department2, allDepartments.get(1));
    }

    @Test
    void getAllDepartments_noDepartmentsShouldThrowException() {
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        assertThrows(RuntimeException.class, networkPlanService::getAllDepartments);
    }

    @Test
    void getAllSwitches() {
    }

    @Test
    void getAllHosts() {
    }

    @Test
    void getHostsByDepartmentId() {
    }

    @Test
    void getHostsByDepartmentName() {
    }

    @Test
    void getHostsBySwitchId() {
    }

    @Test
    void getHostsByVlan() {
    }

    @Test
    void getHostByIp() {
    }
}