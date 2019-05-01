package com.example.springintegration.integration;

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

    private static final String HEADER_PAYLOADS = "payloads";
    private static final String HEADER_FILTER = "filter";
    private static final String HEADER_FILTER_DEFAULT = "default";
    private static final String HEADER_FILTER_DISCARD = "discard";

    public Message process(Message<String> message) {
        MessageHeaders headers = message.getHeaders();
        String msgInHeader = (String)headers.get("msg");

        MessageBuilder builder;
        List<String> payloads;

        switch(msgInHeader) {
            case "msg1":
                payloads = new ArrayList<>();
                payloads.add("msg1-response");

                builder = MessageBuilder.withPayload("msg1.1").copyHeaders(headers);
                builder.setHeader(HEADER_PAYLOADS, payloads);

                bridgeCh.send(builder.build());

                builder = MessageBuilder.withPayload("");
                builder.setHeader(HEADER_FILTER, HEADER_FILTER_DISCARD);
                break;

            case "msg1.1":
                payloads = (List)headers.get(HEADER_PAYLOADS);
                payloads.add("msg1.1-response");

                builder = MessageBuilder.withPayload(payloads);
                builder.setHeader(HEADER_FILTER, HEADER_FILTER_DEFAULT);
                break;

            default:
                builder = MessageBuilder.withPayload(msgInHeader + "-response");
                builder.setHeader(HEADER_FILTER, HEADER_FILTER_DEFAULT);
                break;
        }

        return builder.build();
    }
}
