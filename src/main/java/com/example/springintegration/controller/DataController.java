package com.example.springintegration.controller;

import com.example.springintegration.service.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/data")
    public String getData(
            @RequestParam(value = "type") String type,
            @RequestHeader HttpHeaders headers)
            throws JsonProcessingException, InterruptedException {

        System.out.println("type: " + type + ", headers: " + headers);
        return dataService.getData(type);
    }
}
