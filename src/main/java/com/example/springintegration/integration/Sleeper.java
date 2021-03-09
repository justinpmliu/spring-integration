package com.example.springintegration.integration;

import com.example.springintegration.aspect.Timed;
import com.example.springintegration.util.SleepUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Sleeper {

    @Timed
    public String process(String message) throws InterruptedException {
        log.info("[Sleeper] {} start sleeping", message);
        SleepUtils.randomSleep(5);
        log.info("[Sleeper] {} end sleeping", message);

        return message;
    }
}
