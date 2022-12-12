package com.alexey.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true) // указывает что надо использовать аннотаций @Secured в rest-контроллере для определния полномочий
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// по умолчанию spring security не позволяет работать из браузера, и csrf это выполняет,
                // мы его отключает, что бы можно было работать
                .authorizeRequests() //авторизация всех запросов
                .antMatchers("/api/v1/products/**").authenticated() // по указанным пути и всем дочерним только
                // аутентифицированные пользователи
                .antMatchers("/h2-console/**").permitAll() // доступ к консоли встроенной БД H2 доступна всем без авторизации
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// позволяет хранить пользователя только на время сессии,
                //т.е. сделали rest-запрос, прошли аутентификацию по токену, сложили в spring security context пользователя и инфу о нем из токена
                // дальше наш сервис стал работать, в нем может быть логика по работе с пользователем, мы эту информацию берем из spring security context,
                // выполняем все необходимое, отправляем ответ по rest, удаляем юзера из spring security context, конец.
                // т.е. храним юзера только в течении запроса - это и есть вся наша сессия
                .and()
                .headers().frameOptions().disable()//настройка по умолчанию для фронтэнда
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));//если пользователь не авторизован
        // то мы просто отправляем ошибку - пользователь не авторизован 401. т.е. эта настройка отключит форму логина, которую
        // делает по умолчанию spring security

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider())
                .build();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
}
