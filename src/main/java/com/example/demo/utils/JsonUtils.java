package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JsonUtils {
    public static ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    static {
        mapper.disable(SerializationFeature.INDENT_OUTPUT);
    }
    public static String write(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void print(Object obj) {
        try {
            String x = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            System.out.println(x);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
