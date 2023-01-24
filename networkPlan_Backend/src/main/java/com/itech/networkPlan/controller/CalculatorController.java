package com.itech.networkPlan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin
public class CalculatorController {

    static HashMap<String, String> subnets = new HashMap<>();

    record NewCalculateRequest(String ip, String mask, int amount) {
    }

    @PostMapping()
    public ResponseEntity<HashMap<String, String>> calculateSubnetsAndReturnNewGeneratedSubnets(@RequestBody NewCalculateRequest request){
        subnets.clear();
        String ip = request.ip();
        int cidr = Integer.parseInt(request.mask());
        int amount = request.amount();
        HashMap<String, String> result = calculateSubnets(ip,cidr,amount);
        return ResponseEntity.status(200).body(result);
    }

    public static HashMap<String, String> calculateSubnets(String ip, int cidr, int amount){
        boolean debug = false;
        String subnetMask = getSubnetByCidr(cidr);
        char[] binaryIp = convertIpStringToCharBinaryArray(ip);
        char[] binaryMask = convertIpStringToCharBinaryArray(subnetMask);
        int newAmount = getAmountOfBitsBorrowed(amount);
        char[] newBinaryMask = getUpdatedBinaryIpIncludingSubnetAmount(binaryMask, newAmount);
        int[] x = getStepsToGoForEachSubnet(newBinaryMask);
        char[] wildcard = getWildcard(binaryMask);
        String wildcardString = String.valueOf(wildcard);
        String[] octetsWildcard = new String[4];
        for(int i = 0; i < 4; i++) {
            octetsWildcard[i] = wildcardString.substring(8*i, 8*i + 8);
        }
        wildcardString = binaryIpToString(octetsWildcard);
        char[] result = getFirstHostAddress(binaryIp,binaryMask);
        String binary = String.valueOf(result);
        String[] octets = new String[4];
        for(int i = 0; i < 4; i++) {
            octets[i] = binary.substring(8*i, 8*i + 8);
        }

        if(debug){
            System.out.println("START SUBNET CALCULATiON: ");
            System.out.println("CIDR Annotation: " + cidr);
            System.out.println("Extra reserved bits for hosts: " + amount);
            System.out.print("IP in Binary: ");
            for(char z: binaryIp){
                System.out.print(z);
            }
            System.out.println();
            System.out.print("Mask in Binary: ");
            for(char z: binaryMask){
                System.out.print(z);
            }
            System.out.println();
            System.out.print("Updated Mask in Binary: ");
            for(char z: newBinaryMask){
                System.out.print(z);
            }
            System.out.println();
            System.out.println("Steps to go: " + x[0]);
            System.out.println("In Octet: " + x[1]);
            System.out.println();
            System.out.println("TEST===============");
            System.out.print("Wildcard: in binary: ");
            for(char z: wildcard){
                System.out.print(z);
            }
            System.out.println();
            System.out.println("Wildcard as IP:" + binaryIpToString(octetsWildcard));
            System.out.println();
        }

        String firstNetworkIp = binaryIpToString(octets);
        String firstBroadcastIp = getBroadcastIp(firstNetworkIp,wildcardString);
        amount -= 1;
        System.out.print("Subnet No:1\nNetworkIP: " + firstNetworkIp);
        System.out.print(", BroadcastIP: " + firstBroadcastIp + "\n");
        subnets.put(firstNetworkIp, firstBroadcastIp);
        generateNewSubnets(firstNetworkIp, firstBroadcastIp, amount, x);
        return subnets;
    }

    public static void generateNewSubnets(String firstNetwork, String firstBroadcast, int amount, int[] x){
        int index = 2;
        while(amount > 0) {
            String newNetworkIp = incrementIp(firstNetwork, x[0], x[1]);
            String newBroadcastIp = incrementIp(firstBroadcast, x[0],x[1]);
            System.out.println("Subnet No:" + index);
            System.out.print("NetworkIP: " + newNetworkIp + ", BroadcastIP: " + newBroadcastIp);
            System.out.println();
            firstNetwork = newNetworkIp;
            firstBroadcast = newBroadcastIp;
            index++;
            amount--;
            subnets.put(firstNetwork, firstBroadcast);
        }
    }

    public static String getBroadcastIp(String ip, String wildcard){
        String[] ip1Octets = ip.split("\\.");
        String[] ip2Octets = wildcard.split("\\.");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(Integer.parseInt(ip1Octets[i]) + Integer.parseInt(ip2Octets[i]));
            if(i<3) sb.append(".");
        }

        return sb.toString();
    }

    public static String binaryIpToString(String[] ip){
        StringBuilder res = new StringBuilder();
        for (String s : ip) {
            int result = 0;
            if (s.charAt(0) == '1') {
                result += 128;
            }
            if (s.charAt(1) == '1') {
                result += 64;
            }
            if (s.charAt(2) == '1') {
                result += 32;
            }
            if (s.charAt(3) == '1') {
                result += 16;
            }
            if (s.charAt(4) == '1') {
                result += 8;
            }
            if (s.charAt(5) == '1') {
                result += 4;
            }
            if (s.charAt(6) == '1') {
                result += 2;
            }
            if (s.charAt(7) == '1') {
                result += 1;
            }
            res.append(result).append(".");
        }
        res = new StringBuilder(res.substring(0, res.length() - 1));
        return res.toString();
    }

    public static String incrementIp(String baseIp, int increment, int octet) {
        String[] octets = baseIp.split("\\.");
        int currentOctet = Integer.parseInt(octets[octet]);
        int newOctet = currentOctet + increment;
        if (newOctet > 255) {
            System.out.println("Error: Octet value exceeded maximum limit of 255");
            return baseIp;
        }
        octets[octet] = Integer.toString(newOctet);
        return String.join(".", octets);
    }

    public static int getAmountOfBitsBorrowed(int amount){
        return switch(amount){
            case 2 -> 1;
            case 4 -> 2;
            case 8 -> 3;
            case 16 -> 4;
            case 32 -> 5;
            case 64 -> 6;
            case 128 -> 7;
            case 256 -> 8;
            default -> 0;
        };
    }

    public static char[] getUpdatedBinaryIpIncludingSubnetAmount(char[] binaryMask, int amount){
        boolean checkForFirstZero = false;
        int index = 0;
        while(!checkForFirstZero){
            if(binaryMask[index] == '0'){
                checkForFirstZero = true;
            }
            index++;
        }
        index--;
        for(int i = 0; i < amount; i++){
            binaryMask[index+i] = '1';
        }
        return binaryMask;
    }

    public static int[] getStepsToGoForEachSubnet(char[] binaryMask) {
        int whatOctet = 3;
        int charAt = 0;
        for (int i = binaryMask.length - 1; i >= 0; i--) {
            if (binaryMask[i] == '0') {
                charAt++;
                if (charAt >= 8) {
                    charAt = 0;
                    whatOctet--;
                }
            } else {
                break;
            }
        }
        int[] x = new int[2];
        x[0] = getLastBitValue(charAt);
        x[1] = whatOctet;
        return x;
    }

    public static int getLastBitValue(int bitSpot){
        return switch(bitSpot){
            case 0 -> 1;
            case 1 -> 2;
            case 2 -> 4;
            case 3 -> 8;
            case 4 -> 16;
            case 5 -> 32;
            case 6 -> 64;
            case 7 -> 128;
            case 8 -> 256;
            default -> 0;
        };
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

        return joinedString.toCharArray();
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
            case 16 -> "255.255.0.0";

            default -> throw new IllegalStateException("Unexpected value: " + CidrAnnotation);
        };
    }

}
