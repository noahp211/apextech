package com.cpan228.apextech.service;

import com.cpan228.apextech.model.AppUser;
import com.cpan228.apextech.model.RegistrationForm;
import com.cpan228.apextech.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean usernameExists(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public void register(RegistrationForm form) {
        AppUser user = new AppUser();
        user.setUsername(form.getUsername().trim());
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        user.setRole("CUSTOMER");

        appUserRepository.save(user);
    }
}