package com.itech.networkPlan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin
public class CalculatorController {

    record NewCalculateRequest(String ip, String mask, int amount) {
    }

    @PostMapping()
    public ResponseEntity<List<String>> doSomething(@RequestBody NewCalculateRequest request){
        String ipAddress = request.ip();
        String subnetMask = request.mask();
        int numSubnets = request.amount();
        List<String> subnets = new ArrayList<>();
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            InetAddress subnet = InetAddress.getByName(subnetMask);

            byte[] ip = inetAddress.getAddress();
            byte[] mask = subnet.getAddress();

            int maskBitLength = 0;
            for (byte b : mask) {
                int maskByte = b & 0xFF;
                while (maskByte != 0) {
                    maskBitLength += (maskByte & 1);
                    maskByte >>>= 1;
                }
            }

            byte[] subnetIP = new byte[ip.length];
            for (int i = 0; i < ip.length; i++) {
                subnetIP[i] = (byte) (ip[i] & mask[i]);
            }

            int startIndex = ip[ip.length-1] & 0xff;
            for (int i = 0; i < numSubnets; i++) {
                byte[] subnetAddr = new byte[ip.length];
                for (int j = 0; j < ip.length-1; j++) {
                    subnetAddr[j] = (byte) (subnetIP[j]);
                }
                subnetAddr[ip.length-1] = (byte) (startIndex + i);
                InetAddress subnetAddrInet = InetAddress.getByAddress(subnetAddr);
                subnets.add(subnetAddrInet.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.out.println("Invalid IP address or subnet mask.");
            return ResponseEntity.status(100).body(null);
        }
        return ResponseEntity.status(200).body(subnets);
    }

}
