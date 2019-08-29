package com.example.composertestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { ReactiveManagementWebSecurityAutoConfiguration.class })
public class GroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupApplication.class, args);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:4200")
//                .exposedHeaders("Location");
//            }
//        };
//    }
}
