package com.itech.networkPlan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
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
        List<String> testSubnets = calculateSubnets(ipAddress, Integer.parseInt(subnetMask), numSubnets);
        /*
        List<String> subnets = new ArrayList<>();
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            InetAddress subnet = InetAddress.getByName(subnetMask);

            byte[] ip = inetAddress.getAddress();
            for(byte x: ip){
                System.out.println(x);
            }
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

         */
        return ResponseEntity.status(200).body(testSubnets);
    }

    public static List<String> calculateSubnets(String ip, int cidr, int amount){
        List<String> subnets = new ArrayList<>();
        System.out.println("CIDR Annotation:" + cidr);
        System.out.println("Extra reserved bits for hosts:" + amount);
        String subnetMask = getSubnetByCidr(cidr);
        char[] binaryIp = convertIpStringToCharBinaryArray(ip);
        System.out.print("IP in Binary: ");
        for(char x: binaryIp){
            System.out.print(x);
        }
        System.out.println();
        char[] binaryMask = convertIpStringToCharBinaryArray(subnetMask);
        System.out.print("Mask in Binary: ");
        for(char y: binaryMask){
            System.out.print(y);
        }
        System.out.println();
        char[] wildcard = getWildcard(binaryMask);
        String wildcardString = String.valueOf(wildcard);
        System.out.print("Wildcard: in binary: ");
        String[] octetsWildcard = new String[4];
        for(int i = 0; i < 4; i++) {
            octetsWildcard[i] = wildcardString.substring(8*i, 8*i + 8);
        }
        for(char z: wildcard){
            System.out.print(z);
        }
        System.out.println();
        wildcardString = binaryIpToString(octetsWildcard);
        System.out.println("Wildcard as IP:" + binaryIpToString(octetsWildcard));
        System.out.println();
        char[] result = getFirstHostAddress(binaryIp,binaryMask);
        String binary = String.valueOf(result);
        String[] octets = new String[4];
        for(int i = 0; i < 4; i++) {
            octets[i] = binary.substring(8*i, 8*i + 8);
        }
        String answer = binaryIpToString(octets);
        String broadcastAnswer = getBroadcastIp(answer,wildcardString);
        subnets.add(answer);
        subnets.add(broadcastAnswer);
        //System.out.println("NetzIP: " + answer + "/" + cidr);
        //System.out.println("BroadcastIP: " + broadcastAnswer);
        return subnets;
    }

    public static String getBroadcastIp(String ip, String wildcard){
        String[] ip1Octets = ip.split("\\.");
        String[] ip2Octets = wildcard.split("\\.");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(Integer.parseInt(ip1Octets[i]) + Integer.parseInt(ip2Octets[i]));
            if(i<3) sb.append(".");
        }

        String finalIp = sb.toString();
        return finalIp;
    }

    public static String binaryIpToString(String[] ip){
        String res = "";
        for(int i = 0; i < ip.length; i++) {
            int result = 0;
            if (ip[i].charAt(0) == '1') {
                result += 128;
            }
            if (ip[i].charAt(1) == '1') {
                result += 64;
            }
            if (ip[i].charAt(2) == '1') {
                result += 32;
            }
            if (ip[i].charAt(3) == '1') {
                result += 16;
            }
            if (ip[i].charAt(4) == '1') {
                result += 8;
            }
            if (ip[i].charAt(5) == '1') {
                result += 4;
            }
            if (ip[i].charAt(6) == '1') {
                result += 2;
            }
            if (ip[i].charAt(7) == '1') {
                result += 1;
            }
            res += result + ".";
        }
        res = res.substring(0, res.length()-1);
        return res;
    }

    public static char[] getFirstHostAddress(char[] ip, char[] mask){
        char[] result = new char[ip.length];
        for(int i = 0; i < ip.length; i++){
            if(ip[i] == '1' && mask[i] == '1'){
                result[i] = '1';
            }else{
                result[i] = '0';
            }
        }
        return result;
    }

    public static char[] getWildcard(char[] mask){
        char[] result = new char[mask.length];
        for(int i = 0; i < mask.length; i++){
            if(mask[i] == '0'){
                result[i] = '1';
            }else{
                result[i] = '0';
            }
        }
        return result;
    }

    public static char[] convertIpStringToCharBinaryArray(String arrayToConvert){
        String[] parts = arrayToConvert.split("\\.");
        String[] res = new String[4];
        for(int i = 0; i < parts.length; i++){
            res[i] = Integer.toBinaryString(Integer.parseInt(parts[i]));
        }
        int j = 0;
        while(j < 4){
            while(res[j].length() != 8){
                res[j] = "0" + res[j];
            }
            j++;
        }

        String joinedString = Arrays.toString(res).replaceAll("[\\[\\], ]", "");
        char[] charArray = joinedString.toCharArray();

        return charArray;
    }

    public static String getSubnetByCidr(int CidrAnnotation){
        return switch(CidrAnnotation){
            case 32 -> "255.255.255.255";
            case 31 -> "255.255.255.254";
            case 30 -> "255.255.255.252";
            case 29 -> "255.255.255.248";
            case 28 -> "255.255.255.240";
            case 27 -> "255.255.255.224";
            case 26 -> "255.255.255.192";
            case 25 -> "255.255.255.128";
            case 24 -> "255.255.255.0";
            case 23 -> "255.255.254.0";
            case 22 -> "255.255.252.0";

            default -> throw new IllegalStateException("Unexpected value: " + CidrAnnotation);
        };
    }

}
