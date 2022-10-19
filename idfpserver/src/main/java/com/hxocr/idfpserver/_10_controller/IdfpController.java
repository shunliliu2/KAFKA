package com.hxocr.idfpserver._10_controller;

import com.hxocr.idfpserver._02_threadpool.ServerThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;


@RestController
public class IdfpController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ServerThreadPoolManager serverThreadPoolManager;

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://idfp-provider/echo/" + str, String.class);
    }

    @RequestMapping(value = "/idfp/notify", method = RequestMethod.POST)
    public String notify(@RequestParam(value = "filename", required = true) String name,
                              @RequestParam(value = "filepath", required = true) String path) {
        System.out.println("notify files");

        if (serverThreadPoolManager.addRequest(UUID.randomUUID().toString(), name, path).equals("success")) {
            return name + "__" + path;
        } else {
            return "请等待并重新发送请求";
        }
    }


}
