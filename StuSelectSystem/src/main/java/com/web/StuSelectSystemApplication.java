package com.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.web.dao")
public class StuSelectSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StuSelectSystemApplication.class, args);
    }

}
