package com.itech.networkPlan.controller;

import com.itech.networkPlan.model.Host;
import com.itech.networkPlan.model.Department;
import com.itech.networkPlan.model.Port;
import com.itech.networkPlan.model.Switch;
import com.itech.networkPlan.service.NetworkPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/network")
@CrossOrigin
public class NetworkPlanController {

    private final NetworkPlanService networkPlanService;

    public NetworkPlanController(NetworkPlanService networkPlanService) {
        this.networkPlanService = networkPlanService;
    }

    @GetMapping("networks")
    public ResponseEntity getNetworks() {
        List<Department> allDepartments;
        try {
            allDepartments = networkPlanService.getAllDepartments();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(allDepartments);
    }

    @GetMapping("ports")
    public ResponseEntity getPorts() {
        List<Port> allPorts;
        try {
            allPorts = networkPlanService.getAllPorts();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(allPorts);
    }

    @GetMapping("switches")
    public ResponseEntity getSwitches() {
        List<Switch> allSwitches;
        try {
            allSwitches = networkPlanService.getAllSwitches();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(allSwitches);
    }

    @GetMapping("hosts")
    public ResponseEntity getHosts() {
        List<Host> allHosts;
        try {
            allHosts = networkPlanService.getAllHosts();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(allHosts);
    }

    @GetMapping("hosts-by-department")
    public ResponseEntity getHostsByDepartment(@RequestParam("departmentId") Integer departmentId) {
        List<Host> hostsByNetwork;
        try {
            hostsByNetwork = networkPlanService.getHostsByDepartmentId(departmentId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(hostsByNetwork);
    }

    @GetMapping("hosts-by-department-name")
    public ResponseEntity getHostsByNetwork(@RequestParam("departmentName") String departmentName) {
        List<Host> hostsByDepartmentName;
        try {
            hostsByDepartmentName = networkPlanService.getHostsByDepartmentName(departmentName);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(hostsByDepartmentName);
    }

    @GetMapping("hosts-by-switch")
    public ResponseEntity getHostsBySwitch(@RequestParam("switchId") Integer switchId) {
        List<Host> hostsBySwitch;
        try {
            hostsBySwitch = networkPlanService.getHostsBySwitchId(switchId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(hostsBySwitch);
    }

    @GetMapping("hosts-by-vlan")
    public ResponseEntity getHostsBySwitch(@RequestParam("vlan") String vlan) {
        List<Host> hostsByVlan;
        try {
            hostsByVlan = networkPlanService.getHostsByVlan(vlan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(hostsByVlan);
    }

    @GetMapping("hosts-by-ip")
    public ResponseEntity getHostByIp(@RequestParam("ip") String ip) {
        Host hostByIp;
        try {
            hostByIp = networkPlanService.getHostByIp(ip);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(hostByIp);
    }

}


