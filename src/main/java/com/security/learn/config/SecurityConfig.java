package com.security.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {
//    @Bean
//    public UserDetailsService userDetailsService() {
//        //database is not using here
//        UserDetails user1 = User.withDefaultPasswordEncoder().username("tejas").password("tejas").roles("ADMIN", "GUEST").build();
//        UserDetails user2 = User.withDefaultPasswordEncoder().username("aayu").password("aayu").roles("ADMIN").build();
//        //createing in memory userdetails manager
//        InMemoryUserDetailsManager inMemoryUserDetailsService = new InMemoryUserDetailsManager(user1, user2);
//        return inMemoryUserDetailsService;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request -> {
            request.requestMatchers("/api/route2").permitAll();
//            request.requestMatchers("/api/route2").hasRole("USER");
            request.anyRequest().authenticated();
        });
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
