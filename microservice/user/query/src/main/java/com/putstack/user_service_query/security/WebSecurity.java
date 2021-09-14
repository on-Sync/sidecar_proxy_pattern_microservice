package com.putstack.user_service_query.security;

import com.putstack.user_service_query.service.QueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import lombok.extern.log4j.Log4j2;

@Configuration
@EnableWebSecurity
@Log4j2(topic = "AuthenticationConfig")
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private QueryService userService;
    private Environment environment;
    
    @Autowired
    public WebSecurity(QueryService userService, Environment environment) {
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("configure(HttpSecurity http)");
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
            .antMatchers("/**").permitAll().and().addFilter(getAuthenticationFilter());
            // .access("hasIpAddress('127.0.0.1')")
            // .and()
            // .addFilter(getAuthenticationFilter());
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = 
            new AuthenticationFilter(this.authenticationManager(), this.userService, this.environment);
        
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.debug("configure(AuthenticationManagerBuilder auth)");

        // super.configure(auth);
        // auth.inMemoryAuthentication()
        //     .passwordEncoder(new BCryptPasswordEncoder())
        //     .withUser("putstack")
        //     .password("{noop}test1234")
        //     .roles("USER");
        // auth.authenticationProvider(customIpAuthenticationProvider);
        auth.userDetailsService(userService)
            .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }
}
