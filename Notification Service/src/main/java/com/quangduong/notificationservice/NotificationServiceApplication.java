package com.quangduong.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

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
//            return str.toUpperCase(Locale.ROOT);
//        };
//    }
//
//    // default create receiveMessage-in-0
//    @Bean
//    public Consumer<String> receiveMessage() {
//        return message -> log.info("Receive message after uppercase: " + message);
//    }

}
