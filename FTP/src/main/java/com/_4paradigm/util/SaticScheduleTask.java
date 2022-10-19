package com._4paradigm.util;

import com._4paradigm.ftp.FtpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
public class SaticScheduleTask {
    @Autowired
    private FtpController ftpController;
    //3.添加定时任务
    //@Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=86400000)
    private void configureTasks() throws Exception {
        System.out.println("执行用户采集");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

        String execute = FlowEngineJob.execute();
    }
}
