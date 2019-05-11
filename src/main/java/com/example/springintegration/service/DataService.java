package com.example.springintegration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataService {

    private ObjectMapper mapper = new ObjectMapper();

    private static final String HAS_MORE = "hasMore";
    private static final String DATA = "data";
    private static final String SUFFIX = "-data";

    public String getData(String type) throws JsonProcessingException, InterruptedException {

        Map<String, String> result = new HashMap<>();

        switch (type) {
            case "type-1":
            case "type-2.more":
                result.put(HAS_MORE, "false");
                result.put(DATA, type + SUFFIX);

                sleep(1);

                break;
            case "type-2":
                result.put(HAS_MORE, "true");
                result.put(DATA, type + SUFFIX);

                sleep(2);

                break;

            default:
                result.put(HAS_MORE, "false");
                result.put(DATA, type + SUFFIX);

                break;
        }

        return mapper.writeValueAsString(result);
    }

    private void sleep(long second) throws InterruptedException {
        Thread.sleep(second * 1000);
    }
}
