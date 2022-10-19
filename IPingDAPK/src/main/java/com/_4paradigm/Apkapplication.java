package com._4paradigm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync//开启多线程
public class Apkapplication {
    public static void main(String[] args) {
        SpringApplication.run(Apkapplication.class);
    }
}
