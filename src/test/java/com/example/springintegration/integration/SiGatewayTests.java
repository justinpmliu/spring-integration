package com.example.springintegration.integration;

import com.example.springintegration.common.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.test.context.MockIntegrationContext;
import org.springframework.integration.test.context.SpringIntegrationTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.integration.test.mock.MockIntegration.mockMessageHandler;

@ContextConfiguration("classpath:SiGatewayTests-context.xml")
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringIntegrationTest
public class SiGatewayTests {

    @Autowired
    private QueueChannel testChannel;
    @Autowired
    private SiGateway siGateway;
    @Autowired
    private MockIntegrationContext mockIntegrationContext;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testProcess() {
        ArgumentCaptor<Message<?>> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);

        MessageHandler mockMessageHandler =
                mockMessageHandler(messageArgumentCaptor)
                        .handleNextAndReply(this::reply);

        this.mockIntegrationContext.substituteMessageHandlerFor("getHttp", mockMessageHandler);

        siGateway.process(Arrays.asList("type-1", "type-2", "type-3"));

        assertEquals("type-1-data", getPayload());
        assertEquals("type-2-data", getPayload());
        assertEquals("type-2.more-data", getPayload());
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

    private String reply(Message message) {
        Map<String, String> result = new HashMap<>();

        String type = (String) message.getPayload();

        switch (type) {
            case "type-1":
            case "type-2" + Constants.MORE:
                result.put(Constants.HEADER_HAS_MORE, "false");
                result.put(Constants.DATA, type + Constants.DATA_SUFFIX);

                break;
            case "type-2":
                result.put(Constants.HEADER_HAS_MORE, "true");
                result.put(Constants.DATA, type + Constants.DATA_SUFFIX);

                break;

            default:
                result.put(Constants.HEADER_HAS_MORE, "false");
                result.put(Constants.DATA, type + Constants.DATA_SUFFIX);

                break;
        }

        String json = null;
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

}