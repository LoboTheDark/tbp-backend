package com.tbp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TbpBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbpBackendApplication.class, args);
    }
}
