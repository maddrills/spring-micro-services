package com.microservice.currency_conversion.controller;

import com.microservice.currency_conversion.entity.CurrencyConversion;
import com.microservice.currency_conversion.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/currency-conversion")
//http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion currencyConversion(@PathVariable String from, @PathVariable String to,
                                                 @PathVariable BigDecimal quantity){

        //        this will make Local API calls to another service
        // ggetforentity will get the object back from anAPI call
        //remember the returning value will get mapped appropriately as long as the data files match
        // uriVariables are used to pass values the path variables in the uri bellow

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to",to);

        ResponseEntity<CurrencyConversion> forEntity = new RestTemplate()
                .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversion.class,
                        uriVariables);

        //get the response
        CurrencyConversion currencyConversion = forEntity.getBody();

        return new CurrencyConversion(
                currencyConversion.getId(),
                from,
                to,
                quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment()
        );
    }


    //http://localhost:8100/currency-conversion/feign/from/USD/to/INR/quantity/10
    @GetMapping("/feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion currencyConversionFeign(@PathVariable String from, @PathVariable String to,
                                                 @PathVariable BigDecimal quantity){

        //        this will make Local API calls to another service
        // getforentity will get the object back from anAPI call
        //remember the returning value will get mapped appropriately as long as the data files match
        // uriVariables are used to pass values the path variables in the uri bellow

        //get the response
        CurrencyConversion currencyConversion = this.proxy.currencyConversion(from, to);

        return new CurrencyConversion(
                currencyConversion.getId(),
                from,
                to,
                quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment()
        );
    }
}
