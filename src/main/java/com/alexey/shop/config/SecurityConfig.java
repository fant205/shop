package com.alexey.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain mySecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index.html").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/users.html").hasAnyRole("ADMIN", "SUPERADMIN")
                // дает указание, что тем url на которые не указать принудительно правила авторизации,
                // то они по умолчанию будут требовать авторизоваться пользователя
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .build();
    }

}
