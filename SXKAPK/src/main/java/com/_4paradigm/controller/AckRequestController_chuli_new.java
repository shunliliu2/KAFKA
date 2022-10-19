package com._4paradigm.controller;

import com._4paradigm.entity.AckRequest;
import com._4paradigm.entity.KafkaBaoDi;
import com._4paradigm.entity.New_Standard_media_assets;
import com._4paradigm.entity.Request;
import com._4paradigm.log.CommonLogger;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/APK")
public class AckRequestController_chuli_new {
    @Value("${url}")
    private String url;
    @Value("${url2}")
    private String url2;
    private List<New_Standard_media_assets> huancun = new ArrayList<>();
    private Long data = 1632722361810L;

    @Value("${kaiguan}")
    private String kaiguan;

    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;
    @Value("${spring.kafka.consumer.topic}")
    String topic;

    @Value("${SceneId}")
    private String SceneId;
    @Value("${ItemSetId}")
    private String  ItemSetId;


    @RequestMapping(value = "/Sxk", method = RequestMethod.GET)
    public Request getAckRequest(AckRequest ackrequest, HttpServletRequest request222) {
        List<New_Standard_media_assets> list = new ArrayList<>();
        List<New_Standard_media_assets> list0 = new ArrayList<>();


        Request request = new Request();
        try {
            if (!ackrequest.getUserid().equals(null)) {
                HashMap<String, Object> stringStringHashMap = new HashMap<>();

                HashMap<String, Object> objectObjectHashMap1 = new HashMap<>();
                objectObjectHashMap1.put("userId", ackrequest.getUserid());


                Map<String, String> mapItemId2 = new HashMap<>();
                objectObjectHashMap1.put("debug", false);


                stringStringHashMap.put("request", objectObjectHashMap1);

                try {
                    //推荐接口返回数据
                    String porthttp = porthttp(stringStringHashMap);
                    Map<String, Object> map = (Map<String, Object>) JSONArray.parse(porthttp);

                    List<Map<String, Object>> listdata = (List) map.get("data");
                    request.setRequestId(SceneId + "@" + ((String) listdata.get(0).get("traceId")).split("-")[0]);


                    for (Map<String, Object> listmap : listdata) {


                        New_Standard_media_assets new_standard_media_assets = new New_Standard_media_assets();


                        new_standard_media_assets.setItemId((String) listmap.get("itemId"));
                        new_standard_media_assets.setDuration((String) listmap.get("duration"));
                        new_standard_media_assets.setRecallStrategyId((String) listmap.get("recallStrategyId"));
                        list.add(new_standard_media_assets);
                        request.setItems(list);


                        request.setItems(list.subList(0, 300 < list.size() ? 300 : list.size()));


                    }
                } catch (Exception e) {
                    CommonLogger.error("用户id不能为空");
                    e.printStackTrace();
                }
                List<New_Standard_media_assets> list2 = new ArrayList<>();
                try {
                    if (request.getItems().size() < 0) {
                        CommonLogger.error("这次请求返回的结果是走的兜底");
                        String getes = getes();
                        Map<String, Object> map = (Map<String, Object>) JSONArray.parse(getes);
                        Map<String, Object> hits = (Map<String, Object>) map.get("hits");
                        List<Map<String, Object>> hist2 = (List<Map<String, Object>>) hits.get("hits");
                        for (int i = 0; i < hist2.size(); i++) {
                            New_Standard_media_assets new_standard_media_assets = new New_Standard_media_assets();
                            new_standard_media_assets.setItemId((String) hist2.get(i).get("_id"));
                            new_standard_media_assets.setDuration("");
                            if(i<=50){
                                new_standard_media_assets.setRecallStrategyId("0-5");
                            }
                            if(i<=100&&i>=50){
                                new_standard_media_assets.setRecallStrategyId("6-10");
                            }if(i<=140&&i>=100){
                                new_standard_media_assets.setRecallStrategyId("11-12");
                            }if(i<=180&&i>=140){
                                new_standard_media_assets.setRecallStrategyId("13-16");
                            }if(i<=200&&i>=180){
                                new_standard_media_assets.setRecallStrategyId("17-18");
                            }if(i<=220&&i>=200){
                                new_standard_media_assets.setRecallStrategyId("19-19");
                            }if(i<=250&&i>=220){
                                new_standard_media_assets.setRecallStrategyId("20-21");
                            }if(i<=300&&i>=250){
                                new_standard_media_assets.setRecallStrategyId("22-23");
                            }                            list2.add(new_standard_media_assets);
                            request.setRequestId("53@37d8hyd038d8xhx");

                            request.setItems(list2);

                        }
                    }
                } catch (Exception e) {
                    String getes = getes();
                    CommonLogger.error("这次请求返回的结果是走的兜底");
                    Map<String, Object> map = (Map<String, Object>) JSONArray.parse(getes);
                    Map<String, Object> hits = (Map<String, Object>) map.get("hits");
                    List<Map<String, Object>> hist2 = (List<Map<String, Object>>) hits.get("hits");
                    for (int i = 0; i < hist2.size(); i++) {
                        New_Standard_media_assets new_standard_media_assets = new New_Standard_media_assets();
                        if(i<=50){
                            new_standard_media_assets.setRecallStrategyId("0-5");
                        }
                        if(i<=100&&i>=50){
                            new_standard_media_assets.setRecallStrategyId("6-10");
                        }if(i<=140&&i>=100){
                            new_standard_media_assets.setRecallStrategyId("11-12");
                        }if(i<=180&&i>=140){
                            new_standard_media_assets.setRecallStrategyId("13-16");
                        }if(i<=200&&i>=180){
                            new_standard_media_assets.setRecallStrategyId("17-18");
                        }if(i<=220&&i>=200){
                            new_standard_media_assets.setRecallStrategyId("19-19");
                        }if(i<=250&&i>=220){
                            new_standard_media_assets.setRecallStrategyId("20-21");
                        }if(i<=300&&i>=250){
                            new_standard_media_assets.setRecallStrategyId("22-23");
                        }
                        new_standard_media_assets.setItemId((String) hist2.get(i).get("_id"));
                        new_standard_media_assets.setDuration("");
                        list2.add(new_standard_media_assets);
                        request.setRequestId("53@37d8hyd038d8xhx");
                        request.setItems(list2);


                    }
                }
                CommonLogger.info(request.toString());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return request;
    }


    /**
     * 向cess发请求
     * @param request
     * @return
     */

    public String porthttp(HashMap<String, Object> request) {
        //相应内容长度
        String responseEntityString = null;
        Object obj = JSONArray.toJSON(request);
        String jsonObject = obj.toString();
        System.out.println("jsonObject = " + jsonObject);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setHeader("X-CESS-Data-Token", "092e4b7ea9914553a02f901769df45b8");

        StringEntity entity = new StringEntity(jsonObject, "UTF-8");
        httpPost.setEntity(entity);
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
                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                responseEntityString = EntityUtils.toString(responseEntity);

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
        return responseEntityString;
    }


    /**
     * 保底请求es
     * @return
     */
    public String getes(){
        String responseEntityString=null;
   //电视剧
         String json = "{ \"from\":0,\"size\":300,\"query\":{\"bool\":{\"must\":[{\"match_all\":{\"boost\":1.0}}],\"filter\":[{\"wrapper\":{\"query\":\"eyJib29sIjp7ImZpbHRlciI6W3siZXhpc3RzIjp7ImZpZWxkIjoidGl0bGUifX0seyJtYXRjaCI6eyJzZWdtZW50X3NfbmVuZ3R1aV8wNDAxMiI6MX19XX19\"}}]}}}";
    //电影
      //   String json = "{ \"from\":0,\"size\":10,\"query\":{\"bool\":{\"must\":[{\"match_all\":{\"boost\":1.0}}],\"filter\":[{\"wrapper\":{\"query\":\"eyJib29sIjp7ImZpbHRlciI6W3siZXhpc3RzIjp7ImZpZWxkIjoidGl0bGUifX0seyJtYXRjaCI6eyJzZWdtZW50X25lbmd0dWlfMDQwMTIiOjF9fV19fQ==\"}}]}}}";
        //少儿
     //   String json = "{ \"from\":0,\"size\":10,\"query\":{\"bool\":{\"must\":[{\"match_all\":{\"boost\":1.0}}],\"filter\":[{\"wrapper\":{\"query\":\"eyJib29sIjp7ImZpbHRlciI6W3siZXhpc3RzIjp7ImZpZWxkIjoidGl0bGUifX0seyJtYXRjaCI6eyJzZWdtZW50X2R3MDQxMiI6MX19XX19\"}}]}}}";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url2);
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
                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                responseEntityString=EntityUtils.toString(responseEntity);

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
        return responseEntityString;

    }

    /**
     *
     */


}


