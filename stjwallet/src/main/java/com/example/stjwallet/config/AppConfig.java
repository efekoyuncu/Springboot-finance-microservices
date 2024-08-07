package com.example.stjwallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



@Configuration

public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {  // Renamed the bean to avoid conflict
        return new RestTemplate();
    }

}
