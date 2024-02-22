package com.project.ManageTodoApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.project.ManageTodoApp.filters.JWTTokenGeneratorFilter;
import com.project.ManageTodoApp.filters.JWTTokenValidatorFilter;

@Configuration
public class AppConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .csrf((csrf) -> csrf.disable())
                                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest.requestMatchers("/home")
                                                .permitAll())
                                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest
                                                .requestMatchers(HttpMethod.POST, "/api/users/signup").permitAll())
                                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest
                                                .requestMatchers(HttpMethod.GET, "/api/users/signin").authenticated())
                                .authorizeHttpRequests(
                                                (authorizeRequest) -> authorizeRequest.requestMatchers("/users/data")
                                                                .authenticated())
                                .httpBasic(Customizer.withDefaults());
                return httpSecurity.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(10);
        }

}
