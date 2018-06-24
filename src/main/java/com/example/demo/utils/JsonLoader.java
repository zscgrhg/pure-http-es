package com.example.demo.utils;

import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonLoader {
    public static String from(String file) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[2].getClassName();
        try {
            Class<?> clazz = Class.forName(className);
            InputStream resourceAsStream = clazz.getResourceAsStream(file);
            if (resourceAsStream == null) {
                resourceAsStream = clazz.getClassLoader().getResourceAsStream(file);
            }
            return FileCopyUtils.copyToString(new InputStreamReader(resourceAsStream, "UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        String of = JsonLoader.from("data/hello.json");
        System.out.println(of);
    }
}
