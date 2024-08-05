package com.security.learn;

import com.security.learn.entities.Role;
import com.security.learn.entities.User;
import com.security.learn.repository.RoleRepository;
import com.security.learn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class SecurityProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SecurityProjectApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

//        ROLE_ADMIN

//        ROLE_GUEST

        Role role2 = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        Role role1 = roleRepository.findByName("ROLE_GUEST").orElse(null);

        if (role2 == null) {
            role2 = new Role();
            role2.setName("ROLE_ADMIN");
            role2.setRoleId(UUID.randomUUID().toString());
            roleRepository.save(role2);
        }
        if (role1 == null) {
            role1 = new Role();
            role1.setName("ROLE_GUEST");
            role1.setRoleId(UUID.randomUUID().toString());
            roleRepository.save(role1);
        }


        User user = userRepository.findByUsername("ram").orElse(null);
        if (user == null) {
            user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setUsername("ram");
            user.setPassword(passwordEncoder.encode("ram123"));
            user.setRole(List.of(role1, role2));
            userRepository.save(user);
            System.out.println("user is created");
        }

        User user1 = userRepository.findByUsername("shyam").orElse(null);
        if (user1 == null) {
            user1 = new User();
            user1.setUserId(UUID.randomUUID().toString());
            user1.setUsername("shyam");
            user1.setPassword(passwordEncoder.encode("shyam"));
            user1.setRole(List.of(role1));
            userRepository.save(user1);
            System.out.println("user is created");
        }
    }
}
