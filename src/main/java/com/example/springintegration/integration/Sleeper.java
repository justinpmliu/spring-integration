package com.example.springintegration.integration;

import com.example.springintegration.util.SleepUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Sleeper {

    public String process(String message) throws InterruptedException {
        log.info(message + " start sleeping");
        SleepUtils.randomSleep(10);
        log.info(message + " end sleeping");

        return message;
    }
}
