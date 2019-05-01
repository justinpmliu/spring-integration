package com.example.springintegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.ArrayList;
import java.util.List;

public class MessageProcessor {

    @Autowired
    private MessageChannel bridgeCh;

    private final static String HEADER_PAYLOADS = "payloads";

    public Message process(Message<String> message) {
        MessageHeaders headers = message.getHeaders();
        String msgInHeader = (String)headers.get("msg");

        MessageBuilder builder = null;
        List<String> payloads = null;

        switch(msgInHeader) {
            case "msg1":
                payloads = new ArrayList<>();
                payloads.add("msg1-response");

                builder = MessageBuilder.withPayload("msg1.1").copyHeaders(headers);
                builder.setHeader(HEADER_PAYLOADS, payloads);

                bridgeCh.send(builder.build());

                builder = MessageBuilder.withPayload("");
                builder.setHeader("filter", "discard");
                break;

            case "msg1.1":
                payloads = (List)headers.get(HEADER_PAYLOADS);
                payloads.add("msg1.1-response");

                builder = MessageBuilder.withPayload(payloads);
                builder.setHeader("filter", "default");
                break;

            default:
                builder = MessageBuilder.withPayload(msgInHeader + "-response");
                builder.setHeader("filter", "default");
                break;
        }

        return builder.build();
    }
}
