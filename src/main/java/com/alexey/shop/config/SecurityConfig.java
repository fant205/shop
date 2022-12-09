package com.alexey.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain mySecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()//csrf - запрещает работать со spring security через браузер, мы это выключаем
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



//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/v1/products/**").authenticated()
//                .antMatchers("/h2-console/**").permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // позволяет хранить пользователя только на время сессии,
//                  //т.е. сделали rest-запрос, прошли аутентификацию по токену, сложили в spring security context пользователя и инфу о нем из токена
                    // дальше наш сервис стал работать, в нем может быть логика по работе с пользователем, мы эту информацию берем из spring security context,
                    // выполняем все необходимое, отправляем ответ по rest, удаляем юзера из spring security context, конец.
        //          // т.е. храним юзера только в течении запроса - это и есть вся наша сессия
//                .and()
//                .headers().frameOptions().disable() //настройка по умолчанию для фронтэнда
//                .and()
//                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)); // если пользователь не авторизован
                    // то мы просто отправляем ошибку - пользователь не авторизован 401. т.е. эта настройка отключит форму логина, которую
                    // делает по умолчанию spring security




    }

}
