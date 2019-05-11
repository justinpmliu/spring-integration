package com.example.springintegration.service;

import com.example.springintegration.common.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataService {

    private final ObjectMapper objectMapper;

    public DataService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getData(String type) throws JsonProcessingException, InterruptedException {

        Map<String, String> result = new HashMap<>();

        switch (type) {
            case "type-1":
            case "type-2" + Constants.MORE:
                result.put(Constants.HEADER_HAS_MORE, "false");
                result.put(Constants.DATA, type + Constants.DATA_SUFFIX);

                sleep(1);

                break;
            case "type-2":
                result.put(Constants.HEADER_HAS_MORE, "true");
                result.put(Constants.DATA, type + Constants.DATA_SUFFIX);

                sleep(2);

                break;

            default:
                result.put(Constants.HEADER_HAS_MORE, "false");
                result.put(Constants.DATA, type + Constants.DATA_SUFFIX);

                break;
        }

        return objectMapper.writeValueAsString(result);
    }

    private void sleep(long second) throws InterruptedException {
        Thread.sleep(second * 1000);
    }
}
