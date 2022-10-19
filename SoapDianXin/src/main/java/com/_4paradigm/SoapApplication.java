package com._4paradigm;


import com._4paradigm.entity.Standard_media_assets;
import com._4paradigm.util.MaterialData;
import com._4paradigm.util.ftp;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//开启定时任务
@EnableCaching
@EnableAsync
public class SoapApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoapApplication.class);
    }
    @Bean
    public ftp getftp(){
        return new ftp();
    }
    @Bean
    public MaterialData getMaterialData(){
        return new MaterialData();
    }
    @Bean
    public Standard_media_assets getStandard_media_assets(){
        return new Standard_media_assets();
    }
//    @Bean
//    public ChannelProgram getChannelProgram(){
//        return new ChannelProgram();
//    }
//



}
