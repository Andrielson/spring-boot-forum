package io.github.andrielson.spring_boot_forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringBootForumApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootForumApiApplication.class, args);
    }

}
