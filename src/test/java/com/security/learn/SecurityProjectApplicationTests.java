package com.security.learn;

import com.security.learn.entities.User;
import com.security.learn.repository.UserRepository;
import com.security.learn.security.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityProjectApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtHelper jwtHelper;

    @Test
    void contextLoads() {
    }

    @Test
    void testToken() {
        User user=userRepository.findByUsername("ram").get();
        String token=jwtHelper.generateToken(user);
        System.out.println(token);
        System.out.println("getting username from token");
        System.out.println(jwtHelper.getUsernameFromToken(token));
        System.out.println("Token expired");
        System.out.println(jwtHelper.isTokenExpired(token));
    }
}
