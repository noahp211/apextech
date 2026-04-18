package com.cpan228.apextech.controller;

import com.cpan228.apextech.model.AppUser;
import com.cpan228.apextech.repository.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminUserController {

    private final AppUserRepository appUserRepository;

    public AdminUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/admin/users")
    public String users(Model model) {
        model.addAttribute("users", appUserRepository.findAll());
        return "AdminUsers";
    }

    @PostMapping("/admin/users/{id}/role")
    public String updateRole(@PathVariable Long id, @RequestParam String role) {
        appUserRepository.findById(id).ifPresent(user -> {
            if (isAllowedRole(role)) {
                user.setRole(role);
                appUserRepository.save(user);
            }
        });

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, Authentication authentication) {
        AppUser targetUser = appUserRepository.findById(id).orElse(null);
        if (targetUser == null) {
            return "redirect:/admin/users";
        }

        AppUser currentUser = appUserRepository.findByUsername(authentication.getName()).orElse(null);
        if (currentUser != null && currentUser.getId().equals(targetUser.getId())) {
            return "redirect:/admin/users";
        }

        appUserRepository.deleteById(id);
        return "redirect:/admin/users";
    }

    private boolean isAllowedRole(String role) {
        return "ADMIN".equals(role) || "STAFF".equals(role) || "CUSTOMER".equals(role);
    }
}