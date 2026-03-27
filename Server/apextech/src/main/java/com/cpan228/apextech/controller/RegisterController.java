package com.cpan228.apextech.controller;

import com.cpan228.apextech.model.RegistrationForm;
import com.cpan228.apextech.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final RegistrationService registrationService;

    public RegisterController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registrationForm") RegistrationForm form,
                           BindingResult bindingResult) {

        String normalizedUsername = form.getUsername() == null ? "" : form.getUsername().trim();
        form.setUsername(normalizedUsername);

        if (!normalizedUsername.isEmpty() && registrationService.usernameExists(normalizedUsername)) {
            bindingResult.rejectValue("username", "username.exists", "That username is already taken.");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        registrationService.register(form);
        return "redirect:/login?registered";
    }
}
