package com.fsnteam.fsnweb;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@MapperScan({"com.fsnteam.fsnweb.dao","com.fsnteam.fsnweb.mapper"})
@EnableSwagger2
public class FsnwebApplication {
    public static void main(String[] args) {
        SpringApplication.run(FsnwebApplication.class, args);
    }
}
