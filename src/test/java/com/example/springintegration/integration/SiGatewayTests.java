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
        siGateway.process(Arrays.asList("msg1", "msg2", "msg3"));

        assertEquals("msg1-response", getPayload());
        assertEquals("msg1.1-response", getPayload());
        assertEquals("msg2-response", getPayload());
        assertEquals("msg3-response", getPayload());

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