package com.example.dispatcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyManipulation {
    public void getCurrentObject(){
        RestTemplate restTemplate = new RestTemplate();
        Object forObject = restTemplate.getForObject("https://api.nbrb.by/exrates/currencies/145", Object.class);

        System.out.println(forObject);
    }
}
