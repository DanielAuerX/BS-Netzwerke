package com.itech.networkPlan.controller;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.itech.networkPlan.model.Host;
import com.itech.networkPlan.model.Network;
import com.itech.networkPlan.model.Port;
import com.itech.networkPlan.model.Switch;
import com.itech.networkPlan.repository.HostRepository;
import com.itech.networkPlan.repository.NetworkRepository;
import com.itech.networkPlan.repository.PortRepository;
import com.itech.networkPlan.repository.SwitchRepository;
import com.itech.networkPlan.service.NetworkPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        List<Network> allNetworks;
        try {
            allNetworks = networkPlanService.getAllNetworks();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(allNetworks);
    }

    @GetMapping("ports")
    public ResponseEntity getPorts(){
        List<Port> allPorts;
        try {
            allPorts = networkPlanService.getAllPorts();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(allPorts);
    }

    @GetMapping("switches")
    public ResponseEntity getSwitches(){
        List<Switch> allSwitches;
        try {
            allSwitches = networkPlanService.getAllSwitches();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(allSwitches);
    }

    @GetMapping("hosts")
    public ResponseEntity getHosts(){
        List<Host> allHosts;
        try {
            allHosts = networkPlanService.getAllHosts();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(allHosts);
    }

    @GetMapping("hosts/{networkId}")
    public ResponseEntity getHostsByNetwork(@PathVariable("networkId") Integer networkId){
        List<Host> hostsByNetwork;
        try {
            hostsByNetwork = networkPlanService.getHostsByNetwork(networkId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(hostsByNetwork);
    }
}
