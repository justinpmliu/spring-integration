package com.example.springintegration.controller;

import com.example.springintegration.service.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/data")
    public String getData(@RequestParam(value = "type") String type)
            throws JsonProcessingException, InterruptedException {
        return dataService.getData(type);
    }
}
