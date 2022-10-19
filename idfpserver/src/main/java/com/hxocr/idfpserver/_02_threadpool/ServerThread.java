package com.hxocr.idfpserver._02_threadpool;

import com.hxocr.idfpserver._40_message.RequestInfo;

import org.apache.tomcat.jni.Time;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class ServerThread implements Runnable{

    private String UUID;
    RequestInfo requestInfo;

    public ServerThread(RequestInfo requestInfo) {
        this.UUID = requestInfo.getRequestID();
        this.requestInfo = requestInfo;
    }

    public RequestInfo getAcceptRequest() {
        return requestInfo;
    }


    @Override
    public void run() {

        try {
            System.out.println("多线程已经处理消息插入系统，订单号："+requestInfo.getRequestID());
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("消息处理完毕，订单号："+requestInfo.getRequestID());
    }
}