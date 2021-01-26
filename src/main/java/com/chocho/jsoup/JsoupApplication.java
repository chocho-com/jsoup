package com.chocho.jsoup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chocho.jsoup.mapper")
public class JsoupApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsoupApplication.class, args);
    }

}
