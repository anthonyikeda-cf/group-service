package com.example.composertestapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@EnableWebSecurity
@EnableResourceServer
@Configuration
public class OpenIDResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/*", "/group/v1").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/group/v1/*", "/group/v1").hasAnyAuthority("group-usage")
                .antMatchers(HttpMethod.POST, "/group/v1/*", "/group/v1").hasAuthority("group-mgmt")
                .antMatchers(HttpMethod.DELETE, "/group/v1/*").hasAuthority("group-mgmt");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("search");
    }
}
