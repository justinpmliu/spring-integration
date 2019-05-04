package com.example.springintegration.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.*;

public class MessageProcessor {

    @Autowired
    private MessageChannel bridgeCh;

    private static final String HEADER_PAYLOADS = "tmpPayloads";
    private static final String HEADER_FILTER = "filter";
    private static final String HEADER_FILTER_DEFAULT = "default";
    private static final String HEADER_FILTER_DISCARD = "discard";

    private static final List<String> RESEQUENCER_HEADERS = Arrays.asList(
            "correlationId", "sequenceSize", "sequenceNumber"
    );

    Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    public Message process(Message<String> message) {
        MessageHeaders headers = message.getHeaders();
        String msgInHeader = (String)headers.get("msg");

        MessageBuilder builder;
        List<String> tmpPayloads;

        switch(msgInHeader) {
            case "msg1":
                tmpPayloads = new ArrayList<>();
                tmpPayloads.add("msg1-response");

                builder = MessageBuilder.withPayload("msg1.1");

                this.copyHeaders(builder, headers);
                builder.setHeader(HEADER_PAYLOADS, tmpPayloads);

                bridgeCh.send(builder.build());

                builder = MessageBuilder.withPayload("");
                builder.setHeader(HEADER_FILTER, HEADER_FILTER_DISCARD);
                break;

            case "msg1.1":
                tmpPayloads = (List)headers.get(HEADER_PAYLOADS);
                tmpPayloads.add("msg1.1-response");

                builder = MessageBuilder.withPayload(tmpPayloads);
                builder.setHeader(HEADER_FILTER, HEADER_FILTER_DEFAULT);
                break;

            default:
                builder = MessageBuilder.withPayload(msgInHeader + "-response");
                builder.setHeader(HEADER_FILTER, HEADER_FILTER_DEFAULT);
                break;
        }

        logger.debug(msgInHeader);

        return builder.build();
    }

    private void copyHeaders(MessageBuilder builder, MessageHeaders headers) {
        Map<String, Object> headersToCopy = new HashMap<>();
        for (String headerName : RESEQUENCER_HEADERS) {
            headersToCopy.put(headerName, headers.get(headerName));
        }
        builder.copyHeaders(headersToCopy);
    }
}
