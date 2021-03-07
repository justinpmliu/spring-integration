package com.example.springintegration.integration;

import com.example.springintegration.exception.business.SiValidationException;
import com.example.springintegration.util.SleepUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Logger {

    public String process(String message) throws SiValidationException, InterruptedException {

        if ("group-3-3-data".equals(message)) {
            throw new SiValidationException("Test exception");
        }

        log.info("[Logger] {} start sleeping", message);
        SleepUtils.randomSleep(5);
        log.info("[Logger] {} end sleeping", message);

        return message;
    }
}
