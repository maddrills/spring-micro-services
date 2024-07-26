package com.microservice.limits_service.controller;

import com.microservice.limits_service.bean.Limits;
import com.microservice.limits_service.configuration.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/limit-service")
public class LimitsController {

    @Autowired
    PropertiesConfig propertiesConfig;

    @GetMapping("/limits")
    public Limits retrieveLimits(){

        return new Limits(propertiesConfig.getMaximum(), propertiesConfig.getMinimum());

        //return new Limits(4,90);
    }
}