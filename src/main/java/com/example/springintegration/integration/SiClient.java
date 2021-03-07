package com.example.springintegration.integration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SiClient {
    private final SiGateway siGateway;

    public SiClient(SiGateway siGateway) {
        this.siGateway = siGateway;
    }

//    @Scheduled(initialDelay = 90000L, fixedDelay = 90000L)
    public void invokeSiGateway() {
        this.sendMessage("group-1", 5);
        this.sendMessage("group-2", 3);
        this.sendMessage("group-3", 6);
    }

    private void sendMessage(String prefix, int num) {
        List<String> messages = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            messages.add(prefix + "-" + i);
        }
        siGateway.process(messages);
    }
}
