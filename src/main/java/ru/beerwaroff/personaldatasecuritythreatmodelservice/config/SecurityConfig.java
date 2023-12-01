package ru.beerwaroff.personaldatasecuritythreatmodelservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static ru.beerwaroff.personaldatasecuritythreatmodelservice.constant.ControllerConstant.LOGIN_URL;
import static ru.beerwaroff.personaldatasecuritythreatmodelservice.constant.ControllerConstant.LOGOUT_URL;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers()
                    .authenticated()
                .anyRequest()
                    .permitAll()
                .and()
                    .httpBasic()
                .and()
                    .formLogin()
                    .loginPage(LOGIN_URL)
                .and()
                .logout()
                    .logoutUrl(LOGOUT_URL)
                .and()
                    .csrf().disable()
                .build();
    }
}
