package com.airtribe.ShareMyRecipe.config;

import com.airtribe.ShareMyRecipe.config.filter.JwtAuthorizationFilter;
import com.airtribe.ShareMyRecipe.util.JwtTokenUtil;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@EnableMethodSecurity
public class AuthConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain httpSecurityFilterChain(HttpSecurity httpSecurity, JwtTokenUtil jwtTokenUtil) throws Exception{
        httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(httpRequest ->{
                    httpRequest.anyRequest().permitAll();
                })
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager handleAuthentication(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }

}
