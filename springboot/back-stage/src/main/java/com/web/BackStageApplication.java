package com.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackStageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackStageApplication.class, args);
        System.out.println("启动成功");
    }

}
