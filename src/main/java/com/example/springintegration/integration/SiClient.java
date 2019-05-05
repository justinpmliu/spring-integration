package com.example.springintegration.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SiClient {
    @Autowired
    private SiGateway siGateway;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void invokeSiGateway() {
        siGateway.process(Arrays.asList("type-1", "type-2", "type-3"));
    }
}
