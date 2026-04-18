package com.cpan228.apextech.config;

import com.cpan228.apextech.model.AppUser;
import com.cpan228.apextech.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadSampleUsers(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (appUserRepository.findByUsername("admin").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                appUserRepository.save(admin);
            }

            if (appUserRepository.findByUsername("staff").isEmpty()) {
                AppUser staff = new AppUser();
                staff.setUsername("staff");
                staff.setPassword(passwordEncoder.encode("staff123"));
                staff.setRole("STAFF");
                appUserRepository.save(staff);
            }

            if (appUserRepository.findByUsername("customer").isEmpty()) {
                AppUser customer = new AppUser();
                customer.setUsername("customer");
                customer.setPassword(passwordEncoder.encode("customer123"));
                customer.setRole("CUSTOMER");
                appUserRepository.save(customer);
            }
        };
    }
}