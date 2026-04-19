package com.cpan228.apextech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {

    @GetMapping("/api/system/health")
    public String healthCheck() {
        return "ApexTech system is running";
    }

    @GetMapping("/api/system/integration")
    public String integrationCheck() {
        return "Controllers, services, and repositories are connected";
    }
}