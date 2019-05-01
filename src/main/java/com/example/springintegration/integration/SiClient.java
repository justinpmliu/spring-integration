package com.example.springintegration.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SiClient {
    @Autowired
    private SiGateway siGateway;

    public void invokeSiGateway() {
        siGateway.process(Arrays.asList("msg1", "msg2", "msg3"));
    }
}
