package com.hxocr.idfpserver._20_feignservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient("idfp-provider")
public interface IdfpFeignService {
    @RequestMapping("/coupon/list")
    public String membercoupons();
}
