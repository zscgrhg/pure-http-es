package com.example.demo.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanDefs {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
