package com.hxocr.frontsystem;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(dataId = "hxocr-frontsystem", autoRefreshed = true)
public class FrontsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontsystemApplication.class, args);
	}

}
