package com.example.demo.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HelloRunner implements CommandLineRunner {
    @Autowired
    RestTemplate restTemplate;
    @Override
    public void run(String... strings) throws Exception {
        System.out.println(restTemplate);
    }
}
