package com.hxocr.idfpserver._10_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class IdfpController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://idfp-provider/echo/" + str, String.class);
    }

    @RequestMapping(value = "/idfp/notify", method = RequestMethod.POST)
    public String loginByPost(@RequestParam(value = "filename", required = true) String name,
                              @RequestParam(value = "filepath", required = true) String path) {
        System.out.println("notify files");
        return name + "__" + path;
    }


}
