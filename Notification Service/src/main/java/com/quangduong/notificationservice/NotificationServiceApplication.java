package com.quangduong.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Bean
    public Consumer<Data> handleMessage() {
        return data -> log.info("Title: " + data.title + " Message: " + data.message);
    }

    record Data(String title, String message) {}

//
//    // default create sendMessage-out-0
//    @Bean
//    public Supplier<String> sendMessage() {
//        return () -> "Hello from Spring Boot to RabbitMQ";
//    }
//
//    // default create convertToUppercase-in-0 & convertToUppercase-out-0
//    @Bean
//    public Function<String, String> convertToUpperCase() {
//        return (str) -> {
//            log.info("Receive message before uppercase: " + str);
//            return str.toUpperCase();
//        };
//    }
//
//    // default create receiveMessage-in-0
//    @Bean
//    public Consumer<String> receiveMessage() {
//        return message -> log.info("Receive message after uppercase: " + message);
//    }

}
