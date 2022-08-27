package com.example.SmartLibrarian.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/css/**", "/images/**", "/js/**").permitAll();
        http.formLogin().loginPage("/login").permitAll();
        http.formLogin().defaultSuccessUrl("/home", true);
        http.authorizeRequests().antMatchers("/register").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
        http.csrf().disable();
        http.exceptionHandling().accessDeniedPage("/errorPage");
    }
}