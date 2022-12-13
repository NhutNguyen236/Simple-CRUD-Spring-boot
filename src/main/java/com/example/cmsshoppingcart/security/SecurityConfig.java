package com.example.cmsshoppingcart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin/pages").hasAnyRole("USER")
                .antMatchers("/").permitAll()
                    .and()
                        .formLogin()
                            .loginPage("/login")
                    .and()
                        .logout()
                            .logoutSuccessUrl("/login")
                    .and()
                        .exceptionHandling()
                            .accessDeniedPage("/login");
                // .antMatchers("/**").hasAnyRole("USER");

        // http
        //     .authorizeRequests()
        //         .antMatchers("/category/**").access("hasRole('ROLE_USER')")
        //         .antMatchers("/").access("permitAll");
    }
    
}