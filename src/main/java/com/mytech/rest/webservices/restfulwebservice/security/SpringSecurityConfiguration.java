package com.mytech.rest.webservices.restfulwebservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // to create a spring bean
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        1) All requests should be authenticated
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );
//        2) if a request is not authenticated, a web page is shown
        http.httpBasic(Customizer.withDefaults());
//        3)CSRF -> post, PUT
        http.csrf().disable();

        return http.build();
    }
}
