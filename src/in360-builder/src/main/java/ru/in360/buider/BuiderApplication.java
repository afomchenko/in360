package ru.in360.buider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"ru.in360.db", "ru.in360.util"})
public class BuiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuiderApplication.class, args);
    }
}
