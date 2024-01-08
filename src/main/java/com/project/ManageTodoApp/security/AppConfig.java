package com.project.ManageTodoApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf((csrf)->csrf.disable())
        .authorizeHttpRequests((authorizeRequest)->authorizeRequest.requestMatchers("/home").permitAll())
        .authorizeHttpRequests((authorizeRequest)->authorizeRequest.requestMatchers("/api/**").authenticated())
        .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    } 


}
