package org.example.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"org.example.restapi"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}