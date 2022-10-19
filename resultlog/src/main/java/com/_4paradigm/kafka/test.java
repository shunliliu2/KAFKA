package com._4paradigm.kafka;//package com._4paradigm.kafka;
//import com._4paradigm.entity.Contentviewlog;
//import com._4paradigm.entity.Contentviewlog_er;
//import com._4paradigm.log.CommonLogger;
//import com.alibaba.fastjson.JSONArray;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.KafkaListeners;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class test {
//    @Value("${SceneId}")
//    private String SceneId;
//    @Value("${ItemSetId}")
//    private String  ItemSetId;
//    @Value("${actionurl}")
//    private String actionurl;
//
//
//    private static Map<String,String> map=new HashMap<>();
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//    //@KafkaListeners({@KafkaListener(topics="topicttt"), @KafkaListener(topics="topica"),@KafkaListener(topics="topicb")})
//
//    @KafkaListeners({@KafkaListener(topics="cdr_orderLog")})
//    //@KafkaListener(topics="liu")
//    public void listen(ConsumerRecord<Integer, String> consumerRecord) throws ParseException {
//        Contentviewlog_er contentviewlog_er = new Contentviewlog_er();
//        String value = consumerRecord.value();
//        String[] split = value.split(",", -1);
//        if (split[1].equals("order")) {
//            if (split[21].equals(0)) {
//                contentviewlog_er.setActionTime(Long.getLong(split[5]));
//                contentviewlog_er.setSceneId(SceneId);//场景id
//                contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
//                contentviewlog_er.setAction("subscribe");//行为名称
//                contentviewlog_er.setUserId(split[3]);
//                contentviewlog_er.setReferType(split[35]);
//                contentviewlog_er.setAreaCode(split[2]);
//                contentviewlog_er.setCounty(split[23]);
//                contentviewlog_er.setItemId(split[4]);
//                try {
//                    contentviewlog_er.setProductType(Integer.parseInt(split[16]));
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
//                if (split[16].equals(1)) {
//                    contentviewlog_er.setProductId(split[4]);
//                } else {
//                    contentviewlog_er.setProductId(split[13]);
//                }
//                contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
//                contentviewlog_er.setValidTime(split[17]);
//                contentviewlog_er.setExpiredTime(split[18]);
//                contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
//                contentviewlog_er.setAreaCode(split[16]);
//                try {
//                    if (split[23].equals(33)) {
//                        contentviewlog_er.setTraceId(split[43]);
//                    }
//                    contentviewlog_er.setTraceId(split[43]);
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//
//                }
//
//            } else {
//                contentviewlog_er.setActionTime(Long.getLong(split[5]));
//                contentviewlog_er.setSceneId(SceneId);//场景id
//                contentviewlog_er.setItemSetId(Integer.parseInt(ItemSetId));//物料id
//                contentviewlog_er.setAction("unsubscribe");//行为名称
//                contentviewlog_er.setUserId(split[3]);
//                contentviewlog_er.setReferType(split[35]);
//                contentviewlog_er.setAreaCode(split[2]);
//                contentviewlog_er.setCounty(split[23]);
//                contentviewlog_er.setItemId(split[4]);
//                contentviewlog_er.setProductType(Integer.parseInt(split[16]));
//                if (split[16].equals(1)) {
//                    contentviewlog_er.setProductId(split[4]);
//                } else {
//                    contentviewlog_er.setProductId(split[13]);
//                }
//                try {
//                    contentviewlog_er.setActionTime(sdf.parse(split[19]).getTime());
//                } catch (ParseException e) {
//                    System.out.println("e = " + e);
//                }
//                contentviewlog_er.setValidTime(split[17]);
//                contentviewlog_er.setExpiredTime(split[18]);
//                contentviewlog_er.setOrderContinue(Integer.parseInt(split[22]));
//                contentviewlog_er.setAreaCode(split[16]);
//                try {
//                    if (split[23].equals(33)) {
//                        contentviewlog_er.setTraceId(split[43]);
//                    }
//                    contentviewlog_er.setTraceId(split[43]);
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//
//                }
//
//            }
//        }
//
//
//
//
//        System.out.println("contentviewlog = " + contentviewlog_er);
//        Object o = JSONArray.toJSON(contentviewlog_er);
//        String contentviewlogjson = o.toString();
//        CommonLogger.info(contentviewlogjson);
//        gethttp(contentviewlogjson);
//
//
//
//
//
//    }
//    //action
//
//    public void gethttp(String json){
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost(actionurl);
//        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
//        httpPost.setHeader("X-CESS-Data-Token", "0bf6ff0c10d641c99d519abbeec6ed35");
//
//        StringEntity entity = new StringEntity(json, "UTF-8");
//        httpPost.setEntity(entity);
//        // 响应模型
//        CloseableHttpResponse response = null;
//        try {
//            // 由客户端执行(发送)Post请求
//            response = httpClient.execute(httpPost);
//            // 从响应模型中获取响应实体
//            HttpEntity responseEntity = response.getEntity();
//
//            System.out.println("响应状态为:" + response.getStatusLine());
//            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // 释放资源
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
