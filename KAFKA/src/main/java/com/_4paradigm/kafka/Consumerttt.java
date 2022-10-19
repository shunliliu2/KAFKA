//package com._4paradigm.kafka;
//
//import com._4paradigm.entity.Contentviewlog;
//import com.alibaba.fastjson.JSONArray;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.KafkaListeners;
//import org.springframework.stereotype.Component;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//
//import java.io.IOException;
//
//@Component
//public class Consumerttt {
//    private Contentviewlog contentviewlog = new Contentviewlog();
//    @KafkaListeners({@KafkaListener(topics="action")})
//    public void listen(ConsumerRecord<Integer, String> consumerRecord) {
//        System.out.println("consumerRecord.value() = " + consumerRecord.value());
//        String value = consumerRecord.value();
//        String[] split = value.split(",", -1);
//        try {
//            if (split[1].equals("v")){
//                contentviewlog.setSequence(split[0]);
//                contentviewlog.setStatus(split[1]);
//                contentviewlog.setAreaCode(split[2]);
//                contentviewlog.setUserId(split[3]);
//                contentviewlog.setMediaCode(split[4]);
//                contentviewlog.setBeginTime(split[5]);
//                contentviewlog.setEndTime(split[6]);
//                contentviewlog.setViewSeconds(split[7]);
//                contentviewlog.setReferParentID(split[8]);
//                contentviewlog.setReferPageID(split[9]);
//                contentviewlog.setReferPosID(split[10]);
//                contentviewlog.setReferMediaCode(split[11]);
//                contentviewlog.setHDFlag(split[12]);
//                contentviewlog.setRefType(split[13]);
//                contentviewlog.setSeriesFlag(split[14]);
//                contentviewlog.setParentObject(split[15]);
//                contentviewlog.setRefPageName(split[16]);
//                contentviewlog.setRefPosName(split[17]);
//                contentviewlog.setMediaName(split[18]);
//                contentviewlog.setSysID(split[19]);
//                contentviewlog.setTodayonlineseconds(split[20]);
//                contentviewlog.setPageName(split[21]);
//                contentviewlog.setMediaduration(split[22]);
//                contentviewlog.setPageId(split[23]);
//                contentviewlog.setMediaCodeId(split[24]);
//                contentviewlog.setTryView(split[25]);
//                contentviewlog.setTerminaltype(split[26]);
//                contentviewlog.setUserGroupID(split[27]);
//                contentviewlog.setEpgGroupID(split[28]);
//                contentviewlog.setCounty(split[29]);
//                contentviewlog.setFirstbegintime(split[30]);
//                contentviewlog.setRefer_page_type(split[31]);
//                contentviewlog.setRefer_floor_code(split[32]);
//                contentviewlog.setRefer_floor_title(split[33]);
//                contentviewlog.setRefer_floor_num(split[34]);
//                contentviewlog.setRefer_position_num(split[35]);
//                contentviewlog.setCurrent_page_type(split[36]);
//                contentviewlog.setService_id(split[37]);
//                contentviewlog.setTactic_id(split[38]);
//            }else if (split[1].equals("e")){
//
//            }else if (split[1].equals("order")){
//                contentviewlog.setSequence(split[0]);
//                contentviewlog.setStatus(split[1]);
//                contentviewlog.setAreaCode(split[2]);
//                contentviewlog.setUserId(split[3]);
//                contentviewlog.setMediaCode(split[4]);
//                contentviewlog.setBeginTime(split[5]);
//                contentviewlog.setEndTime(split[6]);
//                contentviewlog.setViewSeconds(split[7]);
//                contentviewlog.setReferParentID(split[8]);
//                contentviewlog.setReferPageID(split[9]);
//                contentviewlog.setReferPosID(split[10]);
//                contentviewlog.setReferMediaCode(split[11]);
//                contentviewlog.setHDFlag(split[12]);
//                contentviewlog.setRefType(split[13]);
//                contentviewlog.setSeriesFlag(split[14]);
//                contentviewlog.setParentObject(split[15]);
//                contentviewlog.setRefPageName(split[16]);
//                contentviewlog.setRefPosName(split[17]);
//                contentviewlog.setMediaName(split[18]);
//                contentviewlog.setSysID(split[19]);
//                contentviewlog.setTodayonlineseconds(split[20]);
//                contentviewlog.setPageName(split[21]);
//                contentviewlog.setMediaduration(split[22]);
//                contentviewlog.setPageId(split[23]);
//                contentviewlog.setMediaCodeId(split[24]);
//                contentviewlog.setTryView(split[25]);
//                contentviewlog.setTerminaltype(split[26]);
//                contentviewlog.setUserGroupID(split[27]);
//                contentviewlog.setEpgGroupID(split[28]);
//                contentviewlog.setCounty(split[29]);
//                contentviewlog.setFirstbegintime(split[30]);
//                contentviewlog.setRefer_page_type(split[31]);
//                contentviewlog.setRefer_floor_code(split[32]);
//                contentviewlog.setRefer_floor_title(split[33]);
//                contentviewlog.setRefer_floor_num(split[34]);
//                contentviewlog.setRefer_position_num(split[35]);
//                contentviewlog.setCurrent_page_type(split[36]);
//                contentviewlog.setService_id(split[37]);
//                contentviewlog.setTactic_id(split[38]);
//
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//
//        System.out.println("contentviewlog = " + contentviewlog);
//        Object o = JSONArray.toJSON(contentviewlog);
//        String contentviewlogjson = o.toString();
//        gethttp(contentviewlogjson);
//
//
//
//
//
//    }
//
//    public void gethttp(String json){
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost("http://172.27.64.50:30000/cess-data-17/data/api/data-access/17/306/items");
//        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
//        httpPost.setHeader("X-CESS-Data-Token", "4f7dd9972abe492ab52729ddf8f0ff81");
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
