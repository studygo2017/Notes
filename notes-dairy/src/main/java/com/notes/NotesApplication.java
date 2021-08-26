package com.notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tiny
 * @date 2021/8/26 17:34
 * @Description: 启动类
 */
@SpringBootApplication
public class NotesApplication {
    public static void main(String[] args) {

        SpringApplication.run(NotesApplication.class, args);
        System.out.println("==================================SpringBoot启动成功====================================");
    }
}
