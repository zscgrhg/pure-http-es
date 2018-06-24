package com.example.demo.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PingAndInfo {
    public static void info() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:9200/", String.class);
        System.out.println(forEntity.getBody());
    }

    public static void health() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:9200/_cluster/health", String.class);
        System.out.println(forEntity.getBody());
    }

    public static void main(String[] args) {
        health();
    }
}
