package com.example.springintegration.integration;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

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

        siGateway.process(Arrays.asList("type-1", "type-2"));

        assertEquals("type-1-data", getPayload());
        assertEquals("type-1.more-data", getPayload());
        assertEquals("type-2-data", getPayload());

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
        String json = "";

        String type = (String) message.getPayload();

        try {
            if (type.equals("type-1")) {
                json = this.readFileAsString("/mock/type-1-data.json");
            } else if (type.equals("type-1.more")) {
                json = this.readFileAsString("/mock/type-1.more-data.json");
            } else if (type.equals("type-2")) {
                json = this.readFileAsString("/mock/type-2-data.json");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    private String readFileAsString(String fileName) throws IOException {
        String data = "";

        try (InputStream in = this.getClass().getResourceAsStream(fileName)) {
            data = this.convert(in);
        }

        return data;
    }

    private String convert(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

}