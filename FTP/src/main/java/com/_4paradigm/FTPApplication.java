package com._4paradigm;


import com._4paradigm.util.ZipUtilApache;
import com._4paradigm.util.ftp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//开启定时任务
@EnableCaching
public class FTPApplication {
    public static void main(String[] args) {
        SpringApplication.run(FTPApplication.class);
    }
    @Bean
    public ftp getftp(){
        return new ftp();
    }
    @Bean
    public ZipUtilApache getZipUtilApache(){
        return new ZipUtilApache();
    }


}
