package com.quangduong.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

    private final StreamBridge streamBridge;

    @PostMapping("messages/{messsage}")
    public ResponseEntity<String> sendMessage(@PathVariable String message) {
        log.info("Sending value: " + message);
        streamBridge.send("messages-topic", message);
        return ResponseEntity.ok().build();
    }
}
