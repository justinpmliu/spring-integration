package com.example.springintegration.integration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SiClient {
    private final SiGateway siGateway;

    public SiClient(SiGateway siGateway) {
        this.siGateway = siGateway;
    }

    @Scheduled(cron = "0 0/3 * * * ?")
    public void invokeSiGateway() {
        siGateway.process(Arrays.asList("type-1", "type-2", "type-3"));
    }
}
