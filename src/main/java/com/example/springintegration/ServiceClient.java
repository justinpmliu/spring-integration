package com.example.springintegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ServiceClient {
    @Autowired
    private SiGateway siGateway;

    public void call() {
        siGateway.process(Arrays.asList("msg1", "msg2", "msg3"));
    }
}
