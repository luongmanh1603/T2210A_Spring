package com.example.demo_spring;

import com.example.demo_spring.dao.ClassRoomDAO;
import com.example.demo_spring.enity.ClassRoom;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringApplication.class, args);
        System.out.println("Start");



    }


}



