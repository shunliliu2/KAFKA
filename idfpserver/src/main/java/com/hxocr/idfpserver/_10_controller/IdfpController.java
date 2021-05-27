package com.hxocr.idfpserver._10_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IdfpController {

    @LoadBalanced
    @Bean
    public RestTemplate idfpRestTemplate() {
        return new RestTemplate();
    }

    private final RestTemplate idfpRestTemplate;

    @Autowired
    public IdfpController(RestTemplate restTemplate) {
        this.idfpRestTemplate = restTemplate;
    }

    @RequestMapping(value = "/echo1/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        return idfpRestTemplate.getForObject("http://idfp-provider/echo/" + str, String.class);

    }
}
