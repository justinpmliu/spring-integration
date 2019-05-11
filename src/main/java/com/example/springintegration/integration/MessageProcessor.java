package com.example.springintegration.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;
import java.util.*;

public class MessageProcessor {

    @Autowired
    private MessageChannel bridgeCh;

    private static final String HEADER_PAYLOADS = "tmpPayloads";
    private static final String HEADER_DISCARD = "discard";
    private static final String HEADER_EMPTY = "empty";

    private static final List<String> RESEQUENCER_HEADERS = Arrays.asList(
            "correlationId", "sequenceSize", "sequenceNumber"
    );

    private Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    private ObjectMapper mapper = new ObjectMapper();

    public Message<List<String>> process(Message<String> message) throws IOException {
        MessageHeaders headers = message.getHeaders();
        Map<String, String> payload = mapper.readValue(message.getPayload(), HashMap.class);

        String type = (String)headers.get("type");
        String data = payload.get("data");
        String hasMore = payload.get("hasMore");

        logger.info("type: " + type);

        MessageBuilder builder;

        if (hasMore.equals("true")) {
            builder = MessageBuilder.withPayload(type + ".more");
            copyHeaders(builder, headers);
            saveDataToHeader(data, headers, builder);

            bridgeCh.send(builder.build());

            builder = MessageBuilder.withPayload(Collections.emptyList());
            builder.setHeader(HEADER_DISCARD, "true");

        } else {
            if (data.isEmpty()) {
                builder = MessageBuilder.withPayload(Collections.emptyList());
                builder.setHeader(HEADER_EMPTY, "true");
            } else {
                List<String> tmpData = (List)headers.get(HEADER_PAYLOADS);
                if (tmpData != null) {
                    tmpData.add(data);
                    builder = MessageBuilder.withPayload(tmpData);
                } else {
                    builder = MessageBuilder.withPayload(Collections.singletonList(data));
                }
                builder.setHeader(HEADER_EMPTY, "false");
            }

            builder.setHeader(HEADER_DISCARD, "false");
        }

        return builder.build();
    }

    private void copyHeaders(MessageBuilder builder, MessageHeaders headers) {
        Map<String, Object> headersToCopy = new HashMap<>();
        for (String headerName : RESEQUENCER_HEADERS) {
            headersToCopy.put(headerName, headers.get(headerName));
        }
        builder.copyHeaders(headersToCopy);
    }

    private void saveDataToHeader(String data, MessageHeaders headers, MessageBuilder builder) {
        List<String> tmpData = (List)headers.get(HEADER_PAYLOADS);
        if (tmpData == null) {
            tmpData = new ArrayList<>();
        }
        tmpData.add(data);
        builder.setHeader(HEADER_PAYLOADS, tmpData);
    }
}
