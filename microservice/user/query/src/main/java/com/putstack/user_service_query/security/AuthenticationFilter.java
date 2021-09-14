package com.putstack.user_service_query.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.putstack.user_service_query.entity.UserSummary;
import com.putstack.user_service_query.query.LoginQuery;
import com.putstack.user_service_query.service.QueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

@Log4j2(topic = "AuthenticationConfig")
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private QueryService queryService;
    private Environment environment;

    @Autowired
    public AuthenticationFilter(AuthenticationManager authenticationManager, QueryService queryService, Environment environment) {
        super(authenticationManager);

        this.queryService = queryService;
        this.environment = environment;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
                
        log.info("Authentication {} ", request);

        try {
            LoginQuery loginQuery = new ObjectMapper().readValue(request.getInputStream(), LoginQuery.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginQuery.getEmail(), 
                    loginQuery.getPassword(), 
                    new ArrayList<>()
                )
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException, EntityNotFoundException {
        // super.successfulAuthentication(request, response, chain, authResult);

        String authEmail = ((User)authResult.getPrincipal()).getUsername();
        UserSummary authUser = queryService.findByEmail(authEmail);
        
        String access_token = Jwts.builder()
            .setSubject(authUser.getUserId())
            .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("signature.token.expiration"))))
            .signWith(SignatureAlgorithm.HS512, environment.getProperty("signature.token.secret"))
            .compact();

        response.addHeader("access_token", access_token);
        response.addHeader("userId", authUser.getUserId());

    }
}
