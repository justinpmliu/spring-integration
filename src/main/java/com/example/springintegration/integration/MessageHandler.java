package com.example.springintegration.integration;

import com.example.springintegration.common.Constants;
import com.example.springintegration.exception.SiSystemException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class MessageHandler {

    private static final List<String> EMPTY_LIST = Collections.emptyList();

    private final TypeReference<Map<String, String>> typeRef = new TypeReference<Map<String, String>>() {};

    @Autowired
    private MessageChannel bridgeCh;

    private final ObjectMapper objectMapper;

    public MessageHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Message<List<String>> process(Message<String> message) throws IOException {
        MessageHeaders headers = message.getHeaders();

        Map<String, String> payload = objectMapper.readValue(message.getPayload(), typeRef);

        String type = headers.get(Constants.HEADER_TYPE, String.class);
        String data = payload.get(Constants.DATA);
        String hasMore = payload.get(Constants.HEADER_HAS_MORE);

        MessageBuilder<String> builder1;
        MessageBuilder<List<String>> builder2;

        if (hasMore.equals("true")) {
            builder1 = MessageBuilder.withPayload(type + Constants.MORE);
            builder1.copyHeaders(headers);
            builder1.setHeader(Constants.HEADER_TMP_PAYLOAD, this.appendTmpData(data, Constants.HEADER_TMP_PAYLOAD, headers));

            bridgeCh.send(builder1.build());

            builder2 = MessageBuilder.withPayload(EMPTY_LIST);
            builder2.setHeader(Constants.HEADER_HAS_MORE, "true");

            //test exception handling
//            throw new SiSystemException("Test NullPointerException");

        } else {
            if (data.isEmpty()) {
                builder2 = MessageBuilder.withPayload(EMPTY_LIST);
                builder2.setHeader(Constants.HEADER_IS_EMPTY, "true");
            } else {
                builder2 = MessageBuilder.withPayload(this.appendTmpData(data, Constants.HEADER_TMP_PAYLOAD, headers));
                builder2.setHeader(Constants.HEADER_IS_EMPTY, "false");
            }

            builder2.setHeader(Constants.HEADER_HAS_MORE, "false");
        }

        return builder2.build();
    }

    private List<String> appendTmpData(String data, String headerName, MessageHeaders headers) {
        @SuppressWarnings("unchecked") List<String> tmpData = headers.get(headerName, List.class);
        if (tmpData == null) {
            tmpData = new ArrayList<>();
        }
        tmpData.add(data);
        return tmpData;
    }
}
