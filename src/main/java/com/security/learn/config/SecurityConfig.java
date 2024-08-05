package com.security.learn.config;

import com.security.learn.security.JwtAuthenticationEntryPoint;
import com.security.learn.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableWebSecurity(debug = true)
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private JwtAuthenticationFilter filter;
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
//        httpSecurity.authorizeHttpRequests(request -> {
//            request.requestMatchers("/api/route2").permitAll();
//            request.anyRequest().authenticated();
//            request.anyRequest().permitAll();
//        });

        httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());


        httpSecurity.authorizeHttpRequests(request -> {
            request.requestMatchers("/api/route3", "/api/route4").hasRole("GUEST")
                    .requestMatchers("/api/route1", "/api/route2").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/auth/generate-token").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().permitAll();
        });

//        httpSecurity.formLogin(Customizer.withDefaults());
//        httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.exceptionHandling(ex->ex.authenticationEntryPoint(entryPoint));
        //session creation policy
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer ->httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //main
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
