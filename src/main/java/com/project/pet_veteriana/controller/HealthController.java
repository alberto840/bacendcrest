package com.project.pet_veteriana.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;


@RestController
public class HealthController {

    @GetMapping("/check-ip")
    public String checkIp() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return "IP: " + ip.getHostAddress();
        } catch (Exception e) {
            return "No se pudo obtener la IP: " + e.getMessage();
        }
    }
}
