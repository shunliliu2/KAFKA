package com.hxocr.idfpserver._10_controller;

import com.hxocr.idfpserver._20_feignservice.IdfpFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OcrController{

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/")
    public String index() {
        return "Hello：" ;
    }

    @RequestMapping("/info/{id}")
    public String info(@PathVariable("id") String id){
        return restTemplate.getForObject("http://idfp-provider/echo/" + id, String.class);
    }

    /*
    @Autowired
    IdfpFeignService idfpFeignService;

    @RequestMapping("/coupons")
    public String test(){
        String membercoupons = idfpFeignService.membercoupons();
        return membercoupons;
    }
    */
}