package com.microservice.carency_exchange.service.controller;

import com.microservice.carency_exchange.service.DAO.CurrencyExchangeRepository;
import com.microservice.carency_exchange.service.entity.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currency-exchange")
public class currencyExchangeController {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private Environment environment;

    @GetMapping("/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to){

        //CurrencyExchange currencyExchange = new CurrencyExchange(1000L, "USD", "INR", BigDecimal.valueOf(50));

        CurrencyExchange currencyExchange = this.currencyExchangeRepository.findByFromAndTo(from, to);


        if(currencyExchange == null){
            throw new RuntimeException("Cannot find any data");
        }

        //get server property
        String portNumber = environment.getProperty("local.server.port");

        currencyExchange.setEnvironment(portNumber);

        //get data from repo
        this.currencyExchangeRepository.findAll();

        return currencyExchange;

    }
}
