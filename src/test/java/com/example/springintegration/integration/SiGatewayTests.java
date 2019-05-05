package com.example.springintegration.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ContextConfiguration("classpath:SiGatewayTests-context.xml")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SiGatewayTests {

    @Autowired
    private QueueChannel testChannel;

    @Autowired
    private SiGateway siGateway;

    @Test
    public void testProcess() {
        siGateway.process(Arrays.asList("type-1", "type-2", "type-3"));

        assertEquals("type-1-data", getPayload());
        assertEquals("type-1.more-data", getPayload());
        assertEquals("type-3-data", getPayload());

        assertNull(testChannel.receive(0));
    }

    private String getPayload(){
        String payload = null;
        Message msg = testChannel.receive();
        if (msg != null) {
            payload = msg.getPayload().toString();
        }
        return payload;
    }

}