package com.example.springintegration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataService {

    private ObjectMapper mapper = new ObjectMapper();

    public String getData(String type) throws JsonProcessingException {

        Map<String, String> result = new HashMap<>();

        switch (type) {
            case "type-1":
                result.put("hasMore", "true");
                result.put("data", type + "-data");
                break;
            case "type-1.more":
                result.put("hasMore", "false");
                result.put("data", type + "-data");
                break;
            case "type-2":
                result.put("hasMore", "false");
                result.put("data", "");
                break;
            default:
                result.put("hasMore", "false");
                result.put("data", type + "-data");
                break;
        }

        return mapper.writeValueAsString(result);
    }
}
