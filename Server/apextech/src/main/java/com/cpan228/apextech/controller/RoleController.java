package com.cpan228.apextech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoleController {

    @GetMapping("/roles")
    public String roles(Model model) {
        return "Roles";
    }
}