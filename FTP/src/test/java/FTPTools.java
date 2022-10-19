
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * 通过FTP上传文件
 *
 * @Author lvhaibao
 * @Date 2018/2/11 21:43
 */
public class FTPTools {



    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        int i = 300000;
        System.out.println("=====多线程开始=====");
        // 创建闭锁对象抓住所有线程，构造器指定倒数次数
        CountDownLatch countDownLatch = new CountDownLatch(300000);
        String sss="";
        while (i > 0) {
            int finalI = i;
            // 启用多线程
            int finalI1 = i;
            CompletableFuture.runAsync(() -> {
                System.out.println(finalI);
                System.out.println("i = " + finalI1);
                // 线程执行结束则countDown，倒数一次
                countDownLatch.countDown();
            });
            i--;
        }
        // 等待所有线程都countDown，倒数完毕，结束线程
        countDownLatch.await();

        System.out.println("=====多线程结束=====");
    }
}

