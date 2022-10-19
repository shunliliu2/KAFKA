package com._4paradigm.util;

import com._4paradigm.mapper.CodeMapper;
import com._4paradigm.mapper.Server_wl;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.List;
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
public class Timingtask {
    @Value("${updatees}")
    private String updatees;
    private static CodeMapper codeMapper=new CodeMapper();
    private static Server_wl server_wl=new Server_wl();

    //@Scheduled(fixedRate=3600000)
    private void configureTasks() {
        List<String> selectservercode = codeMapper.selectservercode();
        try {
            for (String s : selectservercode) {
                codeMapper.updateseries0(s);
                server_wl.selectcode0(s);

                //getupdatees0(s);
            }
        } catch (Exception e) {
        }

    }

    public  void getupdatees0(String code){
        String json ="{\n" +
                "    \"doc\":{\n" +
                "        \"itemStatus\":\"0\"\n" +
                "    }\n" +
                "}";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(updatees+code);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        StringEntity stringEntity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(stringEntity);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public  void getupdatees1(String code){
        String json ="{\n" +
                "    \"doc\":{\n" +
                "        \"itemStatus\":\"1\"\n" +
                "    }\n" +
                "}";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(updatees+code);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        StringEntity stringEntity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(stringEntity);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
