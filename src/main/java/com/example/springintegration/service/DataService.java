package com.example.springintegration.service;

import com.example.springintegration.common.Constants;
import com.example.springintegration.util.SleepUtils;
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

        if ("type-2".equals(type)) {
            result.put(Constants.HEADER_HAS_MORE, "true");
        } else {
            result.put(Constants.HEADER_HAS_MORE, "false");
        }

        result.put(Constants.DATA, type + Constants.DATA_SUFFIX);
        SleepUtils.randomSleep(5);

        return objectMapper.writeValueAsString(result);
    }


}
