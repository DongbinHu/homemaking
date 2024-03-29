package com.company.homemaking;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.company.homemaking.*.dao")
public class StartApplication {

    public static void main(String[] args) {

        SpringApplication.run(StartApplication.class, args);
    }
}
