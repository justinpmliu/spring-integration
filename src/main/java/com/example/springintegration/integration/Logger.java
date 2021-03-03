package com.example.springintegration.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Logger {

    public String process(String message) {
        log.info("[Logger] " + message);
        return message;
    }
}
