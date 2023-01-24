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
import java.util.Optional;

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
    void getAllSwitches_shouldReturnListOfSwitches() {
        Switch switch1 = new Switch(1, "Consulting");
        Switch switch2 = new Switch(2, "IT");
        List<Switch> switches = List.of(switch1, switch2);
        when(mockedSwitchRepository.findAll()).thenReturn(switches);
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        List<Switch> allSwitches = networkPlanService.getAllSwitches();

        assertEquals(switch1, allSwitches.get(0));
        assertEquals(switch2, allSwitches.get(1));
    }

    @Test
    void getAllSwitches_noSwitchesShouldThrowException() {
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        assertThrows(RuntimeException.class, networkPlanService::getAllSwitches);
    }

    @Test
    void getAllHosts_shouldReturnListOfHosts() {
        Department department1 = new Department(1, "Consulting", "Steindamm 81");
        Host host1 = new Host("00:00:5e:00:53:af", "PC-CO01", department1, "192.168.0.1", "PC");
        Host host2 = new Host("00:00:5d:00:54:ac", "PC-CO02", department1, "192.168.0.2", "PC");
        List<Host> hosts = List.of(host1, host2);
        when(mockedHostRepository.findAll()).thenReturn(hosts);
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        List<Host> allHosts = networkPlanService.getAllHosts();

        assertEquals(host1, allHosts.get(0));
        assertEquals(host2, allHosts.get(1));
    }

    @Test
    void getAllHosts_noHostsShouldThrowException() {
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        assertThrows(RuntimeException.class, networkPlanService::getAllHosts);
    }

    @Test
    void getHostsByDepartmentId_shouldReturnListOfHosts() {
        Department department1 = new Department(1, "Consulting", "Steindamm 81");
        Host host1 = new Host("00:00:5e:00:53:af", "PC-CO01", department1, "192.168.0.1", "PC");
        Host host2 = new Host("00:00:5d:00:54:ac", "PC-CO02", department1, "192.168.0.2", "PC");
        List<Host> hosts = List.of(host1, host2);
        when(mockedDepartmentRepository.findById(department1.getId())).thenReturn(Optional.of(department1)); // not being used, would throw exception though
        when(mockedHostRepository.findAllByDepartment(department1)).thenReturn(hosts);
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        List<Host> hostsByDepartmentId = networkPlanService.getHostsByDepartmentId(department1.getId());

        assertEquals(host1, hostsByDepartmentId.get(0));
        assertEquals(host2, hostsByDepartmentId.get(1));
    }

    @Test
    void getHostsByDepartmentId_wrongDepartmentIdShouldThrowException() {
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        assertThrows(RuntimeException.class, () -> {
            networkPlanService.getHostsByDepartmentId(999);
        });
    }

    @Test
    void getHostsByDepartmentId_noHostsShouldThrowException() {
        Department department1 = new Department(1, "Consulting", "Steindamm 81");
        when(mockedDepartmentRepository.findById(1)).thenReturn(Optional.of(department1));
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        assertThrows(RuntimeException.class, () -> {
            networkPlanService.getHostsByDepartmentId(department1.getId());
        });
    }

    @Test
    void getHostsByDepartmentName_shouldReturnListOfHosts() {
        Department department1 = new Department(1, "Consulting", "Steindamm 81");
        Host host1 = new Host("00:00:5e:00:53:af", "PC-CO01", department1, "192.168.0.1", "PC");
        Host host2 = new Host("00:00:5d:00:54:ac", "PC-CO02", department1, "192.168.0.2", "PC");
        List<Host> hosts = List.of(host1, host2);
        when(mockedDepartmentRepository.findByName(department1.getName())).thenReturn(Optional.of(department1)); // not being used, would throw exception though
        when(mockedHostRepository.findAllByDepartment(department1)).thenReturn(hosts);
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        List<Host> hostsByDepartmentId = networkPlanService.getHostsByDepartmentName(department1.getName());

        assertEquals(host1, hostsByDepartmentId.get(0));
        assertEquals(host2, hostsByDepartmentId.get(1));
    }

    @Test
    void getHostsByDepartmentName_wrongDepartmentNamedShouldThrowException() {
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        assertThrows(RuntimeException.class, () -> {
            networkPlanService.getHostsByDepartmentName("wrong Name");
        });
    }

    @Test
    void getHostsByDepartmentName_noHostsShouldThrowException() {
        Department department1 = new Department(1, "Consulting", "Steindamm 81");
        when(mockedDepartmentRepository.findByName(department1.getName())).thenReturn(Optional.of(department1));
        NetworkPlanService networkPlanService =
                new NetworkPlanService(mockedDepartmentRepository, mockedHostRepository,
                        mockedPortRepository, mockedSwitchRepository);

        assertThrows(RuntimeException.class, () -> {
            networkPlanService.getHostsByDepartmentId(1);
        });
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